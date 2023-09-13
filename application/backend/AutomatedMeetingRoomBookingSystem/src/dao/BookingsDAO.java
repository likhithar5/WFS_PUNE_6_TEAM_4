package dao;

import beans.Booking;
import exceptions.BookingNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BookingsDAO {

    /**
     * Takes an instance of the booking object and persist it in the database.
     * 
     * @param booking The instance of booking object that has to be persisted.
     * @return boolean
     * @throws SQLException For any Sql related exceptions.
     * @author Sayantan Das
     */
    public boolean create(Booking booking) throws SQLException;

    /**
     * Fetches a booking record that matches with the id given by the user.
     * 
     * @param bookingId The id of the booking record that has to be fetched from the
     *                  database.
     * @return Booking
     * @throws SQLException             For any Sql related exceptions.
     * @throws BookingNotFoundException When the requested booking with given id
     *                                  does not exist.
     * @author Sayantan Das
     */
    public Booking getBookingById(int bookingId) throws SQLException, BookingNotFoundException;

    /**
     * Fetches a set of booking records from the database.
     * 
     * @return List of Booking objects
     * @throws SQLException For any Sql related exceptions.
     * @author Sayantan Das
     */
    public List<Booking> getAllBookings() throws SQLException;

    Map<String, List<Booking>> getBookingsByMeetingRoomName(List<String> meetingRoomNames) throws SQLException;

    /**
     * Updates the booking record that is specified by the id and
     * sets updated values into that record in the database.
     * 
     * @param bookingId      The id of the booking record that has to be updated.
     * @param updatedBooking The booking object containing the updated values
     *                       that has to be persisted into the database.
     * @return boolean
     * @throws SQLException             For any Sql related exceptions.
     * @throws BookingNotFoundException When the requested booking with given id
     *                                  does not exist.
     * @author Sayantan Das
     */
    public boolean modify(int bookingId, Booking updatedBooking) throws SQLException, BookingNotFoundException;

    /**
     * Deletes a booking record from the database identified by the id.
     * 
     * @param bookingId The id of the booking record that to be deleted.
     * @return boolean
     * @throws SQLException             For any Sql related exceptions.
     * @throws BookingNotFoundException When the requested booking with given id
     *                                  does not exist.
     * @author Sayantan Das
     */
    public boolean delete(int bookingId) throws SQLException, BookingNotFoundException;
}
