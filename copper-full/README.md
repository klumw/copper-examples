Copper-Full Example
===================
This setup adds Copper-GUI and a REST based mock for testing. 
The configuration involves some JMX setup to expose copper-engine MBeans to Copper-GUI. 

Order check workflow
------------------
In this example the order workflow checks order id, account id and credit card number for a given order.  
The account id check sends a request to the mock server that is handled synchronously.  
The credit card check sends an request to the mock server that is handled asynchronously.  
If order id is invalid a Runtime Exception is thrown.

Running the example
-------------------
From CL in *copper-full dir* run ***./mvnw clean install*** command. 
After successful compilation run ***docker-compose up***.

Testing
-------
Open your local browser and enter 
***http://localhost:8080/copper/rest/orders/test***.  
This will start a few workflow instances with different validation results;
For order ids < 1 a runtime exception is thrown and the corresponding workflow instance
is set to ERROR. 
Audit trail messages and workflow instances in state ERROR/INVALID can be viewed in Copper-GUI.
Set **Fetch Period** in Copper-GUI to 5 seconds to see copper time-series data immediately in the statistics chart.

Copper-GUI
---------- 
Copper-GUI is reachable from a local browser via
***http://localhost:8081***.  
For login enter admin/admin
After successful login you can see the connection to your copper-engine instance.

Extensions
----------
**JSON data format**  
Copper-full example comes with a ***MixedMode Serializer*** that persists workflow instance data in JSON format.  
Uncomment the ***serializer*** property in ***applicationContext.xml*** to activate the serializer.  
Together with the JSON functionality of PostgreSQL you can now search for workflow instance data on database level.  

**High-Water Mark**  
Input-Adapter services are featuring an additional High-Water Mark check. 
If the amount of workflow instances waiting for dequeue is
above High-Water Mark level, requests return a ***Server Error*** indicating
that the system cannot handle any more load a the moment.  
The **HIGH_WATER_MARK** level is set in the ***.env*** file. 
 




