#!/bin/bash

DIR=$(dirname ${0})
cd ${DIR}
cd ..

mvn clean compile -DskipTests
mvn -P guidpoc flyway:migrate