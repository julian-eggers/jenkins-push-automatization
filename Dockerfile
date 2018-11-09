FROM openjdk:11-jre-slim
EXPOSE 8080
ADD target/jenkins-push-automatization.jar jenkins-push-automatization.jar
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "-Djava.security.egd=file:/dev/./urandom", "jenkins-push-automatization.jar"]
