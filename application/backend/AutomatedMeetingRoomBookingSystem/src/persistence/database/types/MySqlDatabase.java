package persistence.database.types;

import persistence.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabase implements Database {
    private final String database;
    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final boolean useSSL;
    private final boolean autoReconnect;

    private static volatile MySqlDatabase mySqlDatabase;

    private static volatile Connection connection;

    public static MySqlDatabase getInstance(){
        if(mySqlDatabase==null){
            synchronized (MySqlDatabase.class){
                if(mySqlDatabase==null){
                    mySqlDatabase = new MySqlDatabase();
                }
            }
        }
        return mySqlDatabase;
    }
    private MySqlDatabase() {
        this.database = System.getenv("MYSQL_DATABASE");
        this.host = System.getenv("MYSQL_HOST");
        this.port = System.getenv("MYSQL_PORT");

        this.username = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.useSSL = Boolean.parseBoolean(System.getenv("MYSQL_USESSL"));
        this.autoReconnect = Boolean.parseBoolean(System.getenv("MYSQL_AUTORECONNECT"));
    }

    @Override
    public void createConnection() {
        try{
            if(connection==null) {
                synchronized (this) {
                    if(connection==null) {
                        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                        String databaseURL = String.format(
                                "jdbc:mysql://%s:%s/%s?useSSL=%s&autoReconnect=%s",
                                host,
                                port,
                                database,
                                useSSL,
                                autoReconnect
                        );
                        connection = DriverManager.getConnection(
                                databaseURL,
                                username,
                                password
                        );
                    }
                }
            }
        } catch (SQLException e) {
            //TODO: Add logging
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}

