#!/bin/bash
mvn clean package
cd ..
docker-compose up --build
exec bash