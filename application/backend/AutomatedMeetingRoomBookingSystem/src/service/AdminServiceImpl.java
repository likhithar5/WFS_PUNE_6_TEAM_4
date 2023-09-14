package service;

import beans.MeetingRoom;
import dao.AmenitiesDAO;
import dao.AmenitiesDAOImpl;
import dao.MeetingRoomDAO;
import dao.MeetingRoomDAOImpl;
import enums.Amenities;
import exceptions.MeetingRoomNotFoundException;

import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Logger;

public class AdminServiceImpl implements AdminService{
	private MeetingRoomService meetingRoomService;
	private AmenitiesDAO amenitiesDAO;
	MeetingRoomDAO meetingRoomDao;
	public AdminServiceImpl() {
		meetingRoomDao=new MeetingRoomDAOImpl();
		meetingRoomService = new MeetingRoomServiceImpl();
		try {
			amenitiesDAO = new AmenitiesDAOImpl();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public boolean createMeetingRoom(MeetingRoom room) {
		try {
			boolean isMeetingCreated = meetingRoomDao.create(room);
			Set<Amenities> setOfAmenities = room.getAmenities();
			try{
				for (Amenities a : setOfAmenities) {
					if(!amenitiesDAO.addAmenity(room.getMeetingRoomName(), a))
						return false;
				}
				return isMeetingCreated;
			}catch (Exception e){
				Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
				throw e;
			}
			
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

