#!/usr/bin/env bash

set -e

ENV_FILE=".env.coongyaboard-db"

if [ ! -f "$ENV_FILE" ]; then
  echo "Env file not found: $ENV_FILE"
  exit 1
fi

# .env 로드
. "$ENV_FILE"

# 기존 컨테이너 있으면 정리 (있으면 stop/rm, 없으면 무시)
docker stop "$MYSQL_CONTAINER_NAME" 2>/dev/null || true
docker rm "$MYSQL_CONTAINER_NAME" 2>/dev/null || true

# 볼륨 없으면 생성
docker volume create "$MYSQL_VOLUME_NAME" >/dev/null 2>&1 || true

# MySQL 컨테이너 실행
docker run -d \
  --name "$MYSQL_CONTAINER_NAME" \
  -e MYSQL_ROOT_PASSWORD="$MYSQL_ROOT_PASSWORD" \
  -e MYSQL_DATABASE="$MYSQL_DATABASE" \
  -e MYSQL_USER="$MYSQL_USER" \
  -e MYSQL_PASSWORD="$MYSQL_PASSWORD" \
  -e TZ="$TZ" \
  -p "${MYSQL_PORT}:3306" \
  -v "${MYSQL_VOLUME_NAME}:/var/lib/mysql" \
  mysql:8.0 \
  mysqld \
    --character-set-server=utf8mb4 \
    --collation-server=utf8mb4_0900_ai_ci \
    --default-time-zone=+09:00
