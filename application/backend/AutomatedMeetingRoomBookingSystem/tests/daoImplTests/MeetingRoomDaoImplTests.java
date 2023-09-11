package daoImplTests;

import beans.MeetingRoom;
import enums.Amenities;
import exceptions.MeetingRoomNotFoundException;
import dao.MeetingRoomDAO;
import dao.MeetingRoomDAOImpl;

import java.sql.SQLException;
import java.util.Set;

public class MeetingRoomDaoImplTests {
    private static MeetingRoomDAO meetingRoomDAO;
    public static void main(String[] args) {
        meetingRoomDAO = new MeetingRoomDAOImpl();
        MeetingRoom meetingRoom = new MeetingRoom(
                "ROOM_7-LVL_4-PUN",
                50,
                3.75f,
                Set.of(Amenities.CONFERENCE_CALL_FACILITY,Amenities.PROJECTOR,Amenities.TV),
                180
        );

        MeetingRoom updatedMeetingRoom = new MeetingRoom(
                "ROOM_3-LVL_5-PUN",
                24,
                4.6f,
                Set.of(Amenities.PROJECTOR,Amenities.WHITEBOARD,Amenities.COFFEE_MACHINE),
                150
        );
        meetingRoom.setNumOfMeetingsHeld(2);

        try{
            boolean flag = givenMeetingRoomInstance_whenInserted_thenPersisted(meetingRoom);
            boolean flag1 = givenMeetingRoomNameAndUpdatedInstance_whenUpdated_ThenUpdatedEntityPersisted(
                    "ROOM_3-LVL_5-PUN",updatedMeetingRoom
            );
            MeetingRoom meetingRoom1 = givenMeetingRoomName_whenQueried_ThenReturnedMeetingRoom("ROOM_2-LVL_6-PUN");

            if(flag && flag1 && meetingRoom1!=null)
                System.out.println("Success....!!");

        } catch (SQLException | MeetingRoomNotFoundException e) {
            System.out.println("[ERROR OCCURRED] " + e.getMessage());
        }
    }

    private static boolean givenMeetingRoomInstance_whenInserted_thenPersisted(MeetingRoom newMeetingRoom) throws SQLException {
        System.out.println("newMeetingRoom = " + newMeetingRoom);
        return meetingRoomDAO.create(newMeetingRoom);
    }

    private static boolean givenMeetingRoomNameAndUpdatedInstance_whenUpdated_ThenUpdatedEntityPersisted(
            String meetingRoomName,
            MeetingRoom updatedMeetingRoom
    )throws SQLException, MeetingRoomNotFoundException{
        return meetingRoomDAO.modify(meetingRoomName, updatedMeetingRoom);
    }

    private static MeetingRoom givenMeetingRoomName_whenQueried_ThenReturnedMeetingRoom(String meetingRoomName)
            throws MeetingRoomNotFoundException, SQLException {
        return meetingRoomDAO.getMeetingRoomById(meetingRoomName);
    }
}
