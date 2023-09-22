package service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import beans.Meeting;
import enums.MeetingType;

public interface ManagerService {
	int createMeeting(Meeting meeting) throws SQLException;
	
	List<Meeting> getOrganizedByManager(int managerId) throws SQLException;
	
	void editMeeting(Meeting meeting);

	void deleteMeeting(int uniqueId);

	List<String> getAvailableRooms(MeetingType meetingType, int seatsRequired, LocalDate meetingDate,
			LocalTime startTime, int durationInMinutes);

	


}
