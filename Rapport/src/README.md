ABDR
====
lancer sh kvroot.sh 2 ari-31-201-05 (pour creer 2 serveurs sur le poste ari-31-201-05 )
puis recuperer le retour pour lancer les Serveur.java avec cette chaine de charactere en argument

par exemple :
- Lancer les serveurs
	- java Serveur Serveur0 55553 kvstore0 ari-31-201-05 5001
	- java Serveur Serveur1 55555 kvstore1 ari-31-201-05 5003
- Puis lancer le Gateway
	- java Gateway 192.168.1.31 49999 2
- Puis lancer les clients
	- java Client 192.168.1.31 49999
	- java Client 192.168.1.31 49999

-------------------
pour fair le bench des transaction
	taper peupler 1000		; pour charger une base avec 1000 profils
puis 	taper etape1 1000 	; dans chaque client

-------------------
pour verifier la migration de donnée
modifier le code de etape1 et changer false en true
	taper peupler 1000		; pour charger une base avec 1000 profils
puis 	taper etape1 1000	; 

-------------------------
pour verifier les fonctions de base et la transaction multiclé.
	taper peupler 1000	; pour charger une base avec 1000 profils
puis 	taper afficherProfil 0	; pour afficher tous les attributs des objets du profil 0
puis	afficherNbObjets 1	; pour afficher le nombre d'objets du profil 1
puis 	ajouterMultiCle 0 1	; pour effectuer une transaction multiClé
puis	afficherNbObjets 0
et	afficherNbObjets 1	; pour verifier que la transaction multiclé s'est bien deroulé.

-------------------------
pour verifier la suppression
faire 	supprimer 0		; pour supprimer le profil 0.
puis 	afficherNbObjets 0	; pour verifier que la suppression s'est bien deroulé.

-------------------------
Pour produire un graph pour les benchmark :

- Avoir un fichier texte de 2 colonnes, absisse et ordonnée.
- lancer gnuplot puis plot "non du fichier" using 1:2 with linespoints
- pour produire un fichier .ps toujours dans gnuplot executer les commnades suivantes :
  - set out "nom du fichier.ps"
  - set terminal postscript
  - replot
  - quit
- Pour produire le pdf lancer ps2pdf "nom du fichier.ps"
- l'inclure dans le latex avec:
  \begin{figure}
    \centering
    \includegraphics[scale=0.5]{nom du fichier} #sans extension
    \caption{La légende à mettre pour la figure}
  \end{figure}
