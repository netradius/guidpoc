#!/bin/bash

DIR=$(dirname ${0})
cd ${DIR}
cd ..

pwd
mvn clean compile -DskipTests
mvn -P guidpoc flyway:clean flyway:migrate