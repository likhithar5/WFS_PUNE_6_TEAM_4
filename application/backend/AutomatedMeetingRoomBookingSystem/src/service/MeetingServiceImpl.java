package service;

import java.util.List;

import Utility.DaoFactory;
import beans.Meeting;
import dao.MeetingDao;
import exceptions.MeetingNotFoundException;

public class MeetingServiceImpl implements MeetingService {
	private MeetingDao meetingDao;

	public MeetingServiceImpl() {
		meetingDao = DaoFactory.getMeetingDaoInstance();
	}

	@Override
	public int createMeeting(Meeting meeting) {
		return 0;
	}

	@Override
	public List<Meeting> fetchAllMeetings() {
		return meetingDao.fetchAllMeetings();
	}

	@Override
	public Meeting fetchMeetingById(int meetingId) {
		try {
			return meetingDao.fetchMeetingById(meetingId);
		} catch (MeetingNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Meeting> fetchMeetingsByOrganizerId(int meetingId) {
		return meetingDao.fetchMeetingsByOrganizerId(meetingId);
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
