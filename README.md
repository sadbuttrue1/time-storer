Time storer
=

Simple project that can store current date and time to MongoDB every second. It tries to do it reliable and can survive DB server downtime and slowness.

How to run project
- 

1. You'll need an instance of MongoDB on local machine. You'll need docker and docker-compose installed. Just use `docker-compose up` 
from project directory and you'll be ready to go.
    * If you already have one feel free to use it. To do it you should edit mongo uri in `application.properties`.
    
2. Running with maven goals:
    1. To start storing run `mvn spring-boot:run`,
    2. To view stored data use `mvn spring-boot:run -Dspring-boot.run.arguments="-p"`.
3. Running with building executable jar:
    1. Build project with `mvn clean install spring-boot:repackage`,
    2. To start storing run `java -jar target/time-storer-1.0-SNAPSHOT.jar`,
    3. To view stored data use `java -jar target/time-storer-1.0-SNAPSHOT.jar -p`.     