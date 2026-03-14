# 🌾 FarmGame – Jeu de Ferme JavaFX

Un jeu de simulation agricole développé en **Java** avec **JavaFX**, dans lequel vous gérez une ferme : cultivez des plantes, élevez des animaux, achetez et vendez des ressources dans la boutique, et sauvegardez votre progression !

---

## 📸 Aperçu

> _Lancez le jeu pour découvrir l'interface !_  
> Menu principal → Jeu → Boutique

---

## 🎮 Fonctionnalités

### 🌱 Culture de plantes
- Grille de culture **10×10** cliquable
- 4 types de plantes disponibles :
  | Plante | Graine | Prix d'achat | Prix de vente |
  |---|---|---|---|
  | 🌾 Blé | Graine de Blé | 10$ | 20$ |
  | 🌽 Maïs | Graine de Maïs | 15$ | 30$ |
  | 🥕 Carotte | Graine de Carotte | 5$ | 10$ |
  | 🥔 Pomme de terre | Graine de Pomme de terre | 12$ | 24$ |
- Les plantes poussent avec une **animation temporisée** (état : `GROWING` → `READY`)
- Cliquez sur une case prête pour **récolter**

### 🐄 Élevage d'animaux
- Grille d'animaux **4×4** cliquable
- 4 types d'animaux avec leur production :
  | Animal | Nourriture | Production | Prix d'achat | Prix de vente |
  |---|---|---|---|---|
  | 🐄 Vache | Blé | Lait | 100$ | 40$ |
  | 🐷 Cochon | Carotte | Cuir | 50$ | 20$ |
  | 🐑 Mouton | Pomme de terre | Laine | 120$ | 48$ |
  | 🐔 Poule | Maïs | Œuf | 150$ | 60$ |
- Cycle de production en 3 étapes : **Attente** → **Production** → **Prêt à récolter**
- Nourrissez l'animal pour lancer la production, puis récoltez une fois qu'il est prêt

### 🏪 Boutique
- Achetez des **graines** et des **animaux**
- Vendez vos **récoltes** et **productions animales**
- Affichage en temps réel de votre argent via **binding JavaFX**

### 💾 Sauvegarde automatique
- La progression est **sauvegardée automatiquement** à la fermeture de la fenêtre de jeu
- Chargement automatique au démarrage
- Format de sauvegarde : **JSON** (via la bibliothèque Gson)
- Fichier : `~/savegame.json`

---

## 🗂️ Structure du projet

```
src/
├── controller/
│   ├── gameController.java       # Logique principale du jeu
│   ├── menuController.java       # Navigation du menu
│   ├── shopController.java       # Gestion de la boutique
│   └── creditsController.java    # Écran des crédits
├── model/
│   ├── User.java                 # Données du joueur (argent, stocks)
│   ├── Animal.java               # Classe abstraite Animal
│   ├── PlantSeed.java            # Classe abstraite PlantSeed
│   ├── Chicken.java / Cow.java / Pig.java / Sheep.java
│   ├── WheatSeed.java / CornSeed.java / CarrotSeed.java / PotatoSeed.java
│   └── GameSave.java / TileSave.java  # Modèles de sauvegarde
resources/
└── fxml/
    ├── menu.fxml
    ├── game.fxml
    ├── shop.fxml
    ├── credits.fxml
    └── img/                      # Images des plantes et animaux
```

---

## 🛠️ Technologies utilisées

- **Java 17+**
- **JavaFX** – Interface graphique (FXML, SceneBuilder)
- **Gson** – Sérialisation/désérialisation JSON pour la sauvegarde
- **Maven** ou **Gradle** (selon votre configuration)

---

## 🚀 Lancement du projet

### Prérequis
- Java 17 ou supérieur
- JavaFX SDK installé
- Gson dans le classpath (ou via Maven/Gradle)

### Avec Maven

```bash
git clone https://github.com/votre-utilisateur/farmgame.git
cd farmgame
mvn javafx:run
```

### Avec un IDE (IntelliJ / Eclipse)

1. Clonez le dépôt
2. Ouvrez le projet dans votre IDE
3. Configurez le **JavaFX SDK** dans les paramètres VM :
   ```
   --module-path /chemin/vers/javafx/lib --add-modules javafx.controls,javafx.fxml
   ```
4. Lancez la classe principale `Main.java`

---

## 🎯 Comment jouer

1. **Menu principal** → cliquez sur **Jouer**
2. Sélectionnez une **graine** dans le menu déroulant, puis cliquez sur une case de la grille pour planter
3. Attendez que la plante soit prête (`GROWING` → `READY`), puis cliquez pour **récolter**
4. Sélectionnez un **animal** dans le menu déroulant, puis placez-le sur une case de la grille animale
5. Cliquez sur l'animal pour le **nourrir** (consomme une ressource), attendez la production, puis cliquez pour **récolter**
6. Ouvrez la **Boutique** pour acheter des graines/animaux ou vendre vos ressources
7. Fermez la fenêtre : la partie est **sauvegardée automatiquement**

---

## 👥 Crédits

Accessible depuis le menu principal via le bouton **Crédits**.
