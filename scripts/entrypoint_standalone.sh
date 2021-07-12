#!/bin/bash

echo "==> Configuring the environment..."

echo JAVA_OPTS=$JAVA_OPTS

cd /home/app

java $JAVA_OPTS -jar pegasus-rest-api.jar