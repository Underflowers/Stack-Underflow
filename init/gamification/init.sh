#!/bin/bash

# Register application
token=$(curl --location --request POST 'localhost:8080/applications' \
--silent \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Stack Underflow",
    "description": "Stack underflow is a Chinese clone of stack overflow.",
    "url": "flow.io"
}' | sed -e 's/[{""}]/''/g' | awk -F: '{ print $2 }')

echo $token

# Create badges
curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Newbie",
    "description": "Newly created account",
    "image": "https://cdn4.iconfinder.com/data/icons/badges-9/66/47-512.png"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Awesome question",
    "description": "User asked a question that got a lot of upvotes",
    "image": "https://cdn4.iconfinder.com/data/icons/badges-9/66/54-512.png"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Nice guy",
    "description": "User helped out by answering a question",
    "image": "https://cdn4.iconfinder.com/data/icons/badges-9/66/17-512.png"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Eejit",
    "description": "User asked a very dumb question",
    "image": "https://cdn4.iconfinder.com/data/icons/badges-9/66/40-512.png"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "C++",
    "description": "User asked a C++ related question",
    "image": "https://isocpp.org/assets/images/cpp_logo.png"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Python",
    "description": "User asked a Python related question",
    "image": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGvGShLAJbL5g1fezQUTHYX7zWX7XRXmNv8A&usqp=CAU"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Java",
    "description": "User asked a Java related question",
    "image": "https://www.solutions-numeriques.com/wp-content/uploads/2016/03/java.png"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Rust",
    "description": "User asked a Rust related question",
    "image": "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Rust_programming_language_black_logo.svg/1200px-Rust_programming_language_black_logo.svg.png"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Javascript",
    "description": "User asked a Javascript related question",
    "image": "https://qph.fs.quoracdn.net/main-qimg-b9bc9b7d8164c862b48c772daa8e81e1.webp"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "x86-64",
    "description": "User asked a x86-64 related question",
    "image": "https://assets.exercism.io/tracks/x86-64-assembly-hex-turquoise.png"
}'

curl --location --request POST 'localhost:8080/badges' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "ARM",
    "description": "User asked a ARM related question",
    "image": "https://electrosome.com/wp-content/uploads/2012/02/ARM-Cortex-A15-Processor.jpg"
}'


# Create scale points
curl --location --request POST 'http://localhost:8080/pointScales' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Reputation"
}'

curl --location --request POST 'http://localhost:8080/pointScales' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
    "name": "Activity"
}'


# Creat rules
curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "newUser",
   "badgeName": "Newbie",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "upVoted",
   "badgeName": "",
   "scaleName": "Reputation",
   "scalePoints": "1"
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "downVoted",
   "badgeName": "",
   "scaleName": "Reputation",
   "scalePoints": "-2"
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "voted",
   "badgeName": "",
   "scaleName": "Activity",
   "scalePoints": "1"
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "badQuestion",
   "badgeName": "Eejit",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "greatQuestion",
   "badgeName": "Awesome question",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "askedQuestion",
   "badgeName": "",
   "scaleName": "Activity",
   "scalePoints": "5"
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "answeredQuestion",
   "badgeName": "Nice guy",
   "scaleName": "Activity",
   "scalePoints": "5"
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "commented",
   "badgeName": "",
   "scaleName": "Activity",
   "scalePoints": "2"
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "cpp",
   "badgeName": "C++",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "python",
   "badgeName": "Python",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "java",
   "badgeName": "Java",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "rust",
   "badgeName": "Rust",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "javascript",
   "badgeName": "Javascript",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "x86",
   "badgeName": "x86-64",
   "scaleName": "",
   "scalePoints": ""
}'

curl --location --request POST 'localhost:8080/rules' \
--silent --output /dev/null \
--header 'Content-Type: application/json' \
--header "X-API-KEY: $token" \
--data-raw '{
   "eventType": "arm",
   "badgeName": "ARM",
   "scaleName": "",
   "scalePoints": ""
}'