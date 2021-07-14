#!/bin/bash

set -e
echo "$JAVA_HOME" > /tmp/111.txt
$JAVA_HOME/bin/java -jar ~/learn/kotlin/kols/build/libs/kols-1.0-SNAPSHOT.jar

echo "im from vscode-client" > /tmp/222.txt
