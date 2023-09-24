package dao;

import beans.Participant;
import enums.DatabaseProduct;
import persistence.database.Database;
import persistence.database.DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ParticipantDAOImpl implements ParticipantsDAO {
    private static final String INSERT_PARTICIPANT =
            "INSERT INTO PARTICIPANTS (user_id,meeting_id) VALUES (?,?)";

    private static final String DELETE_PARTICIPANT =
            "DELETE FROM PARTICIPANTS WHERE userId = ? AND meetingId = ?";

    private  static final String GET_PARTICIPANTS_BY_MEETING_ID
            = "SELECT user_id FROM PARTICIPANTS WHERE meeting_id = ?";

    private  static final String GET_MEETING_IDs_FOR_PARTICIPANT
            = "SELECT meeting_id FROM PARTICIPANTS WHERE user_id = ?";

    private final Connection connection;

    public ParticipantDAOImpl() {
        Database database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);
        try{
            database.createDatabase();
            this.connection = database.getConnection();
        }catch (Exception e){
            Logger.getLogger(ParticipantDAOImpl.class.getName())
                    .severe("Failed to created database : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean add(List<Participant> participantList) throws SQLException {
        AtomicInteger rowsAffected = new AtomicInteger();
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PARTICIPANT)){
            participantList.forEach((participant -> {
                try {
                    preparedStatement.setObject(1,participant.getUserId());
                    preparedStatement.setObject(2,participant.getMeetingId());
                    rowsAffected.addAndGet(preparedStatement.executeUpdate());
                    preparedStatement.clearParameters();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        return rowsAffected.get() == participantList.size();
    }

    @Override
    public boolean delete(List<Participant> participantList)  throws SQLException {
        AtomicInteger rowsAffected = new AtomicInteger();
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PARTICIPANT)){
            participantList.forEach((participant -> {
                try {
                    preparedStatement.setObject(1,participant.getUserId());
                    preparedStatement.setObject(2,participant.getMeetingId());
                    rowsAffected.addAndGet(preparedStatement.executeUpdate());
                    preparedStatement.clearParameters();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        return rowsAffected.get() == participantList.size();
    }

    @Override
    public List<Integer> getByMeetingId(long meetingId) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_PARTICIPANTS_BY_MEETING_ID)){
            List<Integer> participantList = new ArrayList<>();
            preparedStatement.setLong(1, meetingId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
                participantList.add(rs.getInt("user_id"));
            return participantList;
        }
    }

    @Override
    public List<Integer> getMeetingIdsFor(int userId) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_MEETING_IDs_FOR_PARTICIPANT)){
            preparedStatement.setInt(1,userId);
            ResultSet rs = preparedStatement.executeQuery();
            List<Integer> meetingIds = new ArrayList<>();
            while(rs.next())
                meetingIds.add(rs.getInt("meeting_id"));
            return meetingIds;
        }
    }
}
