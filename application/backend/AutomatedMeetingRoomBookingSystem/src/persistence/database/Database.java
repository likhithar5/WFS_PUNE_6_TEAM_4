package persistence.database;

import java.sql.Connection;

public interface Database {
    void createConnection();

    Connection getConnection();
}
