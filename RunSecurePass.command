#!/bin/zsh

cd "$(dirname "$0")"

JAVA_HOME=$(/usr/libexec/java_home 2>/dev/null)

if [ -z "$JAVA_HOME" ]; then
    echo "Java JDK was not found. Please install a JDK and run this file again."
    read -k 1 "?Press any key to close..."
    exit 1
fi

mkdir -p out
"$JAVA_HOME/bin/javac" -d out src/Main.java src/PasswordGenerator.java src/PasswordStrengthChecker.java
"$JAVA_HOME/bin/java" -cp out Main
