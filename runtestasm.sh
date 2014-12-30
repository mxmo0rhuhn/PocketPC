#!/bin/bash

mvn clean install
java -Dmode=STEP -Dasm=asmfiles/testasm -Dnogui=true -DBLOCKS_PER_LINE=5 -jar target/PocketPC-0.1-SNAPSHOT-jar-with-dependencies.jar 
