package beans;

public class Participant {
    private final int userId;
    private final int meetingId;

    public Participant(int userId, int meetingId) {
        this.userId = userId;
        this.meetingId = meetingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMeetingId() {
        return meetingId;
    }
}
