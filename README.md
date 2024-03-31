# TurboChug!

CHUG! CHUG! CHUG!

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
