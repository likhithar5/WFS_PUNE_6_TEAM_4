package dto;

import enums.MeetingType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.StringJoiner;

public class OrganizeMeetingRequest {
    private String meetingTitle;
    private MeetingType meetingType;
    private LocalDate meetingDate;
    private LocalTime meetingStartTime;
    private int durationInMinutes;
    private List<Integer> participants;
    private int organizedBy;
    private String bookedMeetingRoomName;
    private String meetingDescription;

    // default constructor
    public OrganizeMeetingRequest() {
    }

    // parameterized constructor
    public OrganizeMeetingRequest(
            String meetingTitle,
            MeetingType meetingType,
            LocalDate meetingDate,
            LocalTime meetingStartTime,
            int durationInMinutes,
            List<Integer> participants,
            int organizedBy,
            String bookedMeetingRoomName,
            String meetingDescription) {
        this.meetingTitle = meetingTitle;
        this.meetingType = meetingType;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.durationInMinutes = durationInMinutes;
        this.participants = participants;
        this.organizedBy = organizedBy;
        this.bookedMeetingRoomName = bookedMeetingRoomName;
        this.meetingDescription = meetingDescription;
    }

    // getters and setters
    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    public LocalTime getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(LocalTime meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }

    public int getOrganizedBy() {
        return organizedBy;
    }

    public void setOrganizedBy(int organizedBy) {
        this.organizedBy = organizedBy;
    }

    public String getBookedMeetingRoomName() {
        return bookedMeetingRoomName;
    }

    public void setBookedMeetingRoomName(String bookedMeetingRoomName) {
        this.bookedMeetingRoomName = bookedMeetingRoomName;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }
}
