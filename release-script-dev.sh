#!/bin/bash

# 버전 번호를 변수로 정의
VERSION="0.180"

# Gradle clean 및 빌드
./gradlew clean bootJar

# Docker 이미지 빌드
docker build --platform linux/amd64 -f Dockerfile-dev -t kanghan/baemo:v$VERSION .

# Docker 이미지 푸시
docker push kanghan/baemo:v$VERSION