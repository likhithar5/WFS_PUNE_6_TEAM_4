package service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import beans.Meeting;
import beans.MeetingRoom;
import enums.MeetingType;

public class ManagerServiceImpl implements ManagerService{
	
	private MeetingRoomService meetingRoomService;
	private MeetingService meetingService;
	private BookingService bookingService;
	
	public ManagerServiceImpl() {
		super();
		meetingService =new MeetingServiceImpl();
		meetingRoomService=new MeetingRoomServiceImpl();
		bookingService=new BookingServiceImpl();
	}

	@Override
	public int createMeeting(Meeting meeting) throws SQLException {
		return meetingService.createMeeting(meeting);
	}

	@Override
	public List<String> getAvailableRooms(MeetingType meetingType, int seatsRequired,LocalDate meetingDate,LocalTime startTime,int durationInMinutes) {
		try {
			List<MeetingRoom> list1=meetingRoomService.findMeetingRoomsBasedOnUserRequest(meetingType, seatsRequired);
			List<String> availableRooms=bookingService.findRoomsAvailableOnGivenDateAndTime(list1, meetingDate, startTime, durationInMinutes);
			return availableRooms;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}

	@Override
	public List<Meeting> getOrganizedByManager(int managerId) throws SQLException {
		List<Meeting> schedules = meetingService.fetchMeetingsByOrganizerId(managerId);
		return schedules;
	}
	
	@Override
	public void deleteMeeting(int uniqueId) {
		meetingService.deleteMeetingById(uniqueId);
	}

	@Override
	public void editMeeting(Meeting meeting) {
		
		meetingService.updateMeeting(meeting);
		
	}



}
