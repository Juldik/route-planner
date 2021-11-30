# Route planner applicaiton
Purpose of this application is to provide route planner that calculates best route through the countries. 
Calculation is and based on given boundaries of every country and goal is to visit minimal set of countries to reach the
destination country. 

## How start and build
Following command will build the application using docker multistage build. 

`docker build . -t route-planner`

Following command will run application if you have docker on you PC

`docker run route-planner -expose 8080:8080`

If non docker build is required then JDK 17 is required and access to maven public repo. 

run following command in root directory to build with tests.

`mvn clean install` 

run following command to start the application 

`mvn spring-boot:start`

#How to use

Test request can be found in src/main/resources/test-request.http file e.g.

For calculation rote from Czech rep. to Italy, you could make following request.
```
GET http://localhost:8080/routing/CZE/ITA 
Accept: application/json
```


## About the application
The application calculate the shortest path between two countries  based on the Dijkstra algorithm. For this purpose jgrapht library was used see  [Their website with documentation](https://jgrapht.org/guide/UserOverview) for detailed information. Data about the countries are gathered from given endpoint by open feign client. The whole application is covered byt unit and integration tests written in jUnit5. 

App exposing one required endpoint http://localhost:8080/routing/{origin}/{destination} (if you keep given docker run command).
The result is path from origin to destination country. Using only three letter abbreviations of the countries.

## Inconsistency with requirements
* There was a requirement "If there is no land crossing, the endpoint returns HTTP 400", but it seems to me it is more of a not found than bad request thing. It could be easily changed by changing @ResponseStatus on the top of NoRouteFoundException.
* There was no specification of the behaviour when you put the same destination and origin - so my result is mention this country as a path.
  

## Possible improvements
* EnableCaching for even higher efficiency. Minimal - cache feign request, but ideally the whole chart and dijkstra structures so only search in the prepared structure is made.
* add api versioning actually uses directly endpoint /routing but it would be better to prefix it with /api/v1 or some other versioning prefix.

## How it was created initial thoughts
**WBS:**
* Prepare project structure -> Spring initializer
* Found library for finding shortest path in the graph.
* Think about relevant libraries - httpcliend, lombok, Spring web for Rest API,
* Prepare domain objects and feign client
* Study the shortest path library API
* Create tests for required use case - service and client for json. 
* Write client and service. 
* Write controller - run test. 
* Do Manual test. 
* Prepare docker image


