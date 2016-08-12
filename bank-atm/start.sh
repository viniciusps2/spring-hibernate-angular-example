#!/bin/bash

mvn -Dmaven.test.skip=true package && java -jar target/dependency/webapp-runner.jar --port ${1:-3003} target/*.war
