package com.example.jobapp;

public class Job {
    private int id;
    private String title;
    private String company;
    private String location;
    private String description;
    private boolean isAdmin; // Field to indicate admin status

    // Constructor with all fields, including isAdmin parameter
    public Job(String title, String company, String location, String description) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
        this.isAdmin = isAdmin;
    }
    public Job() { }

    public Job(int id, String title, String company, String location, String description) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
        this.isAdmin = false; // Default to user view
    }

    // Getter and setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    // Getter and setter for 'title'
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter for 'company'
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    // Getter and setter for 'location'
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getter and setter for 'description'
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and setter for 'isAdmin'
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getDocumentId() {

        return String.valueOf(id);
    }
}
