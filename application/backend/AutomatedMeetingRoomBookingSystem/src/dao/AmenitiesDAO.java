package dao;

import enums.Amenities;

import java.sql.SQLException;

public interface AmenitiesDAO {
    boolean addAmenity(String meetingRoomName, Amenities amenity)throws SQLException;
}
