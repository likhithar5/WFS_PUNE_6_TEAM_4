package beans;

import enums.Amenities;

import java.util.Set;
import java.util.StringJoiner;

public class MeetingRoom {

	/// primary key for meeting room
	private String meetingRoomName;
	private int seatingCapacity;
	private float ratings;
	private Set<Amenities> amenities;
	private int perHourCost;
	private long numOfMeetingsHeld;

	/// default constructor
	public MeetingRoom() {
	}

	/// parameterized constructor
	public MeetingRoom(String meetingRoomName, int seatingCapacity, float ratings, Set<Amenities> amenities,
			int perHourCost) {
		this.meetingRoomName = meetingRoomName;
		this.seatingCapacity = seatingCapacity;
		this.ratings = ratings;
		this.amenities = amenities;
		this.perHourCost = perHourCost;
	}

	/// getters and setters
	public String getMeetingRoomName() {
		return meetingRoomName;
	}

	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public float getRatings() {
		return ratings;
	}

	public void setRatings(float ratings) {
		this.ratings = ratings;
	}

	public Set<Amenities> getAmenities() {
		return amenities;
	}

	public void setAmenities(Set<Amenities> amenities) {
		this.amenities = amenities;
	}

	public int getPerHourCost() {
		return perHourCost;
	}

	public void setPerHourCost(int perHourCost) {
		this.perHourCost = perHourCost;
	}

	public long getNumOfMeetingsHeld() {
		return numOfMeetingsHeld;
	}

	public void setNumOfMeetingsHeld(long numOfMeetingsHeld) {
		this.numOfMeetingsHeld = numOfMeetingsHeld;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", MeetingRoom.class.getSimpleName() + "[", "]")
				.add("meetingRoomName='" + meetingRoomName + "'").add("seatingCapacity=" + seatingCapacity)
				.add("ratings=" + ratings).add("amenities=" + amenities).add("perHourCost=" + perHourCost)
				.add("numOfMeetingsHeld=" + numOfMeetingsHeld).toString();
	}

	@Override
	public int hashCode() {
		return meetingRoomName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		return (obj instanceof MeetingRoom && ((MeetingRoom) obj).meetingRoomName.equals(this.meetingRoomName));
	}
}
