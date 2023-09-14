package enums;

import java.util.List;

public enum MeetingType {
    CLASSROOM_TRAINING,
    ONLINE_TRAINING,
    CONFERENCE_CALL,
    BUSINESS;

    public static List<Amenities> getMandatoryAmenities(MeetingType meetingType){
        switch(meetingType){
            case CLASSROOM_TRAINING:
                return List.of(
                        Amenities.PROJECTOR,
                        Amenities.WHITEBOARD
                );
            case ONLINE_TRAINING:
                return List.of(
                        Amenities.WIFI_CONNECTION,
                        Amenities.PROJECTOR
                );
            case CONFERENCE_CALL:
                return List.of(Amenities.CONFERENCE_CALL_FACILITY);
            case BUSINESS:
                return List.of(Amenities.PROJECTOR);
            default:
                return null;
        }
    }
}
