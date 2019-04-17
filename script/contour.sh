#!/bin/bash

#파이썬 파일로 특정 디렉토리의 파일들에 해당하는 모델을 생성하는 쉘스크립트
python3 ../script/contour.py $1
python3 ../script/modelLoad.py
