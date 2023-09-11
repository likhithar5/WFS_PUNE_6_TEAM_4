package exceptions;

public class MeetingNotFoundException extends Exception {
	public MeetingNotFoundException(String msg) {
		System.out.println(msg);
	}
}
