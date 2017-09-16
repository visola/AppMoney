#!/bin/bash

APPLICATION_PID=$(pgrep java)
echo "Stopping application..."
kill $APPLICATION_PID
