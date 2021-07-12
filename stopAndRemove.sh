#!/bin/bash

docker stop pegasus-rest-api && docker ps -a | awk '{ print $1,$2 }' | grep pegasus-rest-api:latest | awk '{print $1 }' | xargs -I {} docker rm {}