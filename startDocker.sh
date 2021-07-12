#!/bin/bash

docker run --name pegasus-rest-api -d -p 9197:9197 pegasus-rest-api:latest && docker logs -f pegasus-rest-api