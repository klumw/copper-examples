Copper-Mock
===========
This is a simple mock backend for testing with copper-full project.
A ready to use mock-server image is available from docker hub. If you want to build
your own version you can use the Docker file in *copper-mock* dir.

Build
--------
From CL in *copper-mock dir* run ***./mvnw clean install*** command. 
After successful compilation you can run '***docker build -t \<image-name\> .***' to build your own docker image.