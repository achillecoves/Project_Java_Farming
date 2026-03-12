// Dans un nouveau fichier GameSave.java
package model;
import java.util.ArrayList;
import java.util.List;

public class GameSave {
    // Données de l'utilisateur (on utilise des types simples pour la save)
    public int money;
    public int wheatSeed, cornSeed, carrotSeed, potatoSeed;
    public int wheat, corn, carrot, potato;
    public int milk, egg, leather, wool;
    public int cow, chicken, pig, sheep;

    // Données du terrain
    public List<TileSave> plantedTiles = new ArrayList<>();
    public List<TileSave> animalTiles = new ArrayList<>();
}

