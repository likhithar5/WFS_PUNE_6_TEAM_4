package Utility;

import dao.MeetingDao;
import dao.MeetingDaoImpl;

public class DaoFactory {

	public static MeetingDao getMeetingDaoInstance() {
		return new MeetingDaoImpl();
	}

}
