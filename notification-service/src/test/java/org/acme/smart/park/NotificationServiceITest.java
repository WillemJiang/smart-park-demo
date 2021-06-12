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

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.websocket.*;

import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;


@QuarkusTest
public class NotificationServiceITest {

    private static final LinkedBlockingDeque<String> MESSAGES = new LinkedBlockingDeque<>();

    @TestHTTPResource("/subscribe/stu")
    URI uri;

    @Test
    public void testNotifyEndpoint() throws Exception {
        System.out.println("send out the message to url" + uri);

        try(Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class, uri)) {
            Assertions.assertEquals("CONNECT", MESSAGES.poll(10, TimeUnit.SECONDS));
            given()
                    .contentType("application/json")
                    .body("{\"message\":\"Notification message\"}")
                    .when().post("/notification/notify")
                    .then()
                    .statusCode(204);

            Assertions.assertEquals("Notification message", MESSAGES.poll(10, TimeUnit.SECONDS));
        }
    }

    //Added a Client to connect
    @ClientEndpoint
    public static class Client {

        @OnOpen
        public void open() {
            MESSAGES.add("CONNECT");
        }

        @OnMessage
        void message(String msg) {
            System.out.println("Get message here");
            MESSAGES.add(msg);
        }

    }

}
