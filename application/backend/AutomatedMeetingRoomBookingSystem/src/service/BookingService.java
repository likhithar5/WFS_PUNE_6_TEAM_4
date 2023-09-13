package service;

import beans.MeetingRoom;
import dto.OrganizeMeetingRequest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingService {

    /**
     * Finds rooms that have time slots available to book for a meeting according to the user's requirement.
     * @param meetingRooms The meeting rooms that have all the required amenities and seating capacity.
     * @param meetingDate The date on which the meeting is to be held.
     * @param start The starting time of the meeting.
     * @param durationInMinutes The duration of the meeting in minutes.
     * @throws SQLException For any Sql related exceptions.
     * @author Sayantan Das
     * */
    List<String> findRoomsAvailableOnGivenDateAndTime(
            List<MeetingRoom> meetingRooms,
            LocalDate meetingDate,
            LocalTime start,
            int durationInMinutes
    ) throws SQLException;

    boolean createBooking(OrganizeMeetingRequest organizeMeetingRequest) throws SQLException;
}
