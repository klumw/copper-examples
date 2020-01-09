Copper Examples
===============
Copper Examples is the result of a workflow engine review. 
The examples are showing how copper workflow engine can be used in a cloud environment.
A lightweight JEE server together with a postgres database are used as runtime environment packaged in docker containers.    
In this setup copper-engine runs within a webapp that utilizes JPA, Pooling and Jersey REST support provided by a
payara micro server.

Example projects
----------------
- Copper-starter project is a simple setup to test some core functionality. It has a simplistic workflow to show the basic 
working of copper engine.

- Copper-full project provides Copper-GUI and a REST based Mock-Server to run more realistic tests against copper-engine.

- Copper-influxdb shows how to add a time-series database to collect important key parameter over time. 
It comes with an additional alternative GUI to visualize the data. 

Detailed project instructions can be found in the README files under the corresponding project dir.

Limitations
-----------
Workflow files are already compiled and read from the classpath. Therefore the 'on the fly
compilation' feature is not available.

Prerequisites
------------- 
A working docker installation with docker-compose is needed for execution. All instructions in the README files are
for a linux based OS.
 
Testing
------
You can immediately start tests from your local browser with configured test url. 
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



