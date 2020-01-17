Copper Prometheus
========
This example shows how you can measure and store copper related time-series data in prometheus.  

The following data can be measured:
- Dequeue count
- Error count
- Finished count
- Invalid count
- Running count
- Waiting count

How it works
------------
Copper metrics data is provided by the prometheus agent running on top of copper jvm. Prometheus scrapes 
the data from the jmx exporters http interface and stores it for later analysis.  
In this example Grafana GUI is used to give us a visual insight into prometheus data.

Running the example
-------------------
From CL in directory *copper-prometheus run ***docker-compose up***.
All necessary images are downloaded and started.  
This example requires a machine with enough CPU and RAM resources. 

Open your local browser and enter 
***http://localhost:8080/copper/rest/orders/test***. 
Repeat the tests runs to create enough data for later analysis.

Viewing prometheus data in Grafana-GUI
----------------------------------------
Grafana-GUI is available at http://localhost:3000.
You can logon with admin/admin. 
From Home Dashboard choose create first data source.
Select Prometheus, in the URL enter http://prometheus:9090.
Press the ***Save and Test*** button. If connection to prometheus is ok 
you will see a confirmation popup.
Now you to your Home Dashboard and click ***Create your first Dashboard***, choose ***Add Query***
and select your prometheus datasource from the ***Query*** drop down.
In the query panel click ***Metrics*** to select metric data for copper engine.
Set ***Custom time range*** for your dashboard to 5 minutes to be able to see recent data.

Troubleshooting
---------------
If you can't see your copper data in metrics panel, check your prometheus server first. Go to http://localhost:9090/graph. Click ***insert metric at cursor***, you should see copper metrics data
in the dropdown list. Execute the query to see the actual metrics for the selected data.
If you can' see metrics data the prometheus java agent in the copper container might not work as
expected.   

Resources
---------
More information about Prometheus and Grafana can be found at:  
https://prometheus.io  
https://github.com/prometheus/jmx_exporter  
https://www.grafana.com

  