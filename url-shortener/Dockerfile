FROM frolvlad/alpine-java
EXPOSE 8080
ADD target/url-shortener-0.0.1-SNAPSHOT.jar url-shortener-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","url-shortener-0.0.1-SNAPSHOT.jar"]