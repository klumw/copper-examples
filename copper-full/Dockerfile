FROM payara/micro:5.201

COPY ./target/copper-full-1.0-SNAPSHOT.war $DEPLOY_DIR
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=90.0"]
CMD ["-Dcom.sun.management.jmxremote.local.only=false","-Dcom.sun.management.jmxremote.port=1099","-Dcom.sun.management.jmxremote.rmi.port=1099","-Dcom.sun.management.jmxremote.authenticate=false","-Dcom.sun.management.jmxremote.ssl=false","-jar","payara-micro.jar","--port","8082","--deploymentDir","/opt/payara/deployments","--nocluster"]


