package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import beans.Meeting;
import beans.User;
import enums.DatabaseProduct;
import enums.MeetingType;
import exceptions.MeetingNotFoundException;
import persistence.database.Database;
import persistence.database.DatabaseFactory;

public class MeetingDaoImpl implements MeetingDao {
	private static final String INSERT_MEETING = "insert into Meeting (meetingId, meetingTitle, organizerId, meetingDate, startTime, endTime, meetingType, meetingRoom, participants, meetingDescription) values (?,?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_MEETING_BY_ORGANIZERID = "Select meetingId, meetingTitle, organizerId, meetingDate, startTime, endTime, meetingType, meetingRoom, participants, meetingDescription from Meeting where organizerId = ?";
	private static final String SELECT_ALL_MEETINGS = "Select * from Meeting";

	private static final String SELECT_MEETING_BY_ID = "Select * From Meeting where meetingId = ?";
	private static final String UPDATE_MEETING = "UPDATE Meeting SET  meetingTtitle=?, meetingDate=?, startTime=?, endTime=?,  type=?, participants=?, meetingDescription=? WHERE meetingId=?";
	private static final String DELETE_MEETING_BY_ID = "DELETE FROM Meeting WHERE meetingId=?";
	private final Connection conn;
	Database database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);

	public MeetingDaoImpl() {
		conn = database.getConnection();
	}

//	public int createMeeting(Meeting meeting) {
//		int id = 0;
//		try {
//			List<String> availableRoomNames = bookingInformationService
//					.getAvailableMeetingRoom(meeting.getMeetingDate(), meeting.getStartTime(), meeting.getEndTime(),
//							MeetingType.valueOf(meeting.getMeetingType().name()))
//					.stream().map(m -> m.getRoomName()).collect(Collectors.toList());
//
//			if (availableRoomNames.contains(meeting.getMeetingRoom())) {
//				PreparedStatement statement = conn.prepareStatement(INSERT_MEETING, Statement.RETURN_GENERATED_KEYS);
//				statement.setString(1, meeting.getMeetingTitle());
//				statement.setInt(2, meeting.getOrganizerId());
//				statement.setString(3, meeting.getMeetingDate().toString());
//				statement.setString(4, meeting.getStartTime().toString());
//				statement.setString(5, meeting.getEndTime().toString());
//				statement.setString(6, meeting.getMeetingType().name());
//				statement.setString(7, meeting.getMeetingRoom());
//
//				statement.executeUpdate();
//
//				ResultSet rs = statement.getGeneratedKeys();
//				if (rs.next()) {
//					id = rs.getInt(1);
//				}
//
//				statement.close();
//
//				// Create members and store their names along with the meeting ID
//				for (Integer memberName : meeting.getParticipants()) {
//					createMember(id, memberName);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return id;
//	}
//
//	private void createMember(int meetingId, int memberName) {
//		try {
//			PreparedStatement statement = conn.prepareStatement(INSERT_MEMBER);
//			statement.setInt(1, meetingId);
//			statement.setString(2, memberName);
//			statement.executeUpdate();
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public List<Meeting> fetchAllMeetings() {
		List<Meeting> meetings = new ArrayList<>();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(SELECT_ALL_MEETINGS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Meeting meeting = mapToMeeting(rs);
				meetings.add(meeting);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return meetings;
	}

	@Override
	public Meeting fetchMeetingById(int meetingId) throws MeetingNotFoundException {
		Meeting meeting = null;
		try (PreparedStatement stmt = conn.prepareStatement(SELECT_MEETING_BY_ID);) {
			stmt.setInt(1, meetingId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				// Meeting found, create a Meeting object and set its properties
				meeting = mapToMeeting(result);

			} else {
				// Meeting not found, throw MeetingNotFoundException
				throw new MeetingNotFoundException("Meeting with ID not found.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return meeting;

	}

	@Override
	public List<Meeting> fetchMeetingsByOrganizerId(int managerId) {
		List<Meeting> meetings = new ArrayList<>();

		try (PreparedStatement statement = conn.prepareStatement(SELECT_MEETING_BY_ORGANIZERID);) {

			statement.setInt(1, managerId);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Meeting meeting = mapToMeeting(result);
				meetings.add(meeting);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return meetings;
	}

	@Override
	public boolean deleteMeetingById(int meetingId) throws MeetingNotFoundException {

		try (PreparedStatement stmt = conn.prepareStatement(DELETE_MEETING_BY_ID);) {

			stmt.setInt(1, meetingId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected == 0) {
				throw new MeetingNotFoundException("Meeting with ID not found.");
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateMeeting(Meeting meeting) {
		try (PreparedStatement statement = conn.prepareStatement(UPDATE_MEETING);
				ResultSet rs = statement.getGeneratedKeys()) {
			statement.setString(1, meeting.getMeetingTitle()); // Replace 'meeting' with your Meeting object
			statement.setString(2, meeting.getMeetingDate().toString());
			statement.setString(3, meeting.getStartTime().toString());
			statement.setString(4, meeting.getEndTime().toString());
			statement.setString(5, meeting.getMeetingType().toString());
			// Convert the comma-separated sFtring to a list of integers
			String participantsString = rs.getString(6);
			List<Integer> participants = Arrays.stream(participantsString.split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());
			meeting.setParticipants(participants);
			statement.setString(7, meeting.getMeetingDescription());
			statement.setInt(8, meeting.getMeetingId());

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected == 0) {
				throw new MeetingNotFoundException("Meeting with ID " + meeting.getMeetingId() + " not found.");
			}

			int id = 0;
			while (rs.next()) {
				id = rs.getInt(1);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	private Meeting mapToMeeting(ResultSet rs) throws SQLException {
		Meeting meeting = new Meeting();
		meeting.setMeetingId(rs.getInt(1));
		meeting.setMeetingTitle(rs.getString(2));
		meeting.setOrganizerId(rs.getInt(3));
		meeting.setMeetingDate(rs.getObject(4, LocalDate.class));
		meeting.setStartTime(rs.getTime(5).toLocalTime());
		meeting.setEndTime(rs.getTime(6).toLocalTime());
		meeting.setMeetingType(MeetingType.valueOf(rs.getString(7)));
		meeting.setMeetingRoom(rs.getString(8));

		// Convert the comma-separated sFtring to a list of integers
		String participantsString = rs.getString(9);
		List<Integer> participants = Arrays.stream(participantsString.split(",")).map(Integer::parseInt)
				.collect(Collectors.toList());
		meeting.setParticipants(participants);

		meeting.setMeetingDescription(rs.getString(10));

		return meeting;
	}

	@Override
	public int createMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		return 0;
	}

}
