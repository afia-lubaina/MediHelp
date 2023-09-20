package com.example.medihelp;

public class Doctor {
    private int ID;
    private String name;
    private String speciality;
    private String location;
    private String contact;

    public Doctor(int ID, String name, String speciality, String location, String contact) {
        this.ID = ID;
        this.name = name;
        this.speciality = speciality;
        this.location = location;
        this.contact = contact;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
