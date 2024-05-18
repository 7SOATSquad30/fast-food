FROM eclipse-temurin:21

RUN mkdir /opt/app

ADD gradle/wrapper/gradle-wrapper.jar /opt/app/gradle-wrapper.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/gradle-wrapper.jar"]