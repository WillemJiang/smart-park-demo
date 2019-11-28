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

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MyBroadcaster implements Broadcaster {

    private static final Logger LOGGER = Logger.getLogger(MyBroadcaster.class);

    private List<Session> sessions = new ArrayList();


    @Override
    public void broadcast(String message) {
        System.out.println("Calling the broadcast...");
        sessions.forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    LOGGER.error("Unable to send message: " + result.getException());
                }
            });
        });
    }

    @Override
    public void addSession(Session session) {
        sessions.add(session);
    }

    @Override
    public void removeSession(Session session) {
        sessions.remove(session);
    }
}
