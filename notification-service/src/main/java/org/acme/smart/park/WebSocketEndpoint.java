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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/subscribe/{username}")
@ApplicationScoped
public class WebSocketEndpoint {
    private static final Logger LOGGER = Logger.getLogger(WebSocketEndpoint.class);
    @Inject
    Broadcaster broadcaster;
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessions.put(username, session);
        broadcaster.addSession(session);
        broadcaster.broadcast("#User: " + username + " joined");
        LOGGER.info(username + " is online!");
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
        broadcaster.removeSession(session);
        broadcaster.broadcast("#User: " + username + " left");
        LOGGER.info(username + " is offline!");
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
        broadcaster.removeSession(session);
        broadcaster.broadcast("#User: " + username + " left on error: " + throwable);
        LOGGER.info("Some error happens, " + username + " is offline!", throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        broadcaster.broadcast(">> " + username + ": " + message);
        // just log the message
        LOGGER.info(username + " send message: " + message);
    }


}
