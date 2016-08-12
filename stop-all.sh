#!/bin/bash
kill -9 $(ps aux | grep "webapp-runner" | awk '{print $2}')
kill -9 $(ps aux | grep "gulp" | awk '{print $2}')
