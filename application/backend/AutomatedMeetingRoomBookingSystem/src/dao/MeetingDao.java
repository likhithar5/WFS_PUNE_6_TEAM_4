package dao;

import java.sql.SQLException;
import java.util.List;

import beans.Meeting;
import exceptions.MeetingNotFoundException;

public interface MeetingDao {
	public int createMeeting(Meeting meeting) throws SQLException;

	public List<Meeting> fetchAllMeetings();

	public Meeting fetchMeetingById(int meetingId) throws MeetingNotFoundException;

	List<Meeting> fetchMeetingsByOrganizerId(int meetingId);

	public boolean deleteMeetingById(int meetingId) throws MeetingNotFoundException;

	public boolean updateMeeting(Meeting meeting) throws MeetingNotFoundException;

}
