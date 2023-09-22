package presentation;

import beans.Meeting;
import beans.MeetingRoom;
import dto.OrganizeMeetingRequest;
import enums.MeetingType;
import enums.Role;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    private static final BufferedReader bufferedReader;
    private static final MeetingRoomService meetingRoomService;
    private static final BookingService bookingService;
    private static final MeetingService meetingService;
    static{
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        meetingRoomService = new MeetingRoomServiceImpl();
        bookingService = new BookingServiceImpl();
        meetingService = new MeetingServiceImpl();
    }
    public static void main(String[] args) {
        try{
            /// takes user input to select the mode of operation for the application
            Role roleSelected;
            do {
                try {
                    showRoleSelectionOptions();
                    System.out.println("Enter choice : ");
                    int roleSelectedChoiceInput = Integer.parseInt(bufferedReader.readLine());
                    switch (roleSelectedChoiceInput){
                        case 1:
                            roleSelected = Role.ADMIN;
                            break;
                        case 2:
                            roleSelected = Role.MANAGER;
                            break;
                        case 3:
                            roleSelected = Role.MEMBER;
                            break;
                        case 4:
                            exitApp();
                        default:
                            System.out.println("Invalid Role...Please choose again...");
                            roleSelected = null;
                    }
                } catch (IllegalArgumentException e) {
                    Logger.getLogger(Main.class.getName()).info("Role not available.");
                    roleSelected = null;
                }
            }while(roleSelected == null);

            /// Using the role to load the app in that perspective
            while(true) {
                showOptions(roleSelected);
                switch (roleSelected){
                    case ADMIN:
                        handleAdminPerspective();
                        break;
                    case MANAGER:
                        handleManagerPerspective();
                        break;
                    case MEMBER:
                        handleMemberPerspective();
                        break;
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static void showRoleSelectionOptions(){
        System.out.println("Select Role:");
        System.out.println("1. ADMIN.");
        System.out.println("2. MANAGER.");
        System.out.println("3. MEMBER.");
    }

    private static void showOptions(Role role){
        System.out.println("Menu");
        System.out.println("--------------------------------------");
        switch (role){
            case ADMIN:
                showAdminMenu();
                return;
            case MANAGER:
                showManagerMenu();
                return;
            case MEMBER:
                showMemberMenu();
                return;
            default:
                System.out.println("Invalid Role....Please choose again.");
        }
    }

    /// options for the members
    private static void showMemberMenu() {
        System.out.println("1. View Scheduled Meetings.");
        System.out.println("2. Get Meeting Info.");
        System.out.println("3. Exit.");
    }
    private static void handleMemberPerspective() throws IOException {
        System.out.println("Enter choice :");
        int choice = Integer.parseInt(bufferedReader.readLine());
        switch (choice){
            case 1: // view scheduled meetings
                System.out.println("View Scheduled Meetings");
                break;
            case 2: // get meeting info
                System.out.println("Get Meeting Info");
                break;
            case 3:// exit from app
                exitApp();
        }
    }

    /// options for the manager
    private static void showManagerMenu() {
        System.out.println("1. Create meeting.");
        System.out.println("2. Get Scheduled Meetings.");
        System.out.println("3. Get Meeting Info.");
        System.out.println("4. Exit.");
    }

    private static void handleManagerPerspective() throws IOException, SQLException {
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

                System.out.println("participants = " + participants);
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
                        2101, // to be retrieved when user (i.e. manager) logs in
                        meetingRoomNameSelected,
                        mDescription
                );
                if(bookingService.createBooking(organizeMeetingRequest))
                    System.out.println("Meeting successfully Created...");
                else
                    System.out.println("Meeting creation Failed...");
                break;
            case 2: // get scheduled meetings
                List<Meeting> meetings = meetingService.fetchMeetingsByOrganizerId(2101);
                System.out.println("Scheduled Meetings : ");
                meetings.forEach(System.out::println);
                break;
            case 3: // get meeting info
                System.out.println("Get Meeting Info");
                break;
            case 4:// exit from app
                exitApp();
        }
    }

    /// options for meeting types
    private static void showMeetingTypeOptions(){
        System.out.println("1. Classroom Training.");
        System.out.println("2. Online Training.");
        System.out.println("3. Conference Call.");
        System.out.println("4. Business.");
    }

    private static MeetingType handleMeetingTypeSelection() throws IOException {
        System.out.println("Enter choice :");
        int choice = Integer.parseInt(bufferedReader.readLine());
        switch (choice){
            case 1: // classroom training
                return MeetingType.CLASSROOM_TRAINING;
            case 2: // online training
                return MeetingType.ONLINE_TRAINING;
            case 3: // conference call
                return MeetingType.CONFERENCE_CALL;
            case 4: // business
                return MeetingType.BUSINESS;
            default:
                System.out.println("Invalid meeting type...Please choose again.");
                return null;
        }
    }

    /// options for the manager
    private static void showAdminMenu(){
        System.out.println("1. Import users.");
        System.out.println("2. Add Meeting Room.");
        System.out.println("3. Edit Meeting Room.");
        System.out.println("4. Show all Meeting Rooms.");
        System.out.println("5. Exit.");
    }

    private static void handleAdminPerspective() throws IOException {
        System.out.println("Enter choice :");
        int choice = Integer.parseInt(bufferedReader.readLine());
        switch (choice){
            case 1: // import users
                System.out.println("Import Users");
                break;
            case 2: // add new meeting rooms
                System.out.println("Add new meeting room");
                break;
            case 3: // edit a meeting room
                System.out.println("Edit a meeting room");
                break;
            case 4: // show all meeting rooms
                System.out.println("Show all meeting rooms");
                break;
            case 5:// exit from app
                exitApp();
        }
    }

    private  static void exitApp() throws IOException {
        System.out.println("Thanks for using the app...");
        bufferedReader.close();
        System.exit(0);
    }
}
