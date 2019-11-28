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

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.enterprise.inject.Produces;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class NotificationServiceTest {

    // To use the static member for injection
    private static Broadcaster MOCK_BROADCASTER = Mockito.mock(Broadcaster.class);

    @Mock
    @Produces
    Broadcaster getMockBroadcaster() {
        return MOCK_BROADCASTER;
    }
    
    @Test
    public void testNotifiyEndpoint() {
        Mockito.reset(MOCK_BROADCASTER);
        
        given()
                .contentType("application/json")
                .body("{\"message\":\"Notification message\"}")
                .when().post("/notification/notify")
                .then()
                .statusCode(204);
        
        verify(MOCK_BROADCASTER).broadcast("Notification message");

    }

}
