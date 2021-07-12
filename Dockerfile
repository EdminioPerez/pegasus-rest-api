FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.11_9

ENV HTTP_PORT 9197
ENV CONTEXT_APP /bank

ENV CONFIG_SERVER_URL http://192.168.1.137:8989
ENV BRANCH develop
ENV PROFILE dev

ENV EUREKA_SERVER_URL http://192.168.0.104:8762/eureka/

ENV DATASOURCE_URL jdbc:postgresql://localhost:5434/pegasus_dev
ENV DATASOURCE_DRIVER_CLASS_NAME org.postgresql.Driver
ENV DATASOURCE_USERNAME pegasus_dev
ENV DATASOURCE_PASSWORD 1234

ENV OAUTH2_SERVER_URL http://192.168.1.137:7575/authorization
ENV OAUTH2_CLIENT_ID clientIdPassword
ENV OAUTH2_CLIENT_SECRET ""

ENV SWAGGER_SERVER_PATH http://192.168.0.104:9197/bank

ENV STANDALONE_RUN false

ENV PROXY_OPTS "" 
ENV REGION_OPTS -Duser.timezone=UTC -Duser.language=en
ENV JAVA_OPTS "-Xms256m -Xmx256m -Xss256k -noverify -XX:+UseG1GC -XX:+UseStringDeduplication -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8 $PROXY_OPTS $REGION_OPTS -server"

RUN echo 'root:Docker!' | chpasswd

RUN addgroup --gid 1024 microservice
RUN adduser --disabled-password --gecos "" --ingroup microservice app

USER app
WORKDIR /home/app

# Different from other dockerfiles
RUN mkdir images

COPY target/pegasus-rest-api.jar /home/app
COPY scripts/* /home/app/

RUN bash -c "[ '$STANDALONE_RUN' = 'false' ] || mv -f /home/app/entrypoint_standalone.sh /home/app/entrypoint.sh"

#RUN cp -r /tmp/pegasus-rest-api.jar /home/app/
#RUN cp -r /tmp/entrypoint.sh /home/app/

RUN sed -i 's/\r$//' entrypoint.sh

RUN ["chmod", "+x", "entrypoint.sh"]

EXPOSE ${HTTP_PORT}

ENTRYPOINT ["./entrypoint.sh"]
