# ! /bin/bash

if [ $# -lt 1 ]
then
	echo
	echo '	- Aide -'
	echo "${0##*/} [nombre de stores]"
	echo '	- Fin -'
	echo
	exit 1	
else
	
	for i in `seq 0 $(($1 - 1))`;
	do
		echo "Serveur$i 5555$((($i * 2)+3)) kvstore$i ari-31-201-05 500$(($i * 2))\n";
		java -jar lib/kvstore.jar kvlite -root /tmp/kvroot$i -store kvstore$i -port 500$(($i * 2)) -admin 500$((($i * 2)+1)) &
	done

fi


