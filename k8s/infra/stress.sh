#!/bin/bash
for i in {1..10000}; do
  curl localhost:30007/swagger-ui/index.html
  sleep $1
done
