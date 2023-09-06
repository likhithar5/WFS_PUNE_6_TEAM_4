package com.demo.beans;

import java.util.Date;
import java.util.List;

public class Meeting {
	private String id;
	private String title;
	private User organizer; // Reference to the User who organized the meeting
	private Date date;
	private String startTime;
	private String endTime;
	private String type;
	private List<User> members; // List of Users attending the meeting
	private MeetingRoom room; // Reference to the associated MeetingRoom

	public Meeting(String id, String title, User organizer, Date date, String startTime, String endTime, String type,
			List<User> members, MeetingRoom room) {
		super();
		this.id = id;
		this.title = title;
		this.organizer = organizer;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
		this.members = members;
		this.room = room;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public MeetingRoom getRoom() {
		return room;
	}

	public void setRoom(MeetingRoom room) {
		this.room = room;
	}

}
