package persistence.database;

import enums.DatabaseProduct;
import exceptions.DatabaseNotSupportedException;
import persistence.database.types.MySqlDatabase;

public interface DatabaseFactory {

    /**
     * Method implementing factory patter to get a connection to the database
     * of user's choice from the given list
     * and other database configurations.
     *
     * @param databaseProduct The named constant of the Database that we need to create
     *
     * @return Connection
     *
     * @author Sayantan Das
     * */
    static Database getDatabaseOf(DatabaseProduct databaseProduct) {
        try{
            switch(databaseProduct){
                case MY_SQL:
                    Database mysqlDatabase = MySqlDatabase.getInstance();
                    mysqlDatabase.createDatabase();
                    return mysqlDatabase;
                case POSTGRES_SQL:
                case DERBY:
                case MARIADB:
                    throw new DatabaseNotSupportedException("This database is still not supported by the application");
                default:
                    return null;
            }
        } catch (DatabaseNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }
}
