## camel-route-eip-demo
This demo shows how to write a camel application for the smark park.

### How to run it

You can use `mvn compile spring-boot:run` to compile and start the application.

You can pass the notification-services address and visitor-service address by using the system properties like this
```
-DnotificationService.address=xxx:8080 -DvisitorService.address=xxx:8081
```

### How to build and run the JVM docker image

You can use below command to build the docker image with JVM
```
docker build -f src/main/docker/Dockerfile.jvm -t smart-park/camel-route-jvm .
```
Then you can use below command to run the container 
```
docker run -i --rm -p 9080:9080 smart-park/camel-route-jvm
```

