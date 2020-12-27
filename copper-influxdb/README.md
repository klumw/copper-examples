Copper InfluxDB
========
This example shows how to measure and store copper related time-series data in influxdb.  

The following copper metrics are available:
- Dequeue count
- Error count
- Finished count
- Invalid count
- Running count
- Waiting count

How it works
------------
Copper data is collected by telegraf and send to influxdb (***see also telegraf.conf***).  
Influxdb data can also be used in Copper-GUI (***see Copper-GUI section***).
Chronograf is an alternative GUI for vizualization of influxdb data.  
In Chronograf you can create your own dashboards for individual reports.

Running the example
-------------------
From CL in *copper-influxdb* run ***docker-compose up***.
All necessary images will be downloaded and started.  
This example requires a machine with high CPU and RAM resources. 

Open your local browser and enter 
***http://localhost:8080/copper/rest/orders/test***. 
Repeat the tests runs to create enough data for later analysis.


Viewing influxdb data in Copper-GUI
----------------------------------------
Copper-GUI is available at http://localhost:8081. Log on with admin/admin and click overview.
On the right you will see a button ***Connect to InfluxDb***.  
Press the button and enter http://localhost:8086 for the influxdb connection.  
Set user and password to 'admin' and press submit.If connection is successful you will see a green dot and a pop up with a confirmation message.  
Don't forget to set the "**Enable Influx Connection**" in the connection popup to enabled.
With influxdb connection enabled you will see the data from influxdb in your Copper GUI chart.

Viewing Charts with Chronograf
------------------------------
Chronograf GUI is available at http://localhost:8888 (use Chrome Browser on Linux).  
In Chronograf GUI go to Configuration and make sure that you have a valid connection to influxdb.
Next you can create new charts by clicking on the Dashboard menu.  
Click ***create new Dashboard*** and then click ***Add Data***.  
Click on ***telegraf.autogen*** to see possible parameters of Copper Workflow Engine. 
Next click on ***default@copper:1099***, copper parameters will appear on the right.
Now you can select the parameters that you want to vizualise.

Resources
---------
More information about telegraf and influxdb can be found at https://www.influxdata.com/

  