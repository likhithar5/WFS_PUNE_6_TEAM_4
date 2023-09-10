package databaseTests;

import persistence.database.Database;
import persistence.database.DatabaseFactory;
import enums.DatabaseProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectionTest {
    public static void main(String[] args) {
        Database database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);

        assert database != null;
        try {

            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Amenities");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
                System.out.println(rs.getString(1) + "-->" + rs.getString(2));
        }catch (SQLException e) {
            System.out.println("[SQL EXCEPTION MESSAGE]" + e.getMessage());
            System.out.println("[SQL EXCEPTION ERROR CODE]" + e.getErrorCode());
        }
    }
}
