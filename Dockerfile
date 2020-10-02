FROM openliberty/open-liberty:kernel-java8-openj9-ubi

# Copy the open liberty config and .war
COPY --chown=1001:0 src/main/liberty/config.xml /config/
COPY --chown=1001:0 target/*.war /config/apps/
