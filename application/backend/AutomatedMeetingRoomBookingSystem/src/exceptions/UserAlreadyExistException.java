package exceptions;

public class UserAlreadyExistException extends Exception{
	public UserAlreadyExistException() {
		super();
	}
	public UserAlreadyExistException(String msg) {
		super(msg);
	}
    public UserAlreadyExistException(String msg,Throwable cause) {
    	super(msg,cause);
    }
}
