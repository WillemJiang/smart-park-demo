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
import org.mockito.Mockito;

import javax.websocket.*;

import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class NotificationServiceIT {

    private static final LinkedBlockingDeque<String> MESSAGES = new LinkedBlockingDeque<>();

    @TestHTTPResource("/subscribe/stu")
    URI uri;

    @Test
    public void testNotifiyEndpoint() throws Exception {

        try(Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class, uri)) {
            Assertions.assertEquals("CONNECT", MESSAGES.poll(10, TimeUnit.SECONDS));
            given()
                    .contentType("application/json")
                    .body("{\"message\":\"Notification message\"}")
                    .when().post("/notification/stu")
                    .then()
                    .statusCode(204);
            Assertions.assertEquals("Notification message", MESSAGES.poll(10, TimeUnit.SECONDS));

            given()
                    .contentType("application/json")
                    .body("{\"message\":\"Notification message\"}")
                    .when().post("/notification/otherStu")
                    .then()
                    .statusCode(204);
            // we should not get message here
            Assertions.assertEquals(null, MESSAGES.poll(10, TimeUnit.SECONDS));
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
            MESSAGES.add(msg);
        }

    }

}
