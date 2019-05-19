#  ./contour.sh ../originalSource/train/pci train/

base=`basename $1`
echo "path is $1"
echo "based on $base"

path=$2

echo "                    "
echo "####################"
echo " NOW CONTOURING"
echo "####################"
echo "                    "

for entry in $1_*
do
 echo "now process $entry"
 fileName=`basename -s ".${entry##*.}" "$entry"`
 echo $base $fileName
 python3 ../script/contour.py $path$base $path$fileName
 echo "done"
done
echo "                    "
echo "####################"
echo " CONTOURING END"
echo "####################"
echo "                    "

echo "                    "
echo "####################"
echo " NOW CONVERTING"
echo "####################"
echo "                    "

python3 ../script/nonTextClassification.py $path$base
echo "                    "
echo "####################"
echo " CONVERTING END"
echo "####################"
echo "                    "
