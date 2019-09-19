package com.example.sporttogether.Data;


public class WorkoutRecord {

    private String type;
    private String title;
    private String city;
    private String description;

    public WorkoutRecord(){

    }

    public WorkoutRecord(String type, String title, String city, String description) {
        this.type = type;
        this.title = title;
        this.city = city;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WorkoutRecord{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    //    private String type;
//    private String city;
//    private Location location;
//    private String description;
//    private int participants;
//    private String title;
//    private String date;
//
//    // private id
//
//
//    public WorkoutRecord() {
//    }
//
//    public WorkoutRecord(String type, String city, Location location, String descriptions,String title) {
//        this.type = type;
//        this.city = city;
//        this.description = descriptions;
//        this.location = location;
//        this.participants = 1;
//        this.title = title;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }
//
//    public String getdescription() {
//        return description;
//    }
//
//    public void setdescription(String description) {
//        this.description = description;
//    }
//
//    public int getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(int participants) {
//        this.participants = participants;
//    }
}
