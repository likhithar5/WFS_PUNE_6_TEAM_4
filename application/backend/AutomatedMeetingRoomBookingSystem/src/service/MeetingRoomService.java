package service;

import beans.MeetingRoom;
import enums.MeetingType;
import exceptions.MeetingRoomNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface MeetingRoomService {

    /**
     * Method to find meeting rooms that conforms to the user's given requirements.
     * @param meetingType The type of meeting to be organized by the user.
     * @param seatsRequired The number of seats required to accommodate at least all the participants.
     * @throws SQLException For any Sql related exceptions.
     * @author Sayantan Das
     * */
    List<MeetingRoom> findMeetingRoomsBasedOnUserRequest(MeetingType meetingType, int seatsRequired) throws SQLException;


    /**
     * Method to add a new meeting rooms.
     * @param meetingRoom The type of meeting to be organized by the user.
     * @throws SQLException For any Sql related exceptions.
     * @author Sayantan Das
     * */
    boolean addNewMeetingRoom(MeetingRoom meetingRoom) throws SQLException;

    int getMeetingRoomCost(String meetingRoomName, int bookingDurationInMinutes) throws SQLException, MeetingRoomNotFoundException;

    List<MeetingRoom> fetchAllMeetingRooms() throws SQLException;

    MeetingRoom getMeetingRoomByName(String roomNameToUpdate) throws SQLException, MeetingRoomNotFoundException;
}
