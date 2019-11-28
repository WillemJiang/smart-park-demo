/*
 * Copyright 2019 Jiang Ning
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        System.out.println(broadcaster);
        broadcaster.broadcast(notification.getMessage());
        LOGGER.info("Send out broadcast message : " + notification.getMessage());
    }


}
