# cake-manager

Building and running
---

The project has a client and a resource module.

cake-manager-resource
---

Run with : 

    cd ./cake-manager-resource
    mvn clean package
    java -jar target/cake-manager-resource.jar

or :

    cd ./cake-manager-resource
    mvn clean spring-boot:run

Verify with :

    curl -i -I http://localhost:8282/cakes                                                                                      śro, 29 sie 2018, 14:36:14 
    HTTP/1.1 200 

cake-manager-client
---

Run with :

    cd ./cake-manager-client
    mvn clean package
    java -jar target/cake-manager-client.jar

or :

    cd ./cake-manager-client
    mvn clean spring-boot:run

Verify with :

    curl -i -I http://localhost:8181/                                                                                      śro, 29 sie 2018, 14:36:14 
    HTTP/1.1 200 

Application
---

In a browser, to view the list of cakes :

    http://localhost:8181/ 

This same view allows you to add a cake to the server, which is subsequently
displayed at the end of the list after adding.

To download the json for the list of cakes, or add a cake directly :

    http://localhost:8282/swagger-ui.html

If you click on the cake-controller's GET /cakes endpoint, click 'try it out', 
and then 'execute' the request, you can download the results for the Server response. 

If you click on the cake-controller's POST /cakes endpoint, click 'try it out',
fill out the title,desc, and image json and  'execute' the request, you can add a new
cake to the Server. 

Steps 
---

1. Got what was there working to see what it does.
Fixed the servlet api, jetty, and maven compiler plugin dependencies in the pom file, added
the servlet version to the web deployment descriptor, and fixed naming errors in CakeEntity.

2. Upgrade to use spring boot as the app currently is.
Modified the pom file, adding spring boot dependencies for web and jpa, and adjusted the 
existing dependencies to be compatible with it. The application now uses embedded Tomcat 
instead of Jetty, which is as standard. Formulated a new package structure, configured the 
spring boot application to find the Servlet, and performed a small refactoring to it, removing
HibernateUtil.java and hibernate.cfg.xml along the way. Finally, added a new controller to 
serve the root jsp.

3. Implemented the client app and rest resource. 
- Added integration tests for the cake controller and service using WireMock.
- Implemented the cake controller and service which meant fleshing out 
the implementation driven by unit tests. 
- Removed servlet.
- Added user interface using thymeleaf templates and a new ui controller.
- Removed jsps.
- Added swagger documentation for the cake controller.
- Added validation and duplicate cake handling, fully tested.
- As part of the implementation, care was taken to separate client from rest resource 
with the view to a complete separation in a future commit.

4. Separation of the client app and rest resource. There are now two projects, a client and a 
resource project, that run on ports 8181 and 8282 respectively.