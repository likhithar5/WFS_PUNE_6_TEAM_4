package beans;

import enums.MeetingType;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

public class Meeting {

    /// primary key for meeting
    private int meetingId;
    private String meetingTitle;

    /// primary of the user who has organized this meeting
    private int organizerId;
    private LocalDate meetingDate;
    private LocalDate startTime;
    private LocalDate endTime;
    private MeetingType meetingType;

    private List<User> participants;
    private String meetingDescription;

    /// default constructor
    public Meeting() {
    }

    /// parameterized constructor
    public Meeting(
            int meetingId,
            String meetingTitle,
            int organizerId,
            LocalDate meetingDate,
            LocalDate startTime,
            LocalDate endTime,
            MeetingType meetingType,
            List<User> participants,
            String meetingDescription
    ) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.organizerId = organizerId;
        this.meetingDate = meetingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingType = meetingType;
        this.participants = participants;
        this.meetingDescription = meetingDescription;
    }


    /// getters and setters
    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Meeting.class.getSimpleName() + "[", "]")
                .add("meetingId=" + meetingId)
                .add("meetingTitle='" + meetingTitle + "'")
                .add("organizerId=" + organizerId)
                .add("meetingDate=" + meetingDate)
                .add("startTime=" + startTime)
                .add("endTime=" + endTime)
                .add("meetingType=" + meetingType)
                .add("participants=" + participants)
                .add("meetingDescription='" + meetingDescription + "'")
                .toString();
    }

    @Override
    public int hashCode() {
        return meetingId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
            return false;
        return (obj instanceof Meeting &&
                ((Meeting)obj).meetingId==this.meetingId
        );
    }
}
