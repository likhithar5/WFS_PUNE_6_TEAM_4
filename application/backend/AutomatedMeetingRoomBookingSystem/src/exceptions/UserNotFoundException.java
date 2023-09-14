package exceptions;

public class UserNotFoundException extends Exception{
	 public UserNotFoundException() {
	    	super();
	    }
	    public UserNotFoundException(String msg) {
	    	super(msg);
	    }
	    public UserNotFoundException(String msg,Throwable cause) {
	    	super(msg,cause);
	    }
}
