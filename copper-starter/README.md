Copper-Starter Example
====================
This simple setup with postgres database and copper-engine shows the basic functionality of
copper engine with persistence.

Running the example
-------------------
From CL in *copper-starter dir* run ***./mvnw clean install*** command. 
After successful compilation run ***docker-compose up***.

Testing
-------
Open your local browser and enter
***http://localhost:8080/copper/rest/customer/accounts/363633/22***  
This will start a workflow instance with personid=363633,age=22;

The workflow instance will not do anything meaningful, it just waits for 10 seconds ending
the workflow instance with a log message.
A more elaborated workflow example is available in the copper-full project.

