package org.acme.smart.park;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/visitors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VisitorInfoResource {

    private Set<VisitorInfo> visitorInfos = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    

    public VisitorInfoResource() {
        visitorInfos.add(new VisitorInfo("Bob", Status.RESERVED));
    }

    @GET
    public Set<VisitorInfo> list() {
        return visitorInfos;
    }

    @POST
    @Path("/verify")
    public VisitorInfo verify(VisitorInfo visitorInfo) {
        if (visitorInfo.getVisitorName().equals("Alex")) {
            visitorInfo.setStatus(Status.RESERVED);
        } else {
            visitorInfo.setStatus(Status.NEEDTO_NOTIFY);
        }
        visitorInfos.add(visitorInfo);
        return visitorInfo;
    }

    @DELETE
    public Set<VisitorInfo> delete(VisitorInfo visitorInfo) {
        visitorInfos.remove(visitorInfo);
        return visitorInfos;
    }
}
