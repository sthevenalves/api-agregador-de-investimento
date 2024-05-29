FROM openjdk:19-jdk

COPY target/agregador-investimentos-0.0.1-SNAPSHOT.jar/ app/app.jar

CMD ("java", "-jar", "app/app.jar")