package dao;

import beans.MeetingRoom;
import enums.Amenities;
import exceptions.MeetingRoomNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface MeetingRoomDAO {

    /**
     * Takes an instance of meeting room and persist it in the database.
     *
     * @param meetingRoom : The meeting room instance with its properties set to persist.
     *
     * @return boolean : Implies if the insert operation is successful or not.
     *
     * @throws SQLException : For any kind of Sql related exceptions.
     *
     * @author Sayantan Das
     * */
    boolean create(MeetingRoom meetingRoom) throws SQLException;

    /**
     * Takes an instance of meeting room and updates it in the database whose primary key
     * matches with the key passed by user.
     *
     * @param meetingRoomNameToUpdate : The primary key of the meeting room to update.
     *
     * @param updatedMeetingRoom : The instance of the meeting room with updated properties that will be
     *                           persisted in the database.
     *
     * @return boolean : Implies if the update operation is successful or not.
     *
     * @throws SQLException : For any kind of Sql related exceptions.
     * @throws MeetingRoomNotFoundException : Implies that the meeting room with the given name does not exist.
     *
     * @author Sayantan Das
     * */
    boolean modify(String meetingRoomNameToUpdate, MeetingRoom updatedMeetingRoom)
            throws SQLException, MeetingRoomNotFoundException;

    /**
     * Fetches  and returns the meeting room instance using the name of the meeting room provided.
     *
     * @param meetingRoomName : The primary key of the entity that needs to be fetched.
     *
     * @return MeetingRoom
     *
     * @throws SQLException : For any kind of Sql related exceptions.
     *
     * @throws MeetingRoomNotFoundException : If the meeting room with the given primary key is not found.
     *
     * @author Sayantan Das
     * */
    MeetingRoom getMeetingRoomById(String meetingRoomName) throws SQLException, MeetingRoomNotFoundException;

    /**
     * Searches for meeting rooms having at least one of the expected amenities.
     * @param expectedAmenities The amenities expected to have as per user's requirement.
     * @return List of MeetingRoom
     * @throws SQLException For any kind of Sql related exceptions.
     * @author Sayantan Das
     * */
    List<MeetingRoom> findByAmenities(List<Amenities> expectedAmenities) throws SQLException;

    /**
     * Fetches all meeting rooms.
     * @return List of MeetingRoom
     * @throws SQLException For any kind of Sql related exceptions.
     * @author Sayantan Das
     * */
    List<MeetingRoom> getAllMeetingRooms() throws SQLException;
}
