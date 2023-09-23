package dao;

import enums.Amenities;

import java.sql.SQLException;
import java.util.Set;

public interface AmenitiesDAO {
    boolean addAmenity(String meetingRoomName, Amenities amenity)throws SQLException;
    Set<Amenities> getAmenitiesByMeetingRoomName(String roomName)throws SQLException;
}
