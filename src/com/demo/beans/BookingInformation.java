package com.demo.beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingInformation {
	private int id;
	private String roomName;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private int organizedBy;

	public BookingInformation(int id, String roomName, LocalDate date, LocalTime startTime, LocalTime endTime,
			int organizedBy) {
		super();
		this.id = id;
		this.roomName = roomName;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.organizedBy = organizedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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

	public int getOrganizedBy() {
		return organizedBy;
	}

	public void setOrganizedBy(int organizedBy) {
		this.organizedBy = organizedBy;
	}

}
