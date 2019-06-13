package org.acme.smart.park;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/visitors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VisitorInfoResource {

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
        visitorInfos.add(visitorInfo);
        return visitorInfo;
    }

    @DELETE
    public Set<VisitorInfo> delete(VisitorInfo visitorInfo) {
        visitorInfos.remove(visitorInfo);
        return visitorInfos;
    }
}
