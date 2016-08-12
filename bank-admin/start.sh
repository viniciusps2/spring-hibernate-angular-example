#!/bin/bash

mvn package -Dmaven.test.skip=true && java -jar target/dependency/webapp-runner.jar --port ${1:-3002} target/*.war
