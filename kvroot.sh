# ! /bin/bash

if [ $# -lt 2 ]
then
    echo
    echo '	- Aide -'
    echo "${0##*/} [nombre de stores][hostname]"
    echo '	- Fin -'
    echo
    exit 1	
else
    for i in `seq 0 $(($1 - 1))`;
    do
	echo "Serveur$i 5555$((($i * 2)+3)) kvstore$i $2 500$(($i * 2))\n";
	java -jar lib/kvstore.jar kvlite -root /tmp/kvroot$i -store kvstore$i -port 500$(($i * 2)) -admin 500$((($i * 2)+1)) &
    done
    sleep 5
    echo "Press enter to continue once the kvstore(s) is/are ready"
    read a
    cd bin
    for i in `seq 0 $(($1 - 1))`;
    do
	java -cp "$CLASSPATH" kvstoreRmi.Serveur Serveur$i 5555$((($i * 2)+3)) kvstore$i $2 500$(($i * 2))&
    done
    sleep 1
    echo "Press enter to continue once the Server(s) is/are ready"
    read a
    java -cp "$CLASSPATH" kvstoreRmi.Gateway localhost 49999 $1 &
    sleep 1
fi


