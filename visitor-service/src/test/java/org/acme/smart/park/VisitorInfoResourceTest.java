package org.acme.smart.park;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class VisitorInfoResourceTest {

    @Test
    public void testGetVisitors() {
        given()
          .when().get("/visitors")
          .then()
             .statusCode(200)
             .body(is("[{\"status\":\"RESERVED\",\"visitorName\":\"Bob\"}]"));
    }

}
