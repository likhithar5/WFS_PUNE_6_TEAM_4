package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.log4j.BasicConfigurator;

import beans.MeetingRoom;
import dao.MeetingRoomDAO;
import dao.MeetingRoomDAOImpl;
import exceptions.MeetingNotFoundException;
import exceptions.MeetingRoomNotFoundException;

public class AdminServiceImpl implements AdminService{
	MeetingRoomDAO meetingRoomDao;
	public AdminServiceImpl() {
		meetingRoomDao=new MeetingRoomDAOImpl();

	}

	public boolean createMeetingRoom(MeetingRoom room) {
		try {
			return meetingRoomDao.create(room);
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			
		}
		return false;
	}

	public boolean editMeetingRoom(String meetingRoomNameToUpdate, MeetingRoom updatedMeetingRoom) {
		try {
			return meetingRoomDao.modify(meetingRoomNameToUpdate, updatedMeetingRoom);
		} catch (MeetingRoomNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


}

