# const define
startText="STRING.*SELECT"
sectionStartText="STRING"
sectionEndText="END-STRING"
resultFile="result.txt"
TRUE=0
FALSE=1

inSection=$FALSE
startFlg=$FALSE
rowNo=1

textList=()

dateStamp=`date "+%Y%m%d%H%M%S"`


function job(){
    echo "" > $resultFile
    run $1
}

function nextKeyValue(){
    while read line
    do
        # if 
        # # file start
        # isStart "$line"
        # if [ $? == $FALSE ]; then
        #     continue
        # fi

        # # load section
        # hasInSection "$line"
        # if [ $? == $TRUE ]; then
        #     textList[${#textList[@]}]=$line

        #     endSection "$line"
        #     if [ $? == $TRUE ]; then
        #         map
        #         textList=()
        #     fi
        # fi

        # # echo "loop:" $rowNo
        # ((rowNo++))
    done < $1

}

function run(){
    while (nextKeyValue()) {
        map(rowNo, textLine)
    }
}

# fileSplitor
function isStart(){
    if [ $startFlg == $TRUE ]; then
        return $TRUE
    elif ( echo $1 | egrep $startText &> /dev/null; ) then
        startFlg=$TRUE
        return $TRUE
    else
        return $FALSE
    fi
}

# fileSplitor
function hasInSection(){
    if [ $inSection == $TRUE ]; then
        return $TRUE
    elif ( echo $1 | egrep $sectionStartText &> /dev/null; ) then
        inSection=$TRUE
        return $TRUE
    else
        return $FALSE
    fi
}

# fileSplitor
function endSection(){
    if ( echo $1 | egrep $sectionEndText &> /dev/null; ) then
        inSection=$FALSE
        return $TRUE
    else
        return $FALSE
    fi
}

#map
function map(){
    resultStr=""
    for(( i=0; i<${#textList[@]}; i++ ))
    do
        row=${textList[$i]}
        charat7=`echo "$row" | cut -c 7`
        if ( echo "$row" | egrep '' )
            continue
        fi
        if ( echo "$row" | egrep '\".*\"' &> /dev/null; ) then
            temp=`echo "$row" | sed -r 's#(.*)\"(.*)\"#\2#'`
            resultStr="$resultStr$temp"
        fi
    done

    echo "$resultStr" >> $resultFile
}

#run $1
job $1
