#!/bin/bash
mkdir -p bin
javac -cp ".:lib/mysql-connector-j-9.1.0.jar" src/*.java -d bin
java -cp ".:lib/mysql-connector-j-9.1.0.jar:bin" LibrarySystem
