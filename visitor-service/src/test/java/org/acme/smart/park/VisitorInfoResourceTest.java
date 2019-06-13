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
