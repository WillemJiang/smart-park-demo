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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.websocket.Session;


class WebSocketEndpointTest {

    // Inject the Broadcaster
    private Broadcaster broadcaster = Mockito.mock(Broadcaster.class);
    private WebSocketEndpoint webSocketEndpoint = new WebSocketEndpoint();
    private Session session = Mockito.mock(Session.class);

    @BeforeEach
    public void setup() {
        // Inject the broadcaster
        webSocketEndpoint.broadcaster = broadcaster;
        Mockito.reset(broadcaster);
    }


    @Test
    void onOpen() {
        webSocketEndpoint.onOpen(session, "user1");
        Mockito.verify(broadcaster).addSession(session);
        Mockito.verify(broadcaster).broadcast("#User: user1 joined");
    }

    @Test
    void onClose() {
        webSocketEndpoint.onClose(session, "user2");
        Mockito.verify(broadcaster).removeSession(session);
        Mockito.verify(broadcaster).broadcast("#User: user2 left");
    }

    @Test
    void onError() {
        webSocketEndpoint.onError(session, "user3", new RuntimeException("Error!"));
        Mockito.verify(broadcaster).removeSession(session);
        Mockito.verify(broadcaster).broadcast("#User: user3 left on error: java.lang.RuntimeException: Error!");
    }

    @Test
    void onMessage() {
        webSocketEndpoint.onMessage("Message!", "user2");
        Mockito.verify(broadcaster).broadcast(">> user2: Message!");
    }
}
