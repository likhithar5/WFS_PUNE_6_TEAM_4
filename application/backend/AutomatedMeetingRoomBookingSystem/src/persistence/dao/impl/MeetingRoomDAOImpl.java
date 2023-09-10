package persistence.dao.impl;

import beans.MeetingRoom;
import enums.DatabaseProduct;
import exceptions.MeetingRoomNotFoundException;
import persistence.dao.MeetingRoomDAO;
import persistence.database.Database;
import persistence.database.DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MeetingRoomDAOImpl implements MeetingRoomDAO {
    private static final String CREATE_NEW_MEETING_ROOM_ENTRY =
            "INSERT INTO MEETING_ROOM VALUES (?,?,?,?,?)";

    private static final String MODIFY_MEETING_ROOM =
            "UPDATE MEETING_ROOM " +
                    "SET seatingCapacity = ?, perHourCost = ?, ratings = ?, numberOfMeetingsHeld = ? " +
                    "WHERE meetingRoomName = ?";

    private static final String FETCH_MEETING_ROOM_BY_ID =
            "SELECT * FROM MEETING_ROOM WHERE meetingRoomName = ?";
    private final Connection connection;

    public MeetingRoomDAOImpl() {
        Database database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);
        try {
            if(database!=null)
                database.createDatabase();
            else throw new RuntimeException("Database is Null");
            this.connection = database.getConnection();
        } catch (Exception e) {
            Logger.getLogger(ParticipantDAOImpl.class.getName())
                    .severe("Failed to created database : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean create(MeetingRoom meetingRoom) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_MEETING_ROOM_ENTRY)) {
            preparedStatement.setString(1, meetingRoom.getMeetingRoomName());
            preparedStatement.setInt(2, meetingRoom.getSeatingCapacity());
            preparedStatement.setInt(3, meetingRoom.getPerHourCost());
            preparedStatement.setDouble(4, meetingRoom.getRatings());
            preparedStatement.setLong(5, meetingRoom.getNumOfMeetingsHeld());

            return  preparedStatement.executeUpdate()>0;
        }
    }

    @Override
    public boolean modify(String meetingRoomNameToUpdate, MeetingRoom updatedMeetingRoom)
            throws SQLException, MeetingRoomNotFoundException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MODIFY_MEETING_ROOM)) {
            if(getMeetingRoomById(meetingRoomNameToUpdate)==null)
                throw new MeetingRoomNotFoundException("The meeting room with the given name does not exist.");
            preparedStatement.setInt(1, updatedMeetingRoom.getSeatingCapacity());
            preparedStatement.setInt(2, updatedMeetingRoom.getPerHourCost());
            preparedStatement.setDouble(3, updatedMeetingRoom.getRatings());
            preparedStatement.setLong(4, updatedMeetingRoom.getNumOfMeetingsHeld());
            preparedStatement.setString(5, updatedMeetingRoom.getMeetingRoomName());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public MeetingRoom getMeetingRoomById(String meetingRoomName) throws SQLException, MeetingRoomNotFoundException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FETCH_MEETING_ROOM_BY_ID)) {
            preparedStatement.setString(1,meetingRoomName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new MeetingRoom(
                        rs.getString("meetingRoomName"),
                        rs.getInt("seatingCapacity"),
                        rs.getFloat("ratings"),
                        null,
                        rs.getInt("perHourCost")
                );
            } else
                throw new MeetingRoomNotFoundException("The meeting room with the given name does not exist.");
        }
    }
}
