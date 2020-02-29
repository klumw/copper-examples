Copper examples
===============
Copper examples is the result of a workflow engine review I did.
The goal of my review was to find a workflow engine with the following features:
- Fully automated workflow execution
- High Performance/Load
- Auto-Recovery
- Cloud Ready
- Ease of use
- Multi node cloud deployments
- Integration into an existing Java environment
- Good Operational Insight
- Easy to learn
- Small footprint
 
After examining 26 workflow engines the clear winner was Copper.
One disadvantage of Copper was, that there were no cloud specific examples available.
This was the reason why I created the Copper Cloud Examples.

Copper doesn't feature a workflow modeler GUI.
Using modelers for more than 10 years I don't think that this is a disadvantage.  
A simple 'Hello World' looks great in a modeler, but with growing complexity UI models become 
very fast confusing and hard to maintain.
Copper workflows are just plain Java files, something that any Java developer knows inside out.

One last thing that makes Copper a good choice is that it is under steady development for the last 7 years
and if needed  you can get professional support.    
  

Example projects
----------------
Copper Cloud Examples shows how Copper can be used in a cloud/microservice environment.
A lightweight JEE server and a PostgreSQL database are used together with docker container support.    
In this setup copper-engine runs within a webapp that utilizes JPA, Pooling and Jersey REST support provided by a
payara micro server.

- ***copper-starter*** project is a simple setup to test some core functionality. It has a simplistic workflow to show the basic 
working of copper engine.

- ***copper-full*** project provides Copper-GUI and a REST based mock-server to run more realistic tests against copper-engine.

- ***copper-influxdb*** shows how to add a time-series database to collect metrics. 
In this example Chronograf GUI is used to visualize the metrics.

- ***copper-prometheus*** shows how to add the prometheus time-series database to collect metrics.
In this example Grafana GUI is used to visualize the metrics.

- ***copper-kubernetes*** shows how to setup the copper-full example using Kubernetes 

Documentation
-------------
To get an overview of Copper Engine and Copper GUI you should first read the document **Copper-Cloud-Examples.pdf** in the
*docs* folder.
Detailed project instructions can be found in the README files under the corresponding project dir.

Limitations
-----------
Workflow files are already compiled and read from the classpath. Therefore the 'on the fly
compilation' feature is not available. Workflow source code in Copper-GUI is not available.   

Prerequisites
------------- 
A working docker installation together with docker-compose is needed for execution.
For projects with Java files JDK 8 or higher is needed. 
All command specific instructions are for linux based systems.
 
Testing
------
You can immediately start tests from your local browser with a preconfigured test url. 
***See also instructions in the README files in the corresponding project dir***.   
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



