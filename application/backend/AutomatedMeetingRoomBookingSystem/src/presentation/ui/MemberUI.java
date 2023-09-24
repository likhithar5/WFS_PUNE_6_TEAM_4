package presentation.ui;

import beans.Meeting;
import beans.User;
import exceptions.MeetingNotFoundException;
import presentation.Main;
import service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MemberUI {
    private static final BufferedReader bufferedReader;
    private static final MemberService memberService;
    static{
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        memberService = new MemberServiceImpl();
    }

    /// options for the members
    public static void showMemberMenu() {
        System.out.println("1. View Scheduled Meetings.");
        System.out.println("2. Get Meeting Info.");
        System.out.println("3. Exit.");
    }
    public static void handleMemberPerspective(User user) {
        try {
            System.out.println("Enter choice :");
            int choice = Integer.parseInt(bufferedReader.readLine());
            switch (choice) {
                case 1: // view scheduled meetings
                    System.out.println("Scheduled Meetings are as follows :");
                    memberService.getMeetingsScheduled(user.getUserId()).forEach(System.out::println);
                    break;
                case 2: // get meeting info
                    System.out.print("Enter the meeting ID : ");
                    int meetingId = Integer.parseInt(bufferedReader.readLine());
                    System.out.println("Meeting Information : ");
                    try {
                        Meeting meeting = memberService.getMeetingInfoOf(meetingId);
                        System.out.printf(
                                "ID : %s\nTitle : %s\nType : %s\n" +
                                        "Date : %s\nStart time : %s\nEnd time : %s\n" +
                                        "Participants IDs : %s\n" +
                                        "Description : %s\nVenue : %s\n",
                                meeting.getMeetingId(),
                                meeting.getMeetingTitle(),
                                meeting.getMeetingType(),
                                meeting.getMeetingDate(),
                                meeting.getStartTime(),
                                meeting.getEndTime(),
                                meeting.getParticipants(),
                                meeting.getMeetingDescription(),
                                meeting.getMeetingRoom()
                        );
                    } catch (MeetingNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:// exit from app
                    Main.exitApp();
                default:
                    System.out.println("Invalid option...Please choose again.");
            }
        }catch (IOException | SQLException e){
            Logger.getLogger(MemberUI.class.getName()).severe(e.toString());
        }
    }
}
