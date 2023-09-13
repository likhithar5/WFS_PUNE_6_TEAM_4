import beans.MeetingRoom;
import dto.OrganizeMeetingRequest;
import enums.MeetingType;
import exceptions.AvailableSlotsNotAvailableException;
import service.BookingService;
import service.BookingServiceImpl;
import service.MeetingRoomService;
import service.MeetingRoomServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OrganizeMeetingTests {
    public static void main(String[] args) throws SQLException, AvailableSlotsNotAvailableException {
        /// creating organize meeting request
        MeetingType meetingType = MeetingType.CLASSROOM_TRAINING;
        LocalDate meetingDate = LocalDate.of(2023,10,20);
        LocalTime startTime = LocalTime.of(16,20,0);
        int durationInMinutes = 60;
        int seatsReq = 25;

        MeetingRoomService meetingRoomService = new MeetingRoomServiceImpl();
        List<MeetingRoom> meetingRooms = meetingRoomService.findMeetingRoomsBasedOnUserRequest(meetingType,seatsReq);
        System.out.println("After finding rooms based on user request : " );
        meetingRooms.forEach(System.out::println);

        BookingService bookingService = new BookingServiceImpl();
        List<String> availableMeetingRoomNames =
                bookingService.findRoomsAvailableOnGivenDateAndTime(meetingRooms,meetingDate,startTime,durationInMinutes);
        System.out.println("Available meeting rooms for booking : " );
        availableMeetingRoomNames.forEach(System.out::println);

        OrganizeMeetingRequest organizeMeetingRequest = new OrganizeMeetingRequest(
                "Banking Session",
                MeetingType.CLASSROOM_TRAINING,
                LocalDate.of(2022,10,21),
                LocalTime.of(14,30,0),
                45,
                List.of(1001,1002,1003),
                1004,
                "ROOM_2-LVL_3-HYD",
                "Overview of banking domain and salary account opening."
        );
        if(bookingService.createBooking(organizeMeetingRequest))
            System.out.println("Success in booking meeting");
        else
            System.out.println("Failed");
    }
}
