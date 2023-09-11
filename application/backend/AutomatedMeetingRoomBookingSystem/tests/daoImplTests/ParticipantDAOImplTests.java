package daoImplTests;

import beans.Participant;
import dao.ParticipantsDAO;
import dao.ParticipantDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class ParticipantDAOImplTests {
    public static void main(String[] args) {
        ParticipantsDAO participantsDAO = new ParticipantDAOImpl();
        List<Participant> participantListToInsert = List.of(
                new Participant(1004,2),
                new Participant(1003,2),
                new Participant(1002,2)
        );
        givenListOfParticipantsOfMeeting_whenInserted_ThenAllSaved(participantListToInsert, participantsDAO);

        List<Participant> participantListToDelete = List.of(
                new Participant(1004,2),
                new Participant(1002,2)
        );
        givenListOfParticipantsOfMeeting_whenDeleted_ThenAllRemoved(participantListToDelete,participantsDAO);
    }

    private static void givenListOfParticipantsOfMeeting_whenInserted_ThenAllSaved(
            List<Participant> participantListToInsert, ParticipantsDAO participantsDAO){
        try{
            if(participantsDAO.add(participantListToInsert))
                System.out.println("All participants are added.");
            else
                System.out.println("Failed to add all participants.");
        }catch (SQLException e){
            System.out.println("e.getMessage() = " + e.getMessage());
            System.out.println("Failed to insert...!!");
        }
    }

    private static void givenListOfParticipantsOfMeeting_whenDeleted_ThenAllRemoved(
            List<Participant> participantListToInsert, ParticipantsDAO participantsDAO){
        try{
            if(participantsDAO.delete(participantListToInsert))
                System.out.println("All specified participants are deleted.");
            else
                System.out.println("Failed to delete all the specified participants.");
        }catch (SQLException e){
            System.out.println("e.getMessage() = " + e.getMessage());
            System.out.println("Failed to delete...!!");
        }
    }
}
