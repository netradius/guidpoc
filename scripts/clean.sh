#!/bin/bash

DIR=$(dirname ${0})
cd ${DIR}
cd ..


cd ../../../../../
mvn clean compile -DskipTests
mvn -P guidpoc flyway:clean
