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
	
	echo "Gateway.class Gateway port: 49999 \n";
	java bin/kvstoreRmi/Gateway &
	
	for i in `seq 0 $(($1 - 1))`;
	do
		echo "Serveur.class Serveur$i 5555$((($i * 2)+3)) kvstore$i Mini-Lenix 500$((($i * 2)+1))\n";
		java ./bin/kvstoreRmi/Serveur Serveur$i 5555$((($i * 2)+3)) kvstore$i Mini-Lenix 500$((($i * 2)+1)) &
	done

fi