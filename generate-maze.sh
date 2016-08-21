#!/usr/bin/env bash

while [ true ]; do
  clear
  curl "http://localhost:8080/maze/basic?rows=16&columns=20"
  sleep 1
done
