#!/bin/bash

mvn clean install
java -Dasm=asmfiles/testasm -jar target/PocketPC-0.1-SNAPSHOT-jar-with-dependencies.jar 
