#!/bin/bash

name=`basename $1`
echo 모델 트레이닝 시작
python3 ../script/train.py $1 
echo 트레이닝 완료.
touch trainLog
