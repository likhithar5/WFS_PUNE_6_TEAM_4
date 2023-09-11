package dao;

import beans.Booking;
import enums.DatabaseProduct;
import exceptions.BookingNotFoundException;
import persistence.database.Database;
import persistence.database.DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookingsDAOImpl implements BookingsDAO {

    private static final String CREATE_NEW_BOOKING =
            "INSERT INTO BOOKINGS (meetingRoom, meetingDate, startTime, endTime, bookedBy) VALUES (?,?,?,?,?)";
    private static final String GET_BOOKING_BY_ID = "SELECT * FROM BOOKINGS WHERE id = ?";

    private static final String GET_ALL_BOOKINGS =
            "SELECT * FROM BOOKINGS ORDER BY meetingDate ASC, startTime ASC, endTime ASC";

    private static final String MODIFY_BOOKING =
            "UPDATE BOOKINGS SET meetingRoom = ?, meetingDate = ?, startTime = ?, endTime = ?, bookedBy = ? WHERE id = ?";

    private static final String DELETE_BOOKING = "DELETE FROM BOOKINGS WHERE id = ?";
    private final Connection connection;

    public BookingsDAOImpl() {
        try{
            Database database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);
            if(database==null)
                throw new RuntimeException("Database is null");
            database.createDatabase();
            connection = database.getConnection();
        } catch (Exception e) {
            Logger.getLogger(BookingsDAOImpl.class.getName())
                    .severe("Failed to created database : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean create(Booking booking) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_BOOKING)){
            preparedStatement.setString(1,booking.getBookedForMeetingRoom());
            preparedStatement.setObject(2, booking.getDateOfBooking());
            preparedStatement.setObject(3,booking.getStartTime());
            preparedStatement.setObject(4,booking.getEndTime());
            preparedStatement.setInt(5,booking.getBookingDoneBy());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public Booking getBookingById(int bookingId) throws SQLException, BookingNotFoundException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOKING_BY_ID)){
            preparedStatement.setInt(1,bookingId);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
                return new Booking(
                        rs.getInt("id"),
                        rs.getString("meetingRoom"),
                        rs.getDate("meetingDate").toLocalDate(),
                        rs.getTime("startTime").toLocalTime(),
                        rs.getTime("endTime").toLocalTime(),
                        rs.getInt("bookedBy")
                );
            throw new BookingNotFoundException("Booking with the specified ID does not exist.");
        }
    }

    @Override
    public List<Booking> getAllBookings() throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOKINGS)){
            ResultSet rs = preparedStatement.executeQuery();
            List<Booking> bookingList = new ArrayList<>();
            while(rs.next()){
                bookingList.add(
                        new Booking(
                                rs.getInt("id"),
                                rs.getString("meetingRoom"),
                                rs.getDate("meetingDate").toLocalDate(),
                                rs.getTime("startTime").toLocalTime(),
                                rs.getTime("endTime").toLocalTime(),
                                rs.getInt("bookedBy")
                        )
                );
            }
            return bookingList;
        }
    }

    @Override
    public boolean modify(int bookingId, Booking updatedBooking) throws SQLException, BookingNotFoundException {
        getBookingById(bookingId);
        try(PreparedStatement preparedStatement = connection.prepareStatement(MODIFY_BOOKING)){
            preparedStatement.setString(1,updatedBooking.getBookedForMeetingRoom());
            preparedStatement.setObject(2, updatedBooking.getDateOfBooking());
            preparedStatement.setObject(3,updatedBooking.getStartTime());
            preparedStatement.setObject(4,updatedBooking.getEndTime());
            preparedStatement.setInt(5,updatedBooking.getBookingDoneBy());
            preparedStatement.setInt(6,bookingId);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int bookingId) throws SQLException, BookingNotFoundException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOKING)){
            preparedStatement.setLong(1,bookingId);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected<=0)
                throw new BookingNotFoundException("Booking does not exist");
            return true;
        }
    }
}
