package service;

import Utility.DaoFactory;
import beans.Meeting;
import dao.MeetingDao;
import dao.ParticipantDAOImpl;
import dao.ParticipantsDAO;
import exceptions.MeetingNotFoundException;

import java.sql.SQLException;
import java.util.List;

public class MeetingServiceImpl implements MeetingService {
	private MeetingDao meetingDao;
	private ParticipantsDAO participantsDAO;

	public MeetingServiceImpl() {
		meetingDao = DaoFactory.getMeetingDaoInstance();
		participantsDAO = new ParticipantDAOImpl();
	}

	@Override
	public int createMeeting(Meeting meeting) throws SQLException {
		return meetingDao.createMeeting(meeting);
	}

	@Override
	public List<Meeting> fetchAllMeetings() {
		return meetingDao.fetchAllMeetings();
	}

	@Override
	public Meeting fetchMeetingById(int meetingId) throws MeetingNotFoundException {
		try {
			return meetingDao.fetchMeetingById(meetingId);
		} catch (MeetingNotFoundException e) {
			throw e;
		}
	}

	@Override
	public List<Meeting> fetchMeetingsByOrganizerId(int meetingId) throws SQLException {
		List<Meeting> meetings = meetingDao.fetchMeetingsByOrganizerId(meetingId);
		for(Meeting meeting : meetings){
			meeting.setParticipants(
					participantsDAO.getByMeetingId(meeting.getMeetingId())
			);
		}
		return meetings;
	}

	@Override
	public boolean deleteMeetingById(int meetingId) {
		try {
			return meetingDao.deleteMeetingById(meetingId);
		} catch (MeetingNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateMeeting(Meeting meeting) {
		try {
			return meetingDao.updateMeeting(meeting);
		} catch (MeetingNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return false;
		}
	}
}
