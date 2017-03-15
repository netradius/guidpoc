#!/bin/bash

# This shell script may be used to create a properly named migration script

DIR=$(dirname ${0})
cd ${DIR}
cd ..

DATE=$(date "+%Y%m%d%H%M%S")

echo "What do you want to name this script? Please no spaces"

read name

touch "V${DATE}__${name}.sql"