package dao;

import beans.MeetingRoom;
import enums.Amenities;
import enums.DatabaseProduct;
import exceptions.MeetingRoomNotFoundException;
import persistence.database.Database;
import persistence.database.DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class MeetingRoomDAOImpl implements MeetingRoomDAO {
    private static final String CREATE_NEW_MEETING_ROOM_ENTRY = "INSERT INTO MEETING_ROOMS (room_name, seating_capacity, ratings, hourly_cost, number_of_metings_held) VALUES (?,?,?,?,?)";

    private static final String MODIFY_MEETING_ROOM = "UPDATE MEETING_ROOM " +
            "SET seatingCapacity = ?, perHourCost = ?, ratings = ?, numberOfMeetingsHeld = ? " +
            "WHERE meetingRoomName = ?";

    private static final String FETCH_MEETING_ROOM_BY_ID = "SELECT * FROM MEETING_ROOM WHERE meetingRoomName = ?";
    private final Connection connection;

    public MeetingRoomDAOImpl() {
        Database database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);
        try {
            if (database != null)
                database.createDatabase();
            else
                throw new RuntimeException("Database is Null");
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
            preparedStatement.setDouble(3, meetingRoom.getRatings());
            preparedStatement.setInt(4, meetingRoom.getPerHourCost());
            preparedStatement.setLong(5, meetingRoom.getNumOfMeetingsHeld());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean modify(String meetingRoomNameToUpdate, MeetingRoom updatedMeetingRoom)
            throws SQLException, MeetingRoomNotFoundException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MODIFY_MEETING_ROOM)) {
            if (getMeetingRoomById(meetingRoomNameToUpdate) == null)
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
            preparedStatement.setString(1, meetingRoomName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new MeetingRoom(
                        rs.getString("meetingRoomName"),
                        rs.getInt("seatingCapacity"),
                        rs.getFloat("ratings"),
                        null,
                        rs.getInt("perHourCost"));
            } else
                throw new MeetingRoomNotFoundException("The meeting room with the given name does not exist.");
        }
    }

    @Override
    public List<MeetingRoom> findByAmenities(List<Amenities> expectedAmenities) throws SQLException {
        final StringBuilder query =
                new StringBuilder("select amenities.meeting_room_name, seatingCapacity, perHourCost, ratings, numberOfMeetingsHeld, amenities.amenities " +
                        "from amenities inner join meeting_room on amenities.meeting_room_name = meeting_room.meetingRoomName " +
                        "where amenities.amenities in (");
        for(int i=0;i<expectedAmenities.size();++i){
            query.append("?");
            if(i!= expectedAmenities.size()-1)
                query.append(',');
        }
        query.append(')');
        PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        for(int i=0;i<expectedAmenities.size();++i){
            preparedStatement.setString((i+1), expectedAmenities.get(i).toString());
        }
        ResultSet rs = preparedStatement.executeQuery();
        Map<String,MeetingRoom> meetingRoomMap = new HashMap<>();
        while(rs.next()){
            String id = rs.getString("meeting_room_name");
            if(meetingRoomMap.containsKey(id))
                meetingRoomMap.get(id).getAmenities().add(Amenities.valueOf(rs.getString("amenities")));
            else{
                Set<Amenities> amenities = new HashSet<>();
                amenities.add(Amenities.valueOf(rs.getString("amenities")));
                meetingRoomMap.put(
                        id,
                        new MeetingRoom(
                                id,
                                rs.getInt("seatingCapacity"),
                                rs.getFloat("ratings"),
                                amenities,
                                rs.getInt("perHourCost")
                        )
                );
            }
        }// end of while
        return new ArrayList<>(meetingRoomMap.values());
    }
}
