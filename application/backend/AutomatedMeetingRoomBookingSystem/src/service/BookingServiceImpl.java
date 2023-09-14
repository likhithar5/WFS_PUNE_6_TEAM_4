package service;

import beans.Booking;
import beans.Meeting;
import beans.MeetingRoom;
import beans.Participant;
import dao.*;
import dto.OrganizeMeetingRequest;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService{
    private final BookingsDAO bookingsDAO;
    private final MeetingDao meetingDao;
    private final ParticipantsDAO participantsDAO;

    public BookingServiceImpl() {
        this.bookingsDAO = new BookingsDAOImpl();
        this.meetingDao = new MeetingDaoImpl();
        this.participantsDAO = new ParticipantDAOImpl();
    }

    @Override
    public List<String> findRoomsAvailableOnGivenDateAndTime(
            List<MeetingRoom> meetingRooms,
            LocalDate meetingDate,
            LocalTime startTime,
            int durationInMinutes) throws SQLException {
        /// find the bookings that have been made for the shortlisted rooms sorted in asc by date then st_time then end_time
        Map<String, List<Booking>> bookingsGroupedByRoomName =
                bookingsDAO.getBookingsByMeetingRoomName(
                        meetingRooms.stream()
                                .map(MeetingRoom::getMeetingRoomName)
                                .collect(Collectors.toList())
                );

        /// find an available slot(s)
        List<String> roomsAvailableForBooking = new ArrayList<>();
        for(Map.Entry<String, List<Booking>> entry : bookingsGroupedByRoomName.entrySet())
        {
            String roomName = entry.getKey();
            List<Booking> bookings = entry.getValue();
            if(isThereAnyBookingForGivenDate(bookings,meetingDate)) // there are some booking for that date, so need to check the start times
            {
                // finding the range of search based on the required meeting date
                int searchStartIndex = -1, searchEndIndex = -1;
                for(int i=0;i<bookings.size();++i)
                {
                    if(bookings.get(i).getDateOfBooking().isEqual(meetingDate) && searchStartIndex==-1)
                        searchStartIndex = i;
                    else if(!bookings.get(i).getDateOfBooking().isEqual(meetingDate)){
                        searchEndIndex = i-1;
                        break;
                    }
                }

                // finding available slot
                for(int i=searchStartIndex;i<=searchEndIndex;++i)
                {
                    if(startTime.isBefore(bookings.get(i).getStartTime()))
                    {
                        if((i-1)>=searchStartIndex) // previous index is valid
                        {
                            long durationBetweenPrevAndNextMeeting =
                                    Duration.between(bookings.get(i).getStartTime(), bookings.get(i-1).getEndTime())
                                            .toMinutes();
                            if(durationBetweenPrevAndNextMeeting>=durationInMinutes)
                            {
                                roomsAvailableForBooking.add(roomName);
                                break;
                            }
                        }
                        else // invalid index,i.e. the first meeting in search range itself is booked after the required one.
                            roomsAvailableForBooking.add(roomName);
                    }
                    else if (startTime.equals(bookings.get(i).getStartTime()))
                        break;
                }// end loop for finding the available slot
                if(searchEndIndex+1<= bookings.size()-1 &&
                        startTime.isAfter(bookings.get(searchEndIndex+1).getEndTime()))
                    roomsAvailableForBooking.add(roomName);
            }// end of if part when there are some bookings on the required date
            else // when there are no bookings on that required date, then we can safely add it our list of answer.
                roomsAvailableForBooking.add(roomName);
        }//end outermost for
        return roomsAvailableForBooking;
    }

    @Override
    public boolean createBooking(OrganizeMeetingRequest organizeMeetingRequest) throws SQLException {
        /// Adding meeting to the meetings table
        int meetingId = generateMeetingId();
        Meeting meeting = new Meeting(
                meetingId,
                organizeMeetingRequest.getMeetingTitle(),
                organizeMeetingRequest.getOrganizedBy(),
                organizeMeetingRequest.getMeetingDate(),
                organizeMeetingRequest.getMeetingStartTime(),
                organizeMeetingRequest.getMeetingStartTime().plusMinutes(organizeMeetingRequest.getDurationInMinutes()),
                organizeMeetingRequest.getMeetingType(),
                organizeMeetingRequest.getBookedMeetingRoomName(),
                null,
                organizeMeetingRequest.getMeetingDescription()
        );
        int rowsAffectedInMeetingsTable = meetingDao.createMeeting(meeting);
        System.out.println("rowsAffectedInMeetingsTable = " + rowsAffectedInMeetingsTable);

        /// Adding meeting participants to the participants table
        List<Participant> participantListForTheMeeting =
                organizeMeetingRequest.getParticipants()
                        .stream()
                        .map(userId -> new Participant(userId,meetingId))
                        .collect(Collectors.toList());
        boolean areAllParticipantsAdded = participantsDAO.add(participantListForTheMeeting);
        if(areAllParticipantsAdded)
            System.out.println("Participants Added...");
        /// Adding booking to the bookings table
        Booking booking = new Booking(
                Instant.now().getNano(),
                organizeMeetingRequest.getBookedMeetingRoomName(),
                organizeMeetingRequest.getMeetingDate(),
                organizeMeetingRequest.getMeetingStartTime(),
                organizeMeetingRequest.getMeetingStartTime().plusMinutes(organizeMeetingRequest.getDurationInMinutes()),
                organizeMeetingRequest.getOrganizedBy()
        );
        boolean isBookingRecordCreated = bookingsDAO.create(booking);
        System.out.println("isBookingRecordCreated = " + isBookingRecordCreated);
        return rowsAffectedInMeetingsTable>0 && areAllParticipantsAdded && isBookingRecordCreated;
    }

    private int generateMeetingId() {
        return Instant.now().getNano();
    }

    private boolean isThereAnyBookingForGivenDate(List<Booking> list, LocalDate meetingDate){
        for (Booking booking : list)
            if (booking.getDateOfBooking().isEqual(meetingDate))
                return true;
        return false;
    }
}
