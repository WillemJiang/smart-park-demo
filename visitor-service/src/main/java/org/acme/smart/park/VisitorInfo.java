package org.acme.smart.park;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;
import java.util.Objects;

public class VisitorInfo {

    private String visitorName;

    @JsonbDateFormat("yyyy-MM-dd HH:mm:ss a z")
    private Date visitDate;

    public VisitorInfo() {
    }

    public VisitorInfo(String visitorName, Date date) {
        this.visitorName = visitorName;
        this.visitDate = date;
    }

    public VisitorInfo(Visitor visitor) {
        this.visitorName = visitor.getVisitorName();
        this.visitDate = new Date();
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitorInfo that = (VisitorInfo) o;
        return Objects.equals(visitorName, that.visitorName) &&
                Objects.equals(visitDate.toString(), that.visitDate.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitorName, visitDate.toString());
    }
}
