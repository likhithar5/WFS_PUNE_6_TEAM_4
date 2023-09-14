package dao;

import enums.Amenities;
import enums.DatabaseProduct;
import persistence.database.Database;
import persistence.database.DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AmenitiesDAOImpl implements AmenitiesDAO{
    private final Connection connection;

    public AmenitiesDAOImpl() throws Exception {
        Database database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);
        if(database!=null){
            database.createDatabase();
            connection = database.getConnection();
        }else {
            Logger.getLogger(this.getClass().getName()).severe("Error occurred in creating database. Database is NULL.");
            throw  new Exception("Error occurred in creating database.");
        }
    }

    @Override
    public boolean addAmenity(String meetingRoomName, Amenities amenity) throws SQLException {
        final String INSERT_AMENITY_QUERY =
                "INSERT INTO amenities (meeting_room_name,amenities) values (?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AMENITY_QUERY)){
            preparedStatement.setString(1,meetingRoomName);
            preparedStatement.setString(2,amenity.name());
            return preparedStatement.executeUpdate()>0;
        }
    }
}
