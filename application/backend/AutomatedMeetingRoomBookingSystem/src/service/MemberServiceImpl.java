package service;

import beans.Meeting;
import dao.ParticipantDAOImpl;
import dao.ParticipantsDAO;
import exceptions.MeetingNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService{
    private final MeetingService meetingService;
    private final ParticipantsDAO participantsDAO;

    public MemberServiceImpl() {
        meetingService = new MeetingServiceImpl();
        participantsDAO = new ParticipantDAOImpl();
    }

    @Override
    public List<Meeting> getMeetingsScheduled(int userId) throws SQLException {
        List<Integer> meetingIdsScheduledForParticipant = participantsDAO.getMeetingIdsFor(userId);
        List<Meeting> meetingsScheduledForParticipant = meetingIdsScheduledForParticipant.stream()
                .map(meetingId -> {
                    try {
                        return meetingService.fetchMeetingById(meetingId);
                    } catch (MeetingNotFoundException e) {
                        Logger.getLogger(this.getClass().getName()).info(e.getMessage());
                        return null;
                    }
                }).collect(Collectors.toList());
        return meetingsScheduledForParticipant;
    }

    @Override
    public Meeting getMeetingInfoOf(int meetingId) throws SQLException, MeetingNotFoundException {
        Meeting meeting = meetingService.fetchMeetingById(meetingId);
        List<Integer> participantsOfMeeting = participantsDAO.getByMeetingId(meetingId);
        meeting.setParticipants(participantsOfMeeting);
        return meeting;
    }
}
