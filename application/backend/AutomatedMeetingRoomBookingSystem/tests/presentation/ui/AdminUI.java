package presentation.ui;

import beans.MeetingRoom;
import enums.Amenities;
import exceptions.MeetingRoomNotFoundException;
import presentation.Main;
import service.AdminService;
import service.AdminServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class AdminUI {
    private static final BufferedReader bufferedReader;
    private static final AdminService adminService;

    static{
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        adminService = new AdminServiceImpl();
    }

    /// options for the admin
    public static void showAdminMenu(){
        System.out.println("1. Import users.");
        System.out.println("2. Add Meeting Room.");
        System.out.println("3. Edit Meeting Room.");
        System.out.println("4. Show all Meeting Rooms.");
        System.out.println("5. Exit.");
    }

    public static void handleAdminPerspective() throws IOException {
        Set<Amenities> amenitiesSet;
        int perHourCharge;
        System.out.println("Enter choice :");
        int choice = Integer.parseInt(bufferedReader.readLine());
        switch (choice){
            case 1: // import users
                System.out.println("Import Users");
                break;
            case 2: // add new meeting rooms
                System.out.println("Enter details of New Meeting Room :");
                System.out.println("Room name : ");
                String roomName = bufferedReader.readLine();

                System.out.println("Seating capacity : ");
                int seatingCap = Integer.parseInt(bufferedReader.readLine());

                amenitiesSet = selectAmenitiesMenuHandler();
                perHourCharge = calculatePerHourCharges(amenitiesSet);
                if(adminService.createMeetingRoom(
                        new MeetingRoom(roomName, seatingCap, 0, amenitiesSet, perHourCharge)
                ))
                    System.out.println("New meeting room successfully added.");
                else
                    System.out.println("Failed to add new meeting room.");
                break;
            case 3: // edit a meeting room
                System.out.print("Enter meeting room name to edit : ");
                String roomNameToEdit = bufferedReader.readLine();
                try {
                    MeetingRoom meetingRoomPrev = adminService.fetchMeetingRoomByName(roomNameToEdit);
                    System.out.println("Current Data : " + meetingRoomPrev);

                    System.out.println("Enter new data [to keep fields same put -] : ");
                    System.out.print("New Room Name : ");
                    String newRoomName = bufferedReader.readLine();
                    newRoomName = newRoomName.charAt(0)=='-' ? meetingRoomPrev.getMeetingRoomName() : newRoomName;

                    System.out.print("New Seating Capacity : ");
                    String newSeatingCapStr = bufferedReader.readLine();
                    int newSeatingCap = newSeatingCapStr.charAt(0)=='-' ?
                            meetingRoomPrev.getSeatingCapacity() :
                            Integer.parseInt(newSeatingCapStr);

                    System.out.print("New Ratings : ");
                    String newRatingsStr = bufferedReader.readLine();
                    float newRatings = newRatingsStr.charAt(0)=='-' ?
                            meetingRoomPrev.getSeatingCapacity() :
                            Float.parseFloat(newRatingsStr);

                    perHourCharge = meetingRoomPrev.getPerHourCost();
                    System.out.println("Want to add amenities ? [Y/N]");
                    char ch = bufferedReader.readLine().charAt(0);
                    if(ch=='Y')
                    {
                        amenitiesSet = selectAmenitiesMenuHandler();
                        perHourCharge = calculatePerHourCharges(amenitiesSet);
                    }
                    else
                        amenitiesSet = meetingRoomPrev.getAmenities();

                    MeetingRoom updatedMeetingRoom = new MeetingRoom(
                            newRoomName,
                            newSeatingCap,
                            newRatings,
                            amenitiesSet,
                            perHourCharge
                    );

                    if(adminService.editMeetingRoom(roomNameToEdit,updatedMeetingRoom))
                        System.out.println("Meeting room updated Successfully..!!");
                    else
                        System.out.println("Failed to update meeting room...!!");

                } catch (SQLException | MeetingRoomNotFoundException e) {
                    Logger.getLogger(AdminService.class.getName()).info(e.toString());
                }
                break;
            case 4: // show all meeting rooms
                try {
                    adminService.fetchAllMeetingRooms().forEach(System.out::println);
                } catch (SQLException e) {
                    Logger.getLogger(AdminService.class.getName()).info(e.toString());
                }
                break;
            case 5:// exit from app
                Main.exitApp();
            default:
                System.out.println("Invalid option...Please choose again.");
        }
    }

    private static void showAmenitiesMenu() {
        System.out.println("1. PROJECTOR");
        System.out.println("2. WIFI_CONNECTION");
        System.out.println("3. CONFERENCE_CALL_FACILITY");
        System.out.println("4. WHITEBOARD");
        System.out.println("5. WATER_DISPENSER");
        System.out.println("6. TV");
        System.out.println("7. COFFEE_MACHINE");
    }

    private static Set<Amenities> selectAmenitiesMenuHandler() throws IOException {
        /// selecting amenities
        System.out.println("Select Amenities :");
        showAmenitiesMenu();
        System.out.println("Enter choices (amenity options nos. separated by ',') : ");
        String[] amenitiesChoices = bufferedReader.readLine().split(",");
        Set<Amenities> amenitiesSet = new HashSet<>();

        /// Adding amenities to the set
        for(String s : amenitiesChoices)
        {
            switch (Integer.parseInt(s)){
                case 1:
                    amenitiesSet.add(Amenities.PROJECTOR);
                    break;
                case 2:
                    amenitiesSet.add(Amenities.WIFI_CONNECTION);
                    break;
                case 3:
                    amenitiesSet.add(Amenities.CONFERENCE_CALL_FACILITY);
                    break;
                case 4:
                    amenitiesSet.add(Amenities.WHITEBOARD);
                    break;
                case 5:
                    amenitiesSet.add(Amenities.WATER_DISPENSER);
                    break;
                case 6:
                    amenitiesSet.add(Amenities.TV);
                    break;
                case 7:
                    amenitiesSet.add(Amenities.COFFEE_MACHINE);
                    break;
            }
        }// end for loop
        return amenitiesSet;
    }

    private static int calculatePerHourCharges(Set<Amenities> amenitiesList) {
        return amenitiesList.stream()
                .map(Amenities::getAmenityCost)
                .reduce(0, Integer::sum);
    }
}
