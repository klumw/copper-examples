Copper-Kubernetes Example
===================
This Kubernetes setup deploys copper-engine, postgres db, copper-GUI and a REST based mock.
Deployment was tested on minikube v1.16.0 with Kubernetes v1.20.0. Make sure you have 
enough cpu/memory resources in your minikube environment. 
Depending on your Computer and Internet connection, initial startup can take quite some time.   

Order-Check Workflow
------------------
In this example the order workflow checks order id, account id and credit card number for a given order.  
The account id check sends a request to the mock server that is handled synchronously.  
The credit card check sends a request to the mock server that is handled asynchronously.  
For an invalid order id a Runtime Exception is thrown.

Running the example
-------------------
From ***copper-kubernetes*** dir run ***kubectl apply -f copper-kubernetes-deployment.yaml***
Run ***kubectl port-forward*** for the copper-full-xxx pod on port 8080:8080.
Run ***kubectl port-forward*** for the copper-monitoring-xxx pod on port 8081:8080. 

Testing
-------
Open your local browser and enter 
***http://localhost:8080/copper/rest/orders/test***.  
This will start a few workflow instances with different validation results;
For order ids < 1 a Runtime-Exception is thrown and the corresponding workflow instance
is set to ERROR. 
Audit trail messages and workflow instances in state ERROR/INVALID can be viewed in Copper-GUI.

Copper-GUI
---------- 
Copper-GUI is available from a local browser via
***http://localhost:8081*** (***see also port-forwarding***).  
On the login screen enter admin/admin
After successful login you can see the connection to your copper-engine instance.
Set **Fetch Period** to 5 seconds to see copper time-series data immediately in the statistics chart.




