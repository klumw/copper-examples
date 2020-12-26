Copper-Kubernetes Example
===================
This setup deploys copper-engine, postgres db, copper-GUI and the REST based copper-mock using
Kubernetes.  
Deployment was tested on minikube v1.16.0 with Kubernetes v1.20.0. Make sure you provide 
enough cpu/memory resources in minikube config.
The example shows a multi engine scenario where the number of running Copper Engine instances is 
set by a Deployment with 2 replicas.  
Depending on your Computer and Internet connection, initial startup can take quite some time.   

Order check workflow
------------------
In this example the order workflow checks order id, account id and credit card number for a given order.  
The account id check sends a request to the mock server that is handled synchronously.  
The credit card check sends an request to the mock server that is handled asynchronously.  
If order id is invalid a Runtime Exception is thrown.

Running the example
-------------------
From ***copper-kubernetes*** dir run ***kubectl apply -f copper-kubernetes-deployment.yaml***
Run ***kubectl port-forward*** for a copper-full-xxx pod on port 8080:8080.
Run ***kubectl port-forward*** for the copper-monitoring-xxx pod on port 8081:8080. 

Testing
-------
Open your local browser and enter 
***http://localhost:8080/copper/rest/orders/test***.  
This will start a few workflow instances with different validation results;
For order ids < 1 a runtime exception is thrown and the corresponding workflow instance
is set to ERROR. 
Audit trail messages and workflow instances in state ERROR/INVALID can be viewed in Copper-GUI.

Copper-GUI
---------- 
Copper-GUI is reachable from a local browser via
***http://localhost:8081*** (***see also port-forwarding***).  
For login enter admin/admin
After successful login you can see the connection to your copper-engine instance.
Set **Fetch Period** to 5 seconds to see copper time-series data immediately in the statistics chart.




