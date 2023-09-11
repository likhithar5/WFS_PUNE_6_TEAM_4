package beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.StringJoiner;

public class Booking {
	/// primary key for Booking
	private int bookingId;

	/// refers to the PK of the meeting room which has been booked under this
	/// booking
	private String bookedForMeetingRoom;
	private LocalDate dateOfBooking;
	private LocalTime startTime;
	private LocalTime endTime;

	/// refers to the PK of the User who has made the booking
	private int bookingDoneBy;

	/// default constructor
	public Booking() {
	}

	/// parameterized constructor
	public Booking(int bookingId, String bookedForMeetingRoom, LocalDate dateOfBooking, LocalTime startTime,
			LocalTime endTime, int bookingDoneBy) {
		this.bookingId = bookingId;
		this.bookedForMeetingRoom = bookedForMeetingRoom;
		this.dateOfBooking = dateOfBooking;
		this.startTime = startTime;
		this.endTime = endTime;
		this.bookingDoneBy = bookingDoneBy;
	}

	/// getters and setters
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookedForMeetingRoom() {
		return bookedForMeetingRoom;
	}

	public void setBookedForMeetingRoom(String bookedForMeetingRoom) {
		this.bookedForMeetingRoom = bookedForMeetingRoom;
	}

	public LocalDate getDateOfBooking() {
		return dateOfBooking;
	}

	public void setDateOfBooking(LocalDate dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public int getBookingDoneBy() {
		return bookingDoneBy;
	}

	public void setBookingDoneBy(int bookingDoneBy) {
		this.bookingDoneBy = bookingDoneBy;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Booking.class.getSimpleName() + "[", "]").add("bookingId=" + bookingId)
				.add("bookedForMeetingRoom='" + bookedForMeetingRoom + "'").add("dateOfBooking=" + dateOfBooking)
				.add("startTime=" + startTime).add("endTime=" + endTime).add("bookingDoneBy=" + bookingDoneBy)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		return (obj instanceof Booking && ((Booking) obj).bookingId == bookingId);
	}

	@Override
	public int hashCode() {
		return bookingId;
	}
}
