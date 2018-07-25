FROM fabric8/java-jboss-openjdk8-jdk
ADD /build/libs/singer-0.0.1-SNAPSHOT.jar singer-0.0.1-SNAPSHOT.jar
ENV AB_OFF true
EXPOSE 8010
ENTRYPOINT ["java","-jar","singer-0.0.1-SNAPSHOT.jar"]