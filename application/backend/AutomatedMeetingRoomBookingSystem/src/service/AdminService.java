package service;

import beans.MeetingRoom;
import exceptions.MeetingRoomNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface AdminService {
	
	public boolean createMeetingRoom(MeetingRoom room);
	
	public boolean editMeetingRoom(String meetingRoomNameToUpdate, MeetingRoom updatedMeetingRoom);

	List<MeetingRoom> fetchAllMeetingRooms() throws SQLException;

	MeetingRoom fetchMeetingRoomByName(String meetingRoomName) throws SQLException, MeetingRoomNotFoundException;

}
