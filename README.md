# Template for a web application with Spring 5

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

1. Clone the project from git repository.
2. Import the project in the IDE as maven project
3. Modify the class com.greek.service.ServletRestServicesConfiguration
	```java
	return application.properties("spring.config.name:[name of the module]").sources(applicationClass);
	```
4. Modify the pom.xml file
	```xml
	<artifactId>[name of the module]</artifactId>
	<build>
	  <finalName>[name of the module]</artifactId>
	</build>
	```
5. Modify the docker file
	```xml
	ADD target/[name of jar as created in finalName].jar  /tmp
	```
6. Modify the script entrypoint.sh
	  - The line where we extract the properties for the application from swf-env-properties.git
		```xml
			cp ${CONFIG_FILES_DIR}/mlm/${ENV_NAME}/[name of the module].properties /home/app/application.properties
		```
	  - And the last line in the same file
		```xml
			java -Dspring.config.location=file:/home/app/ $JAVA_OPTS -jar [name of the jar].jar
		```
7. Find and replace any reference to rest-example in the whole project (even IDE files)
8. Download the dependencies using maven
9. Build the project using maven

Right button on the project > Run as > Spring Boot App

When eclipse asks for the class to run, select RestServicesApplication
Build the project using maven (mvn clean package or install)
```sh
$ mvn clean package -Pdocker [-Dmaven.test.skip=true]
```
enter into target directory and run the resultant jar with the command: 
```sh
$ java -jar <name of the jar>
```

First you must compile and package the project with docker profile with:
```sh
$ mvn clean package -Pdocker [-Dmaven.test.skip=true]
```
And after that you can do
```sh
$ docker-compose build
$ docker-compose up -d
```

You can find swagger in http[s]://[host]:[port]/swagger-ui

Example:
http://localhost:9191/swagger-ui

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
-noverify -Xms256m -Xmx256m -Xss256k -XX:+UseG1GC -XX:+UseStringDeduplication -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8

### For the Docker
Use adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.11_9 image (fixed version)

## ELK startup
docker run -d -p 5601:5601 -p 9200:9200 -p 5044:5044 -p 9600:9600 -p 4560:4560 -v ${HOME}/elk-data:/var/lib/elasticsearch -v ${HOME}/logstash/conf.d:/etc/logstash/conf.d --name elk sebp/elk

## Value of logstash.conf
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

If you have ideas for releases in the future, please add them here.
