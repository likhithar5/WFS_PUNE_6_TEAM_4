package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import beans.Meeting;
import enums.DatabaseProduct;
import enums.MeetingType;
import exceptions.MeetingNotFoundException;
import persistence.database.Database;
import persistence.database.DatabaseFactory;

public class MeetingDaoImpl implements MeetingDao {
	private static final String SELECT_MEETING_BY_ORGANIZERID = "Select * from Meetings where organized_by = ?";
	private static final String SELECT_ALL_MEETINGS = "Select * from Meetings";

	private static final String SELECT_MEETING_BY_ID = "Select * From Meetings where id = ?";
	private static final String UPDATE_MEETING = "UPDATE Meetings SET  title=?, meeting_date=?, start_time=?, end_time=?,  meeting_type=?, description=? WHERE id=?";
	private static final String DELETE_MEETING_BY_ID = "DELETE FROM Meetings WHERE id=?";
	private final Connection conn;


	public MeetingDaoImpl() {
		Database database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);
		if (database==null)
			throw new RuntimeException("Error occurred in database. Database is null.");
		database.createConnection();
		conn = database.getConnection();
	}

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
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
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
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
			throw new MeetingNotFoundException("Meeting with the specified ID does not exists");
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
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
			throw new RuntimeException(e);
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
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateMeeting(Meeting meeting) throws  MeetingNotFoundException {
		try (PreparedStatement statement = conn.prepareStatement(UPDATE_MEETING);
				ResultSet rs = statement.getGeneratedKeys()) {
			statement.setString(1, meeting.getMeetingTitle()); // Replace 'meeting' with your Meeting object
			statement.setObject(2, meeting.getMeetingDate());
			statement.setObject(3, meeting.getStartTime());
			statement.setObject(4, meeting.getEndTime());
			statement.setString(5, meeting.getMeetingType().toString());
			statement.setString(6, meeting.getMeetingDescription());
			statement.setInt(7, meeting.getMeetingId());

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected == 0) {
				throw new MeetingNotFoundException("Meeting with ID " + meeting.getMeetingId() + " not found.");
			}

			int id = 0;
			while (rs.next()) {
				id = rs.getInt(1);
			}
			return true;
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private Meeting mapToMeeting(ResultSet rs) throws SQLException {
		Meeting meeting = new Meeting();
		meeting.setMeetingId(rs.getInt("id"));
		meeting.setMeetingTitle(rs.getString("title"));
		meeting.setOrganizerId(rs.getInt("organized_by"));
		meeting.setMeetingDate(rs.getObject("meeting_date", LocalDate.class));
		meeting.setStartTime(rs.getTime("start_time").toLocalTime());
		meeting.setEndTime(rs.getTime("end_time").toLocalTime());
		meeting.setMeetingType(MeetingType.valueOf(rs.getString("meeting_type")));
		meeting.setMeetingRoom(rs.getString("assigned_to_meeting_room"));

		// Convert the comma-separated sFtring to a list of integers
		/*String participantsString = rs.getString(9);
		List<Integer> participants = Arrays.stream(participantsString.split(",")).map(Integer::parseInt)
				.collect(Collectors.toList());
		meeting.setParticipants(participants);*/

		meeting.setMeetingDescription(rs.getString("description"));

		return meeting;
	}

	@Override
	public int createMeeting(Meeting meeting) throws SQLException {
		final String INSERT_MEETING_QUERY = new StringBuilder().append("INSERT INTO meetings")
				.append("(id, title, meeting_type, meeting_date, start_time, end_time, description, organized_by, assigned_to_meeting_room)")
				.append("VALUES (?, ?, ?, ?, ? ,? ,? ,? ,?)")
				.toString();

		try(PreparedStatement preparedStatement = conn.prepareStatement(INSERT_MEETING_QUERY)) {
			preparedStatement.setInt(1,meeting.getMeetingId());
			preparedStatement.setString(2, meeting.getMeetingTitle());
			preparedStatement.setString(3, meeting.getMeetingType().name());
			preparedStatement.setObject(4,meeting.getMeetingDate());
			preparedStatement.setObject(5,meeting.getStartTime());
			preparedStatement.setObject(6, meeting.getEndTime());
			preparedStatement.setString(7, meeting.getMeetingDescription());
			preparedStatement.setInt(8,meeting.getOrganizerId());
			preparedStatement.setString(9,meeting.getMeetingRoom());

			return preparedStatement.executeUpdate();

        }
	}

}
