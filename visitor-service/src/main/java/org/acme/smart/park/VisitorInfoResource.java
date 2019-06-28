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

import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/visitors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VisitorInfoResource {
    private static final Logger LOGGER = Logger.getLogger(VisitorInfoResource.class);

    private Set<VisitorInfo> visitorInfos = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    

    public VisitorInfoResource() {
        //Calendar calendar = new Calendar.Builder().setDate(2019, 6, 10).build();
        visitorInfos.add(new VisitorInfo("Bob", new Date(119, 5, 9)));
    }

    @GET
    public Set<VisitorInfo> list() {
        return visitorInfos;
    }

    @POST
    @Path("/checkIn")
    public VisitorInfo checkIn(Visitor visitor) {
        VisitorInfo visitorInfo = new VisitorInfo(visitor);
        LOGGER.info("Visitor " + visitor.getVisitorName() + "is checked in at " + visitorInfo.getVisitDate() + "!");
        visitorInfos.add(visitorInfo);
        return visitorInfo;
    }

    @DELETE
    public Set<VisitorInfo> delete(VisitorInfo visitorInfo) {
        visitorInfos.remove(visitorInfo);
        return visitorInfos;
    }
}
