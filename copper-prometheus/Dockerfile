FROM klumw/copper-full:5.2020.7-jdk11

COPY ./resources/jmx_prometheus_javaagent-0.12.1-SNAPSHOT.jar /opt/payara
COPY ./resources/jmxconfig.yml /opt/payara
COPY ./resources/prometheus.yml /opt/payara

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=90.0","-javaagent:./jmx_prometheus_javaagent-0.12.1-SNAPSHOT.jar=8083:./jmxconfig.yml"]
CMD ["-Dcom.sun.management.jmxremote.local.only=false","-Dcom.sun.management.jmxremote.port=1099","-Dcom.sun.management.jmxremote.rmi.port=1099","-Dcom.sun.management.jmxremote.authenticate=false","-Dcom.sun.management.jmxremote.ssl=false","-Dorg.copperengine.asm.api.version=7","-jar","payara-micro.jar","--port","8080","--deploymentDir","/opt/payara/deployments","--nocluster"]

