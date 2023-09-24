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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BookingsDAOImpl implements BookingsDAO {

    private static final String CREATE_NEW_BOOKING =
            "INSERT INTO bookings (booking_for_meeting_room, date, startTime, endTime, booked_by) VALUES (?,?,?,?,?)";
    private static final String GET_BOOKING_BY_ID = "SELECT * FROM BOOKINGS WHERE id = ?";

    private static final String GET_ALL_BOOKINGS =
            "SELECT * FROM bookings ORDER BY date ASC, startTime ASC, endTime ASC";

    private static final String MODIFY_BOOKING =
            "UPDATE bookings SET booking_for_meeting_room = ?, date = ?, startTime = ?, endTime = ?, booked_by = ? WHERE id = ?";

    private static final String DELETE_BOOKING = "DELETE FROM bookings WHERE id = ?";
    private final Connection connection;

    public BookingsDAOImpl() {
        try{
            Database database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);
            if(database==null)
                throw new RuntimeException("Database is null");
            database.createConnection();
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
    public Map<String, List<Booking>> getBookingsByMeetingRoomName(List<String> meetingRoomNames) throws SQLException {
        final StringBuilder query =
                new StringBuilder("SELECT B.booking_for_meeting_room, B.date, B.startTime, B.endTime " +
                        "FROM bookings B INNER JOIN meeting_rooms MR on B.booking_for_meeting_room = MR.room_name " +
                        "WHERE B.booking_for_meeting_room in ("
                );

        /// dynamically preparing the prepared statement query parameter list
        int numOfParameters = meetingRoomNames.size();
        for(int i=0;i<numOfParameters;++i){
            query.append('?');
            if(i<numOfParameters-1)
                query.append(',');
        }
        query.append(") order by B.booking_for_meeting_room, B.date, B.startTime, B.endTime;");
        /*System.out.println("query : " + query);*/

        try(PreparedStatement preparedStatement = connection.prepareStatement(query.toString())){
            for(int i=0;i<numOfParameters;++i){
                preparedStatement.setString(i+1, meetingRoomNames.get(i));
            }
            ResultSet rs = preparedStatement.executeQuery();
            Map<String, List<Booking>> bookingsGroupedByMeetingRoomName = new HashMap<>();
            while(rs.next()){
                String roomName = rs.getString("booking_for_meeting_room");
                if(!bookingsGroupedByMeetingRoomName.containsKey(roomName))
                {
                    List<Booking> bookings = new ArrayList<>();
                    bookings.add(new Booking(
                            -1,
                            rs.getString("booking_for_meeting_room"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("startTime").toLocalTime(),
                            rs.getTime("endTime").toLocalTime(),
                            -1
                    ));
                    bookingsGroupedByMeetingRoomName.put(roomName, bookings);
                }
                else
                    bookingsGroupedByMeetingRoomName.get(roomName).add(
                            new Booking(
                                    -1,
                                    rs.getString("booking_for_meeting_room"),
                                    rs.getDate("date").toLocalDate(),
                                    rs.getTime("startTime").toLocalTime(),
                                    rs.getTime("endTime").toLocalTime(),
                                    -1
                            )
                    );
            }// end while loop
            return bookingsGroupedByMeetingRoomName;
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
