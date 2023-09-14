import beans.MeetingRoom;
import enums.Amenities;
import service.AdminService;
import service.AdminServiceImpl;
import service.MeetingRoomService;
import service.MeetingRoomServiceImpl;

import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Logger;

public class CreateMeetingRoomTest {
    private static final AdminService adminService;
    static{
        adminService = new AdminServiceImpl();
    }
    public static void main(String[] args) {
        givenAllInput_whenExecuted_ThenSaved();
    }

    private static void givenAllInput_whenExecuted_ThenSaved() {
        /// User input
        String roomName = "ROOM_7-LVL_5-HYD";
        int seatingCapacity = 120;
        Set<Amenities> amenitiesList = Set.of(Amenities.PROJECTOR,Amenities.WHITEBOARD, Amenities.COFFEE_MACHINE, Amenities.WIFI_CONNECTION);
        int perHourCharge = calculatePerHourCharges(amenitiesList);
        if(adminService.createMeetingRoom(new MeetingRoom(
                roomName,
                seatingCapacity,
                0,
                amenitiesList,
                perHourCharge

        )))
            System.out.println("Added a new meeting room");

    }

    private static int calculatePerHourCharges(Set<Amenities> amenitiesList) {
        return amenitiesList.stream()
                .map(Amenities::getAmenityCost)
                .reduce(0, Integer::sum);
    }
}
