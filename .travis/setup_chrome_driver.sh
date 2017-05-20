#!/bin/bash
set -ex
mkdir_p /tmp/bin
cd /tmp/bin
wget https://chromedriver.storage.googleapis.com/2.29/chromedriver_linux64.zip
unzip chromedriver_linux64.zip

