package com.demo.beans;

import java.util.List;

public class MeetingRoom {
	private int roomId;
	private String roomName;
	private int seatingCapacity;
	private double ratings;
	private List<String> amenities; // List of amenities
	private double costPerHr;

	public MeetingRoom(int roomId, String roomName, int seatingCapacity, double ratings, List<String> amenities,
			double costPerHr) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
		this.seatingCapacity = seatingCapacity;
		this.ratings = ratings;
		this.amenities = amenities;
		this.costPerHr = costPerHr;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public double getRatings() {
		return ratings;
	}

	public void setRatings(double ratings) {
		this.ratings = ratings;
	}

	public List<String> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<String> amenities) {
		this.amenities = amenities;
	}

	public double getCostPerHr() {
		return costPerHr;
	}

	public void setCostPerHr(double costPerHr) {
		this.costPerHr = costPerHr;
	}

}
