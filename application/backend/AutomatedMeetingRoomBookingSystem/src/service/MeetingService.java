package service;

import beans.Meeting;
import exceptions.MeetingNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface MeetingService {
	public int createMeeting(Meeting meeting) throws SQLException;

	public List<Meeting> fetchAllMeetings();

	public Meeting fetchMeetingById(int meetingId) throws MeetingNotFoundException;

	List<Meeting> fetchMeetingsByOrganizerId(int organizerId) throws SQLException;

	public boolean deleteMeetingById(int meetingId);

	public boolean updateMeeting(Meeting meeting);
}
