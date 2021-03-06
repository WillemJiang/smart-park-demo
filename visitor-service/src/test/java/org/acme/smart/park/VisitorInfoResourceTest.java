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

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class VisitorInfoResourceTest {

    @Test
    public void testGetVisitors() {
        given()
          .when().get("/visitors")
          .then()
             .statusCode(200)
             .body(is("[{\"visitDate\":\"2019-06-08 16:00:00 PM UTC\",\"visitorName\":\"Bob\"}]"));

    }

    @Test
    public void testPostVisitors() {
        given()
            .contentType("application/json")
            .body("{\"visitorName\":\"Alex\"}")
            .when().post("/visitors/checkIn")
            .then()
                .statusCode(200)
                .body("visitorName", is("Alex"))
                .body("visitDate", endsWith("UTC"));
    }

}
