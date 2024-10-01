#!/bin/bash

# 버전 번호를 변수로 정의
VERSION="1.5-prod"

# Jar 빌드
./gradlew clean bootJar

# Image 빌드 및 배포
docker build --platform linux/amd64 -f Dockerfile-prod -t kanghan/baemo:v$VERSION .
docker push kanghan/baemo:v$VERSION