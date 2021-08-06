# Template for a microservice application with SpringBoot 2.4.x

Template for web application with Spring 5
It provides support for
- REST features
- Securization
- Multilanguage
- HTTP calls
- Data Layer
- Swagger
- Error handling
- Logging
- Web Monitoring
- Life check
- DTO handling

## First running

As the project may need mapstruct previously for dtos creation, before running it you need to do:
```sh
$ mvn clean compile
```

### Running with eclipse

Right button on the project > Run as > Spring Boot App

When eclipse asks for the class to run, select **RestServicesApplication**

## Packaging of the project

Build the project using maven
```sh
$ mvn clean package -Pdocker [-Dmaven.test.skip=true]
```
**Note**: Never run the *install* command for a microservice, it does not make any sense to install locally a microservice jar

## Running for the first time

### Creating the first user in the app

Go to **com.greek.service.manager.impl.UserServiceLayerIT** class and:
- Uncomment the line **extracted();**
- Run the test over this file only
- If everything is ok, check the table **usuario** in the DB and you will have the first user of the app in the field **codigo_usuario**
- Check the table **organizacion** you must see 3 rows (group, organization, venue)
- Check the table **usuario_organizacion** and you will have the user recently created linked to this organizations
- Check the table **rol_usuario** and you will have the user recently created linked to a specific role
- Check the table **persona** and you will have the person data related to this user
- Check the table **persona_organizacion** and you will have the person linked to this group previously created

Enter into target directory and run the resultant jar with the command: 
```sh
$ java -jar <name of the jar>
```

## Dockerization

If you are using linux OS, you have the utility script for compiling, packaging and image creation for docker script
```sh
$ ./createDocker.sh
```
Refer to the content of this file to know the manual steps for dockerization 

## Tools
### Swagger
You can find swagger in http[s]://[host]:[port]/[context]/swagger-ui.html

Example:
http://localhost:9191/bank/swagger-ui.html

You can find actuator in in http[s]://[host]:[port]/actuator

Example:
http://localhost:9191/actuator/health
http://localhost:9191/actuator/info

Simon console is for monitoring response times of every endpoint, and 
you can find it in http[s]://[host]:[port]/performance-monitor/index.html

Example
http://localhost:9191/performance-monitor/index.html

## Performance tips

### For the JVM (even in development mode)
-noverify -Xms256m -Xmx256m -Xss256k -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -XX:+UseStringDeduplication -XX:+OptimizeStringConcat -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8 -Duser.timezone=UTC -Duser.language=en

### For the Docker
Use adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.11_9 image (fixed version)

## ELK startup
docker run -d -p 5601:5601 -p 9200:9200 -p 5044:5044 -p 9600:9600 -p 4560:4560 -v ${HOME}/elk-data:/var/lib/elasticsearch -v ${HOME}/logstash/conf.d:/etc/logstash/conf.d --name elk sebp/elk

#### Value of logstash.conf
Inside the logstash/conf.d/logstash.conf
```
input {
	tcp {
		host => "0.0.0.0"
		port =>  4560
		codec => json_lines
	}
}
output {
 	elasticsearch {
		hosts => ["http://192.168.0.104:9200"]
		index => "logs-%{+YYYY.MM.dd}"
  	}
}
```
#### Value for logback
```
	<appender name="logstash" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
		<param name="Encoding" value="UTF-8" />
		<destination>127.0.0.1:4560</destination>
		<encoder class="net.logstash.logback.encoder.LogstashEncoder">
			<includeMdcKeyName>correlationId</includeMdcKeyName>
			<includeMdcKeyName>username</includeMdcKeyName>
			<customFields>{"app_name":"pegasus-rest-api", "app_port": "YourPort"}</customFields>
		</encoder>
	</appender>
```
If you have ideas for releases in the future, please add them here.
