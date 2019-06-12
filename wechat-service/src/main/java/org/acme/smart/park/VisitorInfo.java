package org.acme.smart.park;

import java.util.Objects;

public class VisitorInfo {

    private String visitorName;

    private Status status;

    public VisitorInfo() {
    }

    public VisitorInfo(String visitorName, Status status) {
        this.visitorName = visitorName;
        this.status = status;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitorInfo that = (VisitorInfo) o;
        return visitorName.equals(that.visitorName) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitorName, status);
    }
}
