package exceptions;

public class DatabaseNotSupportedException extends Throwable {
    public DatabaseNotSupportedException(String s) {
        super(s, new Throwable("Database support is still under development"));
    }
}
