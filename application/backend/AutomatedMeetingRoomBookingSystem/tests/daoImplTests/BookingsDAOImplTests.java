package daoImplTests;

import beans.Booking;
import dao.BookingsDAO;
import exceptions.BookingNotFoundException;
import dao.BookingsDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class BookingsDAOImplTests {
    private static BookingsDAO bookingsDAO;
    public static void main(String[] args) {
        bookingsDAO = new BookingsDAOImpl();
        /*givenABooking_whenInserted_ThenPersisted();
        givenBookingId_whenQueried_ThenFetchedBooking();
        givenWrongBookingId_whenQueried_ThenShowErrorMessage();
        whenQueriedForAll_ThenFetchedAllBookingsInAscOrder();
        givenBookingId_whenDeletedQueryFired_ThenDeleted();
        givenWrongBookingId_whenDeletedQueryFired_ThenShowedErrorMessage();
        givenBookingIdAndUpdatedBooking_whenUpdateQueryFired_ThenUpdatedInDatabase();
        givenWrongBookingId_whenUpdateQueryFired_ThenErrorMessageGiven();*/
        givenMeetingRooms_whenQueried_ThenBookingsGroupedByRoomName();
    }

    private static void givenABooking_whenInserted_ThenPersisted() {
        try{
            boolean flag = bookingsDAO.create(
                    new Booking(
                            200,
                            "ROOM_7-LVL_4-PUN",
                            LocalDate.of(2023, Month.NOVEMBER, 15),
                            LocalTime.of(14, 20),
                            LocalTime.of(15, 30),
                            1001
                    )
            );
            if(flag)
                System.out.println("Success");
            else
                System.out.println("Failure");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void givenBookingId_whenQueried_ThenFetchedBooking(){
        try{
            Booking booking = bookingsDAO.getBookingById(3);
            if(booking!=null)
                System.out.println(booking);
        } catch (BookingNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void givenWrongBookingId_whenQueried_ThenShowErrorMessage() {
        try{
            Booking booking = bookingsDAO.getBookingById(1);
            if(booking!=null)
                System.out.println(booking);
        } catch (BookingNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void whenQueriedForAll_ThenFetchedAllBookingsInAscOrder(){
        try{
            List<Booking> bookingList = bookingsDAO.getAllBookings();
            if(!bookingList.isEmpty())
                bookingList.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void givenBookingIdAndUpdatedBooking_whenUpdateQueryFired_ThenUpdatedInDatabase(){
        int bookingId = 4;
        Booking updatedBooking = new Booking(
                bookingId,
                "ROOM_2-LVL_6-PUN",
                LocalDate.of(2023, Month.OCTOBER, 20),
                LocalTime.of(11, 20),
                LocalTime.of(1, 0),
                1001
        );
        try{
            if(bookingsDAO.modify(bookingId,updatedBooking))
                System.out.println("Successfully modified...!!");
        } catch (BookingNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void givenWrongBookingId_whenUpdateQueryFired_ThenErrorMessageGiven(){
        int bookingId = 2;
        Booking updatedBooking = new Booking(
                bookingId,
                "ROOM_2-LVL_6-PUN",
                LocalDate.of(2023, Month.OCTOBER, 20),
                LocalTime.of(11, 20),
                LocalTime.of(1, 0),
                1001
        );
        try{
            if(bookingsDAO.modify(bookingId,updatedBooking))
                System.out.println("Successfully modified...!!");
        } catch (BookingNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void givenBookingId_whenDeletedQueryFired_ThenDeleted(){
        try{
            if(bookingsDAO.delete(3))
                System.out.println("Successfully deleted");
        } catch (BookingNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void givenWrongBookingId_whenDeletedQueryFired_ThenShowedErrorMessage(){
        try{
            bookingsDAO.delete(1);
        } catch (BookingNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void givenMeetingRooms_whenQueried_ThenBookingsGroupedByRoomName(){
        try {
            List<String> roomNames = List.of("ROOM_3-LVL_3-HYD","ROOM_2-LVL_3-HYD");
            Map<String, List<Booking>> map = bookingsDAO.getBookingsByMeetingRoomName(roomNames);
            for(Map.Entry<String, List<Booking>> entry : map.entrySet()){
                System.out.println(entry.getKey() + " --> " + entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
