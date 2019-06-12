package org.acme.smart.park;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/visitors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WeChatResource {

    private Set<VisitorInfo> visitorInfos = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    

    public WeChatResource() {
        visitorInfos.add(new VisitorInfo("Apple", Status.RESERVERD));
        visitorInfos.add(new VisitorInfo("Banana", Status.RESERVERD));
    }

    @GET
    public Set<VisitorInfo> list() {
        return visitorInfos;
    }

    @POST
    @Path("/verify")
    public VisitorInfo verify(VisitorInfo visitorInfo) {
        if (visitorInfo.getVisitorName().equals("Apple")) {
            visitorInfo.setStatus(Status.RESERVERD);
        } else {
            visitorInfo.setStatus(Status.NEEDTO_CONFIRM);
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
