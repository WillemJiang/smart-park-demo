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
