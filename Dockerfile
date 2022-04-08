FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} gimbo-slice.jar
ENTRYPOINT ["java","-jar","/gimbo-slice.jar"]