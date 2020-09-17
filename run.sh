#!/bin/bash

if [[ $1 == 'docker' ]]; then
  docker build -t sparkdemo .
  docker run -p 4567:4567 sparkdemo
else
  ./mvnw install exec:java -Dexec.mainClass="com.example.sparkdemo.Main"
fi
