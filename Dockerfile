FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY build/libs/gimbo-slice.jar gimbo-slice.jar
ENTRYPOINT ["java","-jar","/gimbo-slice.jar"]