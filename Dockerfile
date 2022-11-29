
# build
FROM openjdk:11-jdk AS build
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
WORKDIR /workspace/app
COPY . /workspace/app
RUN chmod +x gradlew
RUN target=/root/.gradle ./gradlew clean build -x test -Pprofile=prod
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

# run
FROM build
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY} /app/lib
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Seoul","-Dspring.profiles.active=prod","./build/libs/KnockKnock-Backend-0.0.1-SNAPSHOT.jar"]
