package dao;

import beans.Participant;
import enums.DatabaseProduct;
import dao.ParticipantsDAO;
import persistence.database.Database;
import persistence.database.DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ParticipantDAOImpl implements ParticipantsDAO {
    private static final String INSERT_PARTICIPANT =
            "INSERT INTO PARTICIPANTS (userId,meetingId) VALUES (?,?)";

    private static final String DELETE_PARTICIPANT =
            "DELETE FROM PARTICIPANTS WHERE userId = ? AND meetingId = ?";

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
}
