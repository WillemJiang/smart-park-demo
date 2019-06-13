package org.acme.smart.park;

public class Visitor {
    
    private String visitorName;

    public Visitor() {}

    public Visitor(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }
}
