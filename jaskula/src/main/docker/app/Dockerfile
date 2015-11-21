FROM java:7

WORKDIR /

ENTRYPOINT ["java" , "-jar" , "/${project.build.finalName}-exe.jar"]

EXPOSE 8080

ADD ${project.build.finalName}-exe.jar /


