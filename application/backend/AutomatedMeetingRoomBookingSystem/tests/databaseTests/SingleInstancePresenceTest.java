package databaseTests;

import persistence.database.Database;
import persistence.database.DatabaseFactory;
import enums.DatabaseProduct;

public class SingleInstancePresenceTest {
    public static void main(String[] args) {
        Database database1 = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);
        Database database2 = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);

        assert database1==database2;
        System.out.println("database1 = " + database1);
        System.out.println("database2 = " + database2);
    }
}
