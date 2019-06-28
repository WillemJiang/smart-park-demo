/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
