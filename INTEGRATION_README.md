# Intégration Klondike - Backend et Frontend

## Configuration avec Eclipse (Recommandé)

### Étape 1 : Lier les projets dans Eclipse

1. **Ouvrir Eclipse** avec votre workspace
2. **Clic droit** sur le projet `klondike-game` → **Properties**
3. Aller dans **Java Build Path**
4. Onglet **Projects** → Cliquer sur **Add...** (ou **Classpath** puis **Add Projects...**)
5. Sélectionner `klondike-front`
6. Cliquer sur **Apply and Close**

### Étape 2 : Vérifier la structure

Votre projet `klondike-game` devrait maintenant avoir accès aux classes du package `interfaceGraphique`.

### Étape 3 : Tester

1. Exécuter `TestInterfaceGraphique.java` comme application Java
2. Une fenêtre graphique devrait apparaître avec quelques cartes affichées

## Ce qui a été modifié

### Fichier `Carte.java`

✅ **Ajout de l'implémentation de l'interface `ICarte`**
- Import de `interfaceGraphique.ICarte`
- Déclaration : `public class Carte implements ICarte`

✅ **Ajout d'un attribut `visible`**
- Permet de gérer si la carte est visible (face) ou cachée (dos)

✅ **Méthodes ajoutées :**
- `setVisible(boolean visible)` : pour changer la visibilité
- `estVisible()` : pour vérifier la visibilité
- `getNomDeFichierPNG()` : implémentation de ICarte, retourne :
  - `"dos.png"` si la carte n'est pas visible
  - `"valeur_de_symbole.png"` si visible (ex: `"as_de_coeur.png"`)

### Exemples de noms de fichiers générés :

```
as_de_coeur.png
2_de_pique.png
10_de_carreau.png
roi_de_trefle.png
dame_de_coeur.png
dos.png (pour les cartes cachées)
```

## Utilisation dans votre code

### Créer une carte et l'afficher :

```java
// Créer l'interface graphique
InterfaceKlondike ig = new InterfaceKlondike();

// Créer une carte
Carte carte = new Carte(Carte.valeurCarte.roi, Carte.symboleCarte.coeur);
carte.setVisible(true); // La carte est visible

// Ajouter à une liste
List<Carte> tas = new ArrayList<>();
tas.add(carte);

// Afficher dans la défausse (indice 1)
ig.setTas(1, tas);
```

### Les indices des tas dans InterfaceKlondike :

- **0** : Pioche
- **1** : Défausse
- **2-5** : Pieux (4 pieux)
- **6-12** : Colonnes (7 colonnes)

## Prochaines étapes pour votre TP

1. ✅ Lier les deux projets (fait)
2. ✅ Implémenter ICarte dans Carte (fait)
3. 🔲 Modifier vos classes `Plateau`, `Pioche`, `Defausse`, `Colonne`, `Pieux` pour utiliser l'interface graphique
4. 🔲 Ajouter une méthode `afficher()` dans `Plateau` qui appelle `setTas()` pour chaque tas
5. 🔲 (Optionnel) Implémenter `ClickReporter` pour gérer les interactions souris

## Troubleshooting

### Erreur "ICarte cannot be resolved"
→ Vérifiez que le projet `klondike-front` est bien ajouté dans le Build Path

### Images non trouvées
→ Assurez-vous que le dossier `cards/` du projet `klondike-front` contient bien tous les fichiers PNG

### La fenêtre ne s'affiche pas
→ Vérifiez que vous avez bien créé un objet `InterfaceKlondike` et que votre programme ne se termine pas immédiatement
