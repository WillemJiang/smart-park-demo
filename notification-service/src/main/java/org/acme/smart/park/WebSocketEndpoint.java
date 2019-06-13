package org.acme.smart.park;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/subscribe/{username}")
@ApplicationScoped
public class WebSocketEndpoint implements Broadcaster {
    private static final Logger LOGGER = Logger.getLogger(WebSocketEndpoint.class);
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessions.put(username, session);
        broadcast("#User: " + username + " joined");
        LOGGER.info(username + " is online!");
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
        broadcast("#User: " + username + " left");
        LOGGER.info(username + " is offline!");
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
        broadcast("#User: " + username + " left on error: " + throwable);
        LOGGER.info("Some error happens, " + username + " is offline!", throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        broadcast(">> " + username + ": " + message);
        // just log the message
        LOGGER.info(username + " send message: " + message);
    }

    @Override
    public void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    LOGGER.error("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
