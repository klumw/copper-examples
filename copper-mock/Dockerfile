FROM payara/micro:5.2020.7-jdk11

COPY ./target/copper-mock-1.0-SNAPSHOT.war $DEPLOY_DIR
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=90.0"]
CMD ["-jar","payara-micro.jar","--port","8080","--deploymentDir","/opt/payara/deployments","--name","copper-mock"]



