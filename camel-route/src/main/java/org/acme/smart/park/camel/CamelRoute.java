package org.acme.smart.park.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CamelRoute extends RouteBuilder {
  @Value("${visitorService.address:localhost:8080}")
  private String visitorServiceAddress;

  @Value("${notificationService.address:localhost:8082}")
  private String notificationServiceAddress;
  

  @Override
  public void configure() throws Exception {
    restConfiguration()
        .component("restlet")
        .host("0.0.0.0")
        .port("9080");

    rest()
        .get("/visitor/{name}")
        .to("direct:visitorCheckIn");

    from("direct:visitorCheckIn")
        .transform().simple("{\"visitorName\":\"${header.name}\"}")
        .removeHeaders("*")
        .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.POST))
        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        .to("http4://" + visitorServiceAddress + "/visitors/checkIn")
        .transform().jsonpath("$.visitorName")
        .to("direct:notifyService");

    from("direct:notifyService")
        .choice()
            .when().simple("${body} == \"Alex\"")
                .transform().simple("VIP ${body}")
            .end()
        .setHeader("message").simple("Welcome ${body} to robot world")
        .transform().simple("{\"message\":\" ${body} is here.\"}")
        .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.POST))
        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        .to("http4://" + notificationServiceAddress + "/notification/notify")
        .setBody().simple("${headers.message}")
            // We need to clean up the header to send out the message
            .removeHeaders("*");


  }
}
