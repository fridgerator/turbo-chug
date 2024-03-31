# TurboChug!

CHUG! CHUG! CHUG!

Spring boot application to produce applications of 4 different types, and Kafka connect with MongoDB sync

Message types:
- Json without schema registry
- Json with schema registry
- Avro bytes without schema registry (specific class must be on classpath)
- Avro with schema registry

### Run it

1. cd into "avro-bytes-plugin" and `./gradlew assemble`
2. copy "avro-bytes-plugin/lib/build/libs/lib-0.0.2-SNAPSHOT.jar" into "turbo-chug-connect" directory
3. `docker compose up`
4. `docker compose run mongodb /bin/bash`
5. `mongosh mongodb:27017`
6. paste the uncommented contents mongo-init.js
7. create connectors by running all "create" requests in the [thunder](https://marketplace.visualstudio.com/items?itemName=rangav.vscode-thunder-client) collection
8. cd into "mult-producer" and `./gradlew bootrun`
9. connect via MongoDB Compass and see all 4 collections being populated from different message types

### Directory structure

- **avro-bytes-plugin**: Kafka connect plugin to deserialize from bytes without Schema registry
- **multi-producer**: Spring boot app which uses Faker library to generate messages of all 4 types
- **turbo-chug-connect**: Kafka connect docker setup
- **turbo-chug**: Toy application to dynamically create consumers, not being used

### Notes

- I cheated and copied the Futurama avro generated class into avro-bytes-plugin
  - Ideally this would be a shared schema library and imported as a dependency instead
- There is probably a better way to run the MongoDB commands automatically without the manual steps
