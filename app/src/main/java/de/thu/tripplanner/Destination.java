package de.thu.tripplanner;

public class Destination {
    private String city;
    private String destinationType[];

    public Destination(String city, String destinationType[]) {
        this.city = city;
        this.destinationType = destinationType;
    }

    public String getCity() {
        return city;
    }

    public String[] getDestinationType() {
        return destinationType;
    }
}
