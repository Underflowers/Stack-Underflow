#!/bin/bash

# Register application
token=$(curl --location --request POST 'localhost:8080/applications' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Stack Underflow",
    "description": "Stack underflow is a Chinese clone of stack overflow.",
    "url": "flow.io"
}' | sed -e 's/[{""}]/''/g' | awk -F: '{ print $2 }')

# Create badges
curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Newbie",
    "description": "Newly created account",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Awesome question",
    "description": "User asked a question that got a lot of upvotes",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Nice guy",
    "description": "User helped out by answering a question",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Eejit",
    "description": "User asked a very dumb question",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "C++",
    "description": "User asked a C++ related question",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Python",
    "description": "User asked a Python related question",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Java",
    "description": "User asked a Java related question",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Rust",
    "description": "User asked a Rust related question",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Javascript",
    "description": "User asked a Javascript related question",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "x86",
    "description": "User asked a x86 related question",
    "image": "n/a"
}'

curl --location --request POST 'localhost:8080/badges' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "ARM",
    "description": "User asked a ARM related question",
    "image": "n/a"
}'


# Create scale points
curl --location --request POST 'http://localhost:8080/pointScales' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Reputation"
}'

curl --location --request POST 'http://localhost:8080/pointScales' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Activity"
}'


# Creat rules
curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "newUser",
   "badgeName": "Newbie",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "upVoted",
   "badgeName": "",
   "scaleName": "Reputation",
   "scalePoints": "1"
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "downVoted",
   "badgeName": "",
   "scaleName": "Reputation",
   "scalePoints": "-2"
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "voted",
   "badgeName": "",
   "scaleName": "Activity",
   "scalePoints": "1"
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "badQuestion",
   "badgeName": "Eejit",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "greatQuestion",
   "badgeName": "Awesome question",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "askedQuestion",
   "badgeName": "",
   "scaleName": "Activity",
   "scalePoints": "5"
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "answeredQuestion",
   "badgeName": "Nice guy",
   "scaleName": "Activity",
   "scalePoints": "5"
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "commented",
   "badgeName": "",
   "scaleName": "Activity",
   "scalePoints": "2"
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "cpp",
   "badgeName": "C++",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "python",
   "badgeName": "Python",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "java",
   "badgeName": "Java",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "rust",
   "badgeName": "Rust",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "javascript",
   "badgeName": "Javascript",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "x86",
   "badgeName": "x86",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "arm",
   "badgeName": "ARM",
   "scaleName": "",
   "scalePoints": ""
}'