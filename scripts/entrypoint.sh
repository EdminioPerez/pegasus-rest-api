#!/bin/bash

echo "==> Configuring the environment..."

echo JAVA_OPTS=$JAVA_OPTS

cd /home/app

# Part to check the DB is ready to connect

LIQUIBASE_STATUS_URL=${CONFIG_SERVER_URL}/actuator/liquibasestatus

echo Testing response from $LIQUIBASE_STATUS_URL

DATABASE_READY=0
RETRY_COUNTER=10
RESPONSE_PART=\"liquibase.running\":false

while [ $DATABASE_READY -eq 0 ] && [ $RETRY_COUNTER -gt 0 ]
do
  if curl --location --retry-connrefused --connect-timeout 120 --max-time 10 --retry 10 --retry-delay 5 $LIQUIBASE_STATUS_URL | grep "$RESPONSE_PART"
  then
    DATABASE_READY=1
  else
  	echo Retrying cause empty body? Retries left $RETRY_COUNTER
    sleep 10
    (( --RETRY_COUNTER ))
  fi
done

if [[ $DATABASE_READY -eq 0 ]] ; then
  echo 'Impossible to connect to database'
  exit 1
fi

# Part to download the bootstrap

BOOTSTRAT_PROPERTIES_URL=${CONFIG_SERVER_URL}/rest-core-service/${PROFILE}/${BRANCH}/pegasus-rest-api-bootstrap-${PROFILE}.properties

echo Trying to download bootstrap.properties from $BOOTSTRAT_PROPERTIES_URL

BOOTSTRAP_FILE_DOWNLOADED=0
RETRY_COUNTER=5

while [ $BOOTSTRAP_FILE_DOWNLOADED -eq 0 ] && [ $RETRY_COUNTER -gt 0 ]
do
  curl --location --retry-connrefused --connect-timeout 120 --max-time 10 --retry 10 --retry-delay 5 -o bootstrap.properties $BOOTSTRAT_PROPERTIES_URL
  
  if [[ -f bootstrap.properties ]]
  then
    BOOTSTRAP_FILE_DOWNLOADED=1
  else
  	echo Retrying cause empty body? Retries left $RETRY_COUNTER
    sleep 5
    (( --RETRY_COUNTER ))
  fi
done

if [[ ! -f bootstrap.properties ]] ; then
  echo 'File "bootstrap.properties" is not there, aborting.'
  exit 1
fi

printf '%b\n' "$(cat bootstrap.properties)"

java $JAVA_OPTS -jar pegasus-rest-api.jar