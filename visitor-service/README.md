### visitor-service

It provide visitor checkin service from the restful service url visitor/checkin, the response message has the visitor checkin time information. 

### How to run it

You can use `mvn compile quarkus:dev` to compile and start the application.


### How to build and run the JVM docker image

You can use below command to build the docker image with JVM
```
 docker build -f src/main/docker/Dockerfile.jvm -t smart-park/visitor-service-jvm .
```
Then you can use below command to run the container 
```
docker run -i --rm -p 8080:8080 smart-park/visitor-service-jvm
```

### How to build and run the native excution binary docker image

If you want to build the native execution binary, you need to setup the GraalVM by following the instructions [here](https://quarkus.io/guides/building-native-image-guide).

1. Before building the docker image run:
```
 mvn package -Pnative -Dnative-image.docker-build=true
```
2. Build the image with:
```
 docker build -f src/main/docker/Dockerfile.native -t smart-park/visitor-service .
```
3. Then run the container using:
```
 docker run -i --rm -p 8080:8080 smart-park/visitor-service
```


 

