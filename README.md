# LPCC

Ce projet à pour but de réaliser un compilateur de Language Littéral Progaming (.lpc). A la maniére d'un compilateur classique cette application peut extraire le Code, une documentation User et Dev différencié en LaTeX et en HTML.Le contenu des balises utilisé pour la compilation est personnalisable et le projet fonctionne avec tout les languages de programmation, créant des différents fichiers si votre code doit être divisé ainsi.

Instalation : Telecharger le dossier : LPCC_jar
Dans le dossier chercher le fichier LPCC.jar, à la racine et double cliquer dessus pour lancer le programme.

!!!! Attention !!!

Si vous supprimez le dossier LPCCConfig le programme ne se lancera pas, si cela arrive il faut recharger LPCCConfig sur ce dépot distant.


La syntaxe pour écrire les documents .lpc est similaire a HTML:

*Immuable :

  -title1 à title5 pour les titres;
  -color:codehexa pour mettre le texte en couleur 
  -img pour ajouter le lien vers une image
  
*Adaptable :

  -bd pour le style bold
  -it pour le style italique
  -link pour les liens hypertexte
  -ul pour souligner
  - plus ce que vous voulez (tant qu'il n'y a que un argument !)

Comme tout language de litterate programming le code est repéré par des balises spécifiques ($ par défaut) contenant un nom qui sert de clé d'identification. Cette clé d'identification permet d'appeller un morceau de code pour définit un autre en tant qu'il est définit quelque part. A l'instar de tout id le nommage d'un morceau de code est forcément unique dans un projet.


