Copper examples
===============
Copper examples is the result of a workflow engine review I did.
  
The examples are showing how copper workflow engine can be used in a cloud/microservice environment.
A lightweight JEE server and a postgres database are used together with docker container support.    
In this setup copper-engine runs within a webapp that utilizes JPA, Pooling and Jersey REST support provided by a
payara micro server.

Example projects
----------------
- Copper-starter project is a simple setup to test some core functionality. It has a simplistic workflow to show the basic 
working of copper engine.

- Copper-full project provides Copper-GUI and a REST based Mock-Server to run more realistic tests against copper-engine.

- Copper-influxdb shows how to add a time-series database to collect key parameter over time. 
In this example the Chronograf GUI is used to visualize the metrics.

- Copper-prometheus show how to add the prometheus time-series database to collect key parameter over time.
In this example the Grafana GUI is used to visualize the metrics. 

Detailed project instructions can be found in README files under the corresponding project dir.

Limitations
-----------
Workflow files are already compiled and read from the classpath. Therefore the 'on the fly
compilation' feature is not available. Therefore workflow source code in Copper-GUI is not available   

Prerequisites
------------- 
A working docker installation together with docker-compose is needed for execution.
For projects with Java files JDK 8 or higher is needed. 
All command specific instructions are for linux based systems.
 
Testing
------
You can immediately start tests from your local browser with a preconfigured test url. 
***See also instructions in README files in the corresponding project dir***.   
Log output is send to stdout. 

Building Docker Images
----------------------
To create customized docker images run a docker build with the **Dockerfile** under the corresponding project dir. 

Warning
-------
Examples in this repo are for exploration only and are **NOT** production ready. 
Especially security settings and passwords are not set.  

License
-------
All material released in this repo is licensed under the Apache 2.0 license.  
You can find more information about Apache 2.0 license at https://www.apache.org/licenses/LICENSE-2.0.

Resources
---------
More information about the copper workflow engine can be found at:  
https://github.com/copper-engine   
https://copper-engine.org/



