package service;

import beans.MeetingRoom;
import dao.MeetingRoomDAO;
import dao.MeetingRoomDAOImpl;
import enums.Amenities;
import enums.MeetingType;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MeetingRoomServiceImpl implements MeetingRoomService{
    private final MeetingRoomDAO meetingRoomDAO;

    public MeetingRoomServiceImpl() {
        this.meetingRoomDAO = new MeetingRoomDAOImpl();
    }

    @Override
    public List<MeetingRoom> findMeetingRoomsBasedOnUserRequest(
            MeetingType meetingType, int seatsRequired) throws SQLException {
        /// getting the mandatory amenities
        List<Amenities> amenitiesExpected = MeetingType.getMandatoryAmenities(meetingType);

        /// find out meeting rooms based on amenities
        List<MeetingRoom> meetingRooms = meetingRoomDAO.findByAmenities(amenitiesExpected);

        /// filter out meeting rooms based on seating capacity
        meetingRooms = filterMeetingRoomWithAdequateSeat(seatsRequired,meetingRooms);

        /// filtering out the rooms that have all the amenities
        meetingRooms = filterMeetingRoomsHavingAllTheRequestedAmenities(amenitiesExpected, meetingRooms);

        return meetingRooms;
    }

    @Override
    public boolean addNewMeetingRoom(MeetingRoom meetingRoom) throws SQLException {
        return meetingRoomDAO.create(meetingRoom);
    }

    /**
     * Filter out meeting rooms based on the seating capacity from a list of meeting rooms.
     * @param meetingRoomList The list of meeting rooms from where to filter.
     * @param reqSeatingCapacity The number of seats that must be present in a meeting room to accommodate
     *                           all the participants.
     * @return List of type MeetingRoom
     * @author Sayantan Das
     * */
    private static List<MeetingRoom> filterMeetingRoomWithAdequateSeat(
            int reqSeatingCapacity, List<MeetingRoom> meetingRoomList){
        meetingRoomList = meetingRoomList.stream()
                .filter(meetingRoom -> meetingRoom.getSeatingCapacity()>=reqSeatingCapacity)
                .collect(Collectors.toList());
        return meetingRoomList;
    }

    /**
     * Filtering out the rooms that have all the amenities from a list of meeting rooms.
     * @param meetingRoomList The list of meeting rooms from where to filter.
     * @param amenitiesRequested The list of amenities that must be present in a meeting room.
     * @return List of type MeetingRoom
     * @author Sayantan Das
     * */
    private static List<MeetingRoom> filterMeetingRoomsHavingAllTheRequestedAmenities(
            List<Amenities> amenitiesRequested, List<MeetingRoom> meetingRoomList){
        meetingRoomList = meetingRoomList.stream()
                .filter(meetingRoom -> {
                    Set<Amenities> amenitiesSet = meetingRoom.getAmenities();
                    for(Amenities userRequestedAmenity : amenitiesRequested){
                        if(!amenitiesSet.contains(userRequestedAmenity))
                            return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
        return meetingRoomList;
    }
}
