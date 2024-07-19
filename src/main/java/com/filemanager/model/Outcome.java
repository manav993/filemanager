package com.filemanager.model;

public class Outcome {
    private String name;
    private String transport;
    private double topSpeed;

    public Outcome(String name, String transport, double topSpeed) {
        this.name = name;
        this.transport = transport;
        this.topSpeed = topSpeed;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(double topSpeed) {
        this.topSpeed = topSpeed;
    }
}
