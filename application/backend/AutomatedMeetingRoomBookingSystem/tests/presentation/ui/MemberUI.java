package presentation.ui;

import dao.UserDao;
import dao.UserDaoImpl;
import presentation.Main;
import service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemberUI {
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

    /// options for the members
    public static void showMemberMenu() {
        System.out.println("1. View Scheduled Meetings.");
        System.out.println("2. Get Meeting Info.");
        System.out.println("3. Exit.");
    }
    public static void handleMemberPerspective() throws IOException {
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
                Main.exitApp();
            default:
                System.out.println("Invalid option...Please choose again.");
        }
    }
}
