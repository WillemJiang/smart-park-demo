package org.acme.smart.park;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/notification")
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationService {

    private static final Logger LOGGER = Logger.getLogger(NotificationService.class);

    @Inject
    Broadcaster broadcaster;

    @POST
    @Path("/notify")
    public void notify(Notification notification) {
        // Just send out the notification to the connected client
        broadcaster.broadcast(notification.getMessage());
        LOGGER.info("Send out broadcast message : " + notification.getMessage());
    }


}
