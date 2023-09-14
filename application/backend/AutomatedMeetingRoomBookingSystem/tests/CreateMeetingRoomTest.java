import beans.MeetingRoom;
import enums.Amenities;
import service.MeetingRoomService;
import service.MeetingRoomServiceImpl;

import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Logger;

public class CreateMeetingRoomTest {
    private static final MeetingRoomService meetingRoomService;
    static{
        meetingRoomService = new MeetingRoomServiceImpl();
    }
    public static void main(String[] args) {
        givenAllInput_whenExecuted_ThenSaved();
    }

    private static void givenAllInput_whenExecuted_ThenSaved() {
        /// User input
        String roomName = "ROOM_2-LVL_3-PUN";
        int seatingCapacity = 30;
        Set<Amenities> amenitiesList = Set.of(Amenities.PROJECTOR,Amenities.WHITEBOARD);
        int perHourCharge = calculatePerHourCharges(amenitiesList);
        try {
            if(meetingRoomService.addNewMeetingRoom(new MeetingRoom(
                    roomName,
                    seatingCapacity,
                    0,
                    amenitiesList,
                    perHourCharge

            )))
                System.out.println("Added a new meeting room");
        } catch (SQLException e) {
            Logger.getLogger(CreateMeetingRoomTest.class.getName()).severe(e.getMessage());
        }

    }

    private static int calculatePerHourCharges(Set<Amenities> amenitiesList) {
        return amenitiesList.stream()
                .map(Amenities::getAmenityCost)
                .reduce(0, Integer::sum);
    }
}
