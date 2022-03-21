[![Java CI with Gradle](https://github.com/DmitriyZosimov/english/actions/workflows/gradle.yml/badge.svg)](https://github.com/DmitriyZosimov/english/actions/workflows/gradle.yml)
# English
A web application is to study english words

## Requirements
* Java 8
* Apache Tomcat
* PostgreSQL or AWS RDS
* (Optional) Apache Kafka
* (Optional) [Node.js which includes Node Package Manager](https://github.com/DmitriyZosimov/english/tree/master/angular-app#development-setup)

## Profiles
There are few profiles:
* **local** - is used if PostgreSQL was selected as DB.
* **rds** - is used if AWS RDS was selected as DB.


* **withoutKafka** - is used if Kafka wasn't enabled.
* **withKafka** - is used if Kafka was enabled and launched.

## Setup Apache Tomcat
```
cd /opt
mkdir tomcat
wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.60/bin/apache-tomcat-9.0.60.tar.gz
tar -xzvf apache-tomcat-9.0.60.tar.gz
rm -r apache-tomcat-9.0.60.tar.gz
```

## Setup PostgreSQL
```
sudo apt-get update
sudo apt-get -y install postgresql
sudo -u postgres createdb wordsdb
sudo service postgresql start
```

## Properties

DB properties should be written for PostgreSQL or RDS
* [PostgreSQL properties](https://github.com/DmitriyZosimov/english/blob/master/local-db/src/main/resources/db.properties)
* [AWS RDS properties](https://github.com/DmitriyZosimov/english/blob/master/aws-rds/src/main/resources/com/myenglish/aws/aws-config.properties)

## Build application
```./gradlew clean build vaadinBuildFrontend```

## Start application

There are two options for launching the application - using Vaadin or Angular as front-end
### Vaadin application
To start the server:
```
cp ./web/build/libs/web-0.0.1.war /opt/tomcat/apache-tomcat-9.0.60/webapps/
export CATALINA_OPTS="$CATALINA_OPTS -Dspring.profiles.active=local,withoutKafka"
sh /opt/tomcat/apache-tomcat-9.0.60/bin/startup.sh
tail -f /opt/tomcat/apache-tomcat-9.0.60/logs/catalina.out
```
 Server up on http://localhost:8080/web-0.0.1/
 
 ### Angular application
 Node.js is required.
 
 To start the back-end server:
 ```
 cp ./web-angular/build/libs/web-angular-0.0.1.war /opt/tomcat/apache-tomcat-9.0.60/webapps/
 export CATALINA_OPTS="$CATALINA_OPTS -Dspring.profiles.active=local,withoutKafka"
 sh /opt/tomcat/apache-tomcat-9.0.60/bin/startup.sh
 tail -f /opt/tomcat/apache-tomcat-9.0.60/logs/catalina.out
 ```
 and to start the front-end server:
 ```
 cd angular-app
 ng serve
 ```
  Server up on http://localhost:4200/word/test