package service;

import beans.MeetingRoom;

public interface AdminService {
	
	public boolean createMeetingRoom(MeetingRoom room);
	
	public boolean editMeetingRoom(String meetingRoomNameToUpdate, MeetingRoom updatedMeetingRoom);

}
