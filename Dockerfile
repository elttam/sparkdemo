FROM java:8 
WORKDIR /code
COPY pom.xml mvnw /code/
COPY src /code/src
COPY .mvn /code/.mvn
RUN ["./mvnw", "clean", "install"]
EXPOSE 4567
CMD ["./mvnw", "exec:java", "-Dexec.mainClass=com.example.sparkdemo.Main"]
