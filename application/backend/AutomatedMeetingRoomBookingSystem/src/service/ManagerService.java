package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import beans.Meeting;
import beans.MeetingRoom;
import enums.MeetingType;

public interface ManagerService {
	void createMeeting(Meeting meeting);
	
	List<Meeting> getOrganizedByManager(int managerId);
	
	void editMeeting(Meeting meeting);

	void deleteMeeting(int uniqueId);

	List<String> getAvailableRooms(MeetingType meetingType, int seatsRequired, LocalDate meetingDate,
			LocalTime startTime, int durationInMinutes);

	


}
