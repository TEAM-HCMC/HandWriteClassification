#!/bin/bash


echo This is $1

#

#파이썬 파일로 특정 디렉토리의 파일들에 해당하는 모델을 생성하는 쉘스크립트
python3 ../script/contour.py $1

echo FROM $1
echo 오브젝트 추출 완료.

python3 ../script/nonTextClassification.py $1
#Todo 오브젝트 분류 모델 거치기

