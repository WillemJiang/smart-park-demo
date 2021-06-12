### notification-service

It's provide the web interface which could be used for the user to register himself,  user can receive the notification, once there is a notification message which is sent to the notification service. 

### How to run it

You can use `mvn compile quarkus:dev` to compile and start the application.


### How to build and run the JVM docker image

You can use below command to build the docker image with JVM
```
docker build -f src/main/docker/Dockerfile.jvm -t smart-park/notification-service-jvm .
```
Then you can use below command to run the container 
```
docker run -i --rm -p 8080:8080 smart-park/notification-service-jvm
```

### How to build and run the native execution binary docker image

If you want to build the native execution binary, you need to setup the GraalVM by following the instructions [here](https://quarkus.io/guides/building-native-image-guide).

1. Before building the docker image run:
```
 mvn package -Pnative -Dquarkus.native.container-build=true
```
2. Build the image with:
```
 docker build -f src/main/docker/Dockerfile.native -t smart-park/visitor-service .
```
3. Then run the container using:
```
 docker run -i --rm -p 8080:8080 smart-park/visitor-service
```


 

