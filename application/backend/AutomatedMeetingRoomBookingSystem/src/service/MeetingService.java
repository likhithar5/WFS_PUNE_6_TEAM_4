package service;

import java.util.List;

import beans.Meeting;
import exceptions.MeetingNotFoundException;

public interface MeetingService {
	public int createMeeting(Meeting meeting);

	public List<Meeting> fetchAllMeetings();

	public Meeting fetchMeetingById(int meetingId);

	List<Meeting> fetchMeetingsByOrganizerId(int organizerId);

	public boolean deleteMeetingById(int meetingId);

	public boolean updateMeeting(Meeting meeting);
}
