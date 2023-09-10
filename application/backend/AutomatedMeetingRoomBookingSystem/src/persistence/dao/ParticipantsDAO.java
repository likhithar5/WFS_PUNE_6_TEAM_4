package persistence.dao;

import beans.Participant;

import java.sql.SQLException;
import java.util.List;

public interface ParticipantsDAO {
    boolean add(List<Participant> participantList) throws SQLException;
    boolean delete(List<Participant> participantList) throws SQLException;
}
