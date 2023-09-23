package presentation.ui;

import beans.Meeting;
import beans.MeetingRoom;
import beans.User;
import dao.UserDao;
import dao.UserDaoImpl;
import dto.OrganizeMeetingRequest;
import enums.MeetingType;
import exceptions.MeetingRoomNotFoundException;
import exceptions.UserNotFoundException;
import service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static presentation.Main.*;

public class ManagerUI {
    private static final BufferedReader bufferedReader;
    private static final MeetingRoomService meetingRoomService;
    private static final BookingService bookingService;
    private static final MeetingService meetingService;
    private static final UserDao userDao;
    static{
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        meetingRoomService = new MeetingRoomServiceImpl();
        bookingService = new BookingServiceImpl();
        meetingService = new MeetingServiceImpl();
        userDao = new UserDaoImpl();
    }

    /// options for the manager
    public static void showManagerMenu() {
        System.out.println("1. Create meeting.");
        System.out.println("2. Get Scheduled Meetings.");
        System.out.println("3. Get Meeting Info.");
        System.out.println("4. Exit.");
    }

    public static void handleManagerPerspective(User user) throws IOException, SQLException {
        System.out.println("Enter choice :");
        int choice = Integer.parseInt(bufferedReader.readLine());
        switch (choice){
            case 1: // create meeting
                System.out.println("Enter the following meeting details (All field are mandatory :");
                System.out.print("Title : ");
                String mTitle = bufferedReader.readLine();

                System.out.print("Date (dd-mm-yyyy) : ");
                String mDate = bufferedReader.readLine();
                LocalDate mDateLocalDate = LocalDate.parse(mDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                System.out.print("Start time (24hrs) (hh:mm:ss): ");
                String mStartTime = bufferedReader.readLine();
                LocalTime mStartTimeLocalTime = LocalTime.parse(mStartTime,DateTimeFormatter.ofPattern("HH:mm:ss"));

                System.out.println("Duration (in minutes) : ");
                int durationInMinutes = Integer.parseInt(bufferedReader.readLine());

                MeetingType meetingType;
                do{
                    System.out.println("Select meeting type : ");
                    showMeetingTypeOptions();
                    meetingType = handleMeetingTypeSelection();

                }while(meetingType==null);

                System.out.println("Description : ");
                String mDescription = bufferedReader.readLine();

                System.out.println("Enter participants (user IDs separated by ',') : ");
                String participantIds = bufferedReader.readLine();
                List<Integer> participants =
                        Arrays.stream(participantIds.split(","))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());

                System.out.println("Available meeting rooms for booking : " );
                List<MeetingRoom> meetingRooms =
                        meetingRoomService.findMeetingRoomsBasedOnUserRequest(meetingType,participants.size());
                System.out.println("meetingRooms = " + meetingRooms);
                List<String> availableMeetingRoomNames =
                        bookingService.findRoomsAvailableOnGivenDateAndTime(
                                meetingRooms,mDateLocalDate,mStartTimeLocalTime,durationInMinutes
                        );
                availableMeetingRoomNames.forEach(System.out::println);

                System.out.println("\nSelect a Meeting room name : ");
                String meetingRoomNameSelected = bufferedReader.readLine();
                OrganizeMeetingRequest organizeMeetingRequest = new OrganizeMeetingRequest(
                        mTitle,
                        meetingType,
                        mDateLocalDate,
                        mStartTimeLocalTime,
                        durationInMinutes,
                        participants,
                        user.getUserId(), // to be retrieved when user (i.e. manager) logs in
                        meetingRoomNameSelected,
                        mDescription
                );
                //TODO: deduct manager's meeting booking credits as per the cost of booking the meeting room.
                int creditsLeft = userDao.getUserCredits(user.getUserId());
                try {
                    int costOfMeetingRoom = meetingRoomService.getMeetingRoomCost(meetingRoomNameSelected,durationInMinutes);
                    System.out.println("meeting room cost : " + costOfMeetingRoom);
                    if(creditsLeft>=costOfMeetingRoom)
                    {
                        if(bookingService.createBooking(organizeMeetingRequest))
                        {
                            System.out.println("Meeting successfully Created...");
                            creditsLeft-=costOfMeetingRoom;
                            userDao.setUserCredits(user.getUserId(), creditsLeft);
                            System.out.println("credits left : " + creditsLeft);
                        }
                    }
                    else
                        System.out.println("Not Enough credits...");
                } catch (MeetingRoomNotFoundException | UserNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2: // get scheduled meetings
                /// fetch the meetings scheduled by this manager
                List<Meeting> meetings = meetingService.fetchMeetingsByOrganizerId(user.getUserId());
                System.out.println("Scheduled Meetings : ");
                meetings.forEach(System.out::println);
                break;
            case 3: // get meeting info
                System.out.print("Enter meeting ID : ");
                int meetingId = Integer.parseInt(bufferedReader.readLine());
                try{
                    Meeting meeting = meetingService.fetchMeetingById(meetingId);
                    System.out.println("Meeting details :");
                    System.out.printf(
                            "ID : %s\nTitle : %s\nType : %s\n"+
                                    "Date : %s\nStart time : %s\nEnd time : %s\n"+
                                    "Description : %s\nVenue : %s\n",
                            meeting.getMeetingId(),
                            meeting.getMeetingTitle(),
                            meeting.getMeetingType(),
                            meeting.getMeetingDate(),
                            meeting.getStartTime(),
                            meeting.getEndTime(),
                            meeting.getMeetingDescription(),
                            meeting.getMeetingRoom()
                    );
                }catch (MeetingRoomNotFoundException e){
                    System.out.println(e.getMessage());
                }

                break;
            case 4:// exit from app
                exitApp();
            default:
                System.out.println("Invalid option...Please choose again.");
        }
    }
}
