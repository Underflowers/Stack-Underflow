FROM openliberty/open-liberty:kernel-java8-openj9-ubi

# Add my app and config
COPY --chown=1001:0  target/*.war /config/dropins/
