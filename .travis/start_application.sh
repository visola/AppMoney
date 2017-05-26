#!/bin/bash
echo "Starting application server..."
./gradlew bootRun > bootRun.out 2>&1 &

WAIT_LOOP_COUNT=0

while [ "$WAIT_LOOP_COUNT" -le "60" ]; do
  LAST_LINE=$(tail -n1 bootRun.out)
  echo "[$WAIT_LOOP_COUNT] Last line is: $LAST_LINE"
  if [[ $LAST_LINE == *"Started AppMoney in"* ]]; then
    echo "Application server is up and running!"
    break
  fi
  WAIT_LOOP_COUNT=$(($WAIT_LOOP_COUNT+1))
  sleep 1
done

JOB_STATUS=$(jobs)
if [[ $JOB_STATUS == *"Exit"* ]]; then
  echo "Server failed to start, here is the output:"
  cat bootRun.out
  exit 1
fi

exit 0
