FROM openjdk:8
COPY ./target/client-0.0.1.jar client-0.0.1.jar
CMD java -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n -jar client-0.0.1.jar
EXPOSE 8000