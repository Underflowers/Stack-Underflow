#!/bin/sh

# Use this script to build a StackUnderflow image
TEAM_NAME="undeflowers"
APP_NAME="stack-underflow"

# https://stackoverflow.com/questions/59895/how-to-get-the-source-directory-of-a-bash-script-from-within-the-script-itself
ROOT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
WAR_PACKAGE_DIR="./target"
DOCKERFILE_DIR="./docker/images/flow"

cd $ROOT_DIR

echo "Scanning for projects..."

if [ ! -f "./pom.xml" ]
then
  echo "Project not found. Aborting..." >&2
  exit
fi

echo "Building WAR package..."
mvn package

PACKAGE="$(ls "$WAR_PACKAGE_DIR" | grep ".war")"
if [ ! -f "$WAR_PACKAGE_DIR/$PACKAGE" ]
then
    echo "Something went wrong when packaging the application. Aborting..." >&2
    exit
fi

echo "Preparing docker build..."
mv "$WAR_PACKAGE_DIR/$PACKAGE" "$DOCKERFILE_DIR/app.war"

echo "Building docker image..."
docker build "$DOCKERFILE_DIR" -t "$TEAM_NAME/$APP_NAME"
