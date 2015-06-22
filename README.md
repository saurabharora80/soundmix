## Prerequisites
- Java 1.8
- IntelliJ Idea or Eclipse
- a Unix or Mac machine (should run on windows as well, but haven't tried it) 

## Importing the project in IntelliJ IDEA

execute -> './gradlew cleanIdea idea' to generate the Idea project files

## Importing the project in Eclipse

execute -> './gradlew cleanEclipse eclipse' to generate the Idea project files

## Building the application

execute -> './gradlew clean build' to build the application and run all tests
 
## Producing an executable jar
 
execute -> './gradlew clean build fatJar' to produce an executable fat jar
    will produce a fat jar 'soundmix.jar' in build/libs

## Running the Jar

execute 'java -jar build/libs/soundmix.jar' on the console

Once the application has been started, please provide the commands as given in the exercise.
 
## Quiting the application 
 
execute -> 'ctl c' on console 

### Note

*All the command must be executed from the base of the project*