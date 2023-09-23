package presentation;

import beans.User;
import dao.UserDao;
import dao.UserDaoImpl;
import enums.MeetingType;
import enums.Role;
import exceptions.UserNotFoundException;
import presentation.ui.AdminUI;
import presentation.ui.ManagerUI;
import service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {
    private static final BufferedReader bufferedReader;
    private static final MeetingRoomService meetingRoomService;
    private static final BookingService bookingService;
    private static final MeetingService meetingService;
    private static final LoginService loginService;
    private static final UserDao userDao;
    static{
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        meetingRoomService = new MeetingRoomServiceImpl();
        bookingService = new BookingServiceImpl();
        meetingService = new MeetingServiceImpl();
        loginService = new LoginServiceImpl();
        userDao = new UserDaoImpl();
    }
    public static void main(String[] args) {
        while(true)
        {
            try{
                /// takes user input to select the mode of operation for the application
                Role roleSelected;
                User user = null;
                do {
                    try {
                        user = performLoginAndGetUser();
                        if(user==null)
                            throw new UserNotFoundException("User Id or Email is Wrong....");
                        roleSelected = user.getRole();
                    } catch (UserNotFoundException e) {
                        Logger.getLogger(Main.class.getName()).info(e.getMessage());
                        roleSelected = null;
                    }
                }while(roleSelected == null);
                System.out.println("--------------------------------------");
                welcomeMessage(user);

                /// Using the role to load the app in that perspective
                while(true) {
                    showOptions(roleSelected);
                    switch (roleSelected){
                        case ADMIN:
                            AdminUI.handleAdminPerspective();
                            break;
                        case MANAGER:
                            ManagerUI.handleManagerPerspective(user);
                            break;
                        case MEMBER:
                            handleMemberPerspective();
                            break;
                    }
                }

            } catch (IOException | SQLException e) {
                System.out.println(e.getMessage());
                Logger.getLogger(Main.class.getName()).severe(e.toString());
            } catch (Exception e){
                System.out.println(e.getMessage());
                Logger.getLogger(Main.class.getName()).severe(e.toString());
                System.exit(1);
            }
        }
    }


    private static User performLoginAndGetUser() throws IOException {
        System.out.println("Login");
        System.out.println("--------------------------------------");
        System.out.print("User ID : ");
        int userId = Integer.parseInt(bufferedReader.readLine());

        System.out.print("User Email : ");
        String userEmail = bufferedReader.readLine();

        /// if user credentials are okay then a non-null user object will be returned.
        return loginService.login(userId,userEmail);
    }

    private static void showOptions(Role role){
        System.out.println("Menu");
        System.out.println("--------------------------------------");
        switch (role){
            case ADMIN:
                AdminUI.showAdminMenu();
                return;
            case MANAGER:
                ManagerUI.showManagerMenu();
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
            default:
                System.out.println("Invalid option...Please choose again.");
        }
    }

    /// options for meeting types
    public static void showMeetingTypeOptions(){
        System.out.println("1. Classroom Training.");
        System.out.println("2. Online Training.");
        System.out.println("3. Conference Call.");
        System.out.println("4. Business.");
    }

    public static MeetingType handleMeetingTypeSelection() throws IOException {
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



    public static void exitApp() throws IOException {
        System.out.println("Thanks for using the app...");
        bufferedReader.close();
        System.exit(0);
    }

    private static void welcomeMessage(User user){
        System.out.printf(
                "Welcome %s (id : %d\trole : %s)\n",
                user.getName(),
                user.getUserId(),
                user.getRole()
        );
    }
}
