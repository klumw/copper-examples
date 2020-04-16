FROM klumw/payara-micro:5.201-jdk11

COPY ./target/copper-full-1.0-SNAPSHOT.war $DEPLOY_DIR
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=90.0"]
CMD ["-Dcom.sun.management.jmxremote.local.only=false","-Dcom.sun.management.jmxremote.port=1099","-Dcom.sun.management.jmxremote.rmi.port=1099","-Dcom.sun.management.jmxremote.authenticate=false","-Dcom.sun.management.jmxremote.ssl=false","-Dorg.copperengine.asm.api.version=7","-jar","payara-micro.jar","--port","8080","--deploymentDir","/opt/payara/deployments","--nocluster"]


