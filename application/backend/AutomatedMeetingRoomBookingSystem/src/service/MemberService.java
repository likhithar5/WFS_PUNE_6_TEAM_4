package service;

import beans.Meeting;
import exceptions.MeetingNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface MemberService {
    List<Meeting> getMeetingsScheduled(int userId) throws SQLException;

    Meeting getMeetingInfoOf(int meetingId) throws SQLException, MeetingNotFoundException;
}
