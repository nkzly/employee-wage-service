# Empoloyee Wage service 

## Description
The project contains two modules:
* **producer-service** - contains one REST controller that handles requests and sends them to the Kafka
* **consumer-service** - gets messages from the queue and save them in the mysql


Build images via command: 
> ./gradlew :consumer-service:jibDockerBuild :producer-service:jibDockerBuild

And, after then run compose:

> docker-compose up 

**Tax_rate** changing via ENV variable: **TAX_RATE** in the consumer-service

After application has successfully started you can check swagger UI console by the following URL  
> `http://{service_host}:{service_port}/swagger-ui.html`

> API: `/v3/api-docs`
