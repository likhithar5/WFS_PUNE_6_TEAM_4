package enums;

public enum Amenities {
        PROJECTOR,
        WIFI_CONNECTION,
        CONFERENCE_CALL_FACILITY,
        WHITEBOARD,
        WATER_DISPENSER,
        TV,
        COFFEE_MACHINE;

    public static int getAmenityCost(Amenities amenity){
        switch(amenity){
            case PROJECTOR:
            case WHITEBOARD:
            case WATER_DISPENSER:
                return 5;
            case WIFI_CONNECTION:
            case TV:
            case COFFEE_MACHINE:
                return 10;
            case CONFERENCE_CALL_FACILITY:
                return 15;
            default:
                return 0;
        }
    }
}
