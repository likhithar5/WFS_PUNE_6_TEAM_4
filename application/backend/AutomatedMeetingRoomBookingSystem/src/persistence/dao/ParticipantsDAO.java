package persistence.dao;

import beans.Participant;

import java.sql.SQLException;
import java.util.List;

public interface ParticipantsDAO {

    /**
     * Add one or more participants of a meeting into the database.
     * @param participantList : Entries of the participants in the form of List of (userId, meetingId) pairs
     *                        that needs to be persisted into the database.
     *
     * @return boolean : Represents whether all the entries in the list are successfully added.
     *
     * @throws SQLException : For any kind of SQL related Exception
     *
     * @author Sayantan Das
     * */
    boolean add(List<Participant> participantList) throws SQLException;

    /**
     * Deletes one or more participants of a meeting from the database.
     * @param participantList : Entries of the participants in the form of List of (userId, meetingId) pairs
     *                        that needs to be removed from the database.
     *
     * @return boolean : Represents whether all the entries in the list are successfully deleted.
     *
     * @throws SQLException : For any kind of SQL related Exception
     *
     * @author Sayantan Das
     * */
    boolean delete(List<Participant> participantList) throws SQLException;
}
