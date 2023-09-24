package presentation;

import beans.User;
import enums.MeetingType;
import enums.Role;
import exceptions.UserNotFoundException;
import presentation.ui.AdminUI;
import presentation.ui.ManagerUI;
import presentation.ui.MemberUI;
import service.LoginService;
import service.LoginServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Main {
    private static final BufferedReader bufferedReader;
    private static final LoginService loginService;
    static{
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        loginService = new LoginServiceImpl();
    }
    public static void main(String[] args) {
        Role roleSelected = null;
        User user = null;
        try{
            /// takes user input to select the mode of operation for the application
            do {
                try {
                    user = performLoginAndGetUser();
                    if(user==null)
                        throw new UserNotFoundException("User Id or Email is Wrong....");
                    roleSelected = user.getRole();
                } catch (UserNotFoundException e) {
                    Logger.getLogger(Main.class.getName()).info(e.getMessage());
                }
            }while(roleSelected==null && user==null);

        } catch (Exception e){
            System.out.println(e.getMessage());
            Logger.getLogger(Main.class.getName()).severe(e.toString());
        }
  

        System.out.println("--------------------------------------");
        assert user != null;
        welcomeMessage(user);

        try{
            /// Using the role to load the app in that perspective
            while(true) {
                assert roleSelected != null;
                showOptions(roleSelected);
                switch (roleSelected){
                    case ADMIN:
                        AdminUI.handleAdminPerspective();
                        break;
                    case MANAGER:
                        ManagerUI.handleManagerPerspective(user);
                        break;
                    case MEMBER:
                        MemberUI.handleMemberPerspective(user);
                        break;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Logger.getLogger(Main.class.getName()).severe(e.toString());
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
                MemberUI.showMemberMenu();
                return;
            default:
                System.out.println("Invalid Role....Please choose again.");
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
