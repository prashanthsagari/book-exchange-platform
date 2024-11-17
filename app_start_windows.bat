@echo off

REM Start API Gateway in a new command prompt
start cmd /k "cd api-gateway && cd target && java -jar api-gateway-0.0.1-SNAPSHOT.jar"

REM Start Eureka Service Discovery in a new command prompt
start cmd /k "cd eureka-service-discovery-exchange-platform && cd target && java -jar eureka-service-discovery-exchange-platform-0.0.1-SNAPSHOT.jar"


REM Start Book Management Srevice in a new command prompt
start cmd /k "cd book-mgmt && cd target && java -jar  book-mgmt-0.0.1-SNAPSHOT.jar"

REM Start User Management Service in a new command prompt
start cmd /k "cd user-mgmt && cd target && java -jar user-mgmt-0.0.1-SNAPSHOT.jar"

echo "Applications started in separate command prompts."
pause
