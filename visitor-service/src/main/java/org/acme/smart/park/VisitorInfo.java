package org.acme.smart.park;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;
import java.util.Objects;

public class VisitorInfo {

    private String visitorName;

    @JsonbDateFormat("yyyy-MM-dd HH:mm:ss a z")
    private Date date;

    public VisitorInfo() {
    }

    public VisitorInfo(String visitorName, Date date) {
        this.visitorName = visitorName;
        this.date = date;
    }

    public VisitorInfo(Visitor visitor) {
        this.visitorName = visitor.getVisitorName();
        this.date = new Date();
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitorInfo that = (VisitorInfo) o;
        return Objects.equals(visitorName, that.visitorName) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitorName, date);
    }
}
