package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.PauseTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class gameController {

    // -------------------------------------------------------------------------
    // Chemin du fichier de sauvegarde (dans le dossier home de l'utilisateur)
    // -------------------------------------------------------------------------
    private static final String SAVE_FILE = System.getProperty("user.home") + "/savegame.json";

    @FXML private GridPane farmGrid;
    @FXML private GridPane animalGrid;
    @FXML private Label lblUserMoney;
    @FXML private Label lblUserWheatSeedStock;
    @FXML private Label lblUserCornSeedStock;
    @FXML private Label lblUserCarrotSeedStock;
    @FXML private Label lblUserPotatoSeedStock;
    @FXML private Label lblUserWheatStock;
    @FXML private Label lblUserCornStock;
    @FXML private Label lblUserCarrotStock;
    @FXML private Label lblUserPotatoStock;
    @FXML private Label lblUserMilkStock;
    @FXML private Label lblUserEggStock;
    @FXML private Label lblUserLeatherStock;
    @FXML private Label lblUserWoolStock;
    @FXML private Label lblUserCowStock;
    @FXML private Label lblUserChickenStock;
    @FXML private Label lblUserPigStock;
    @FXML private Label lblUserSheepStock;
    @FXML private ComboBox comboSeedSelected;
    @FXML private ComboBox comboAnimalSelected;

    private WheatSeed wheatSeed   = new WheatSeed();
    private CornSeed cornSeed     = new CornSeed();
    private PotatoSeed potatoSeed = new PotatoSeed();
    private CarrotSeed carrotSeed = new CarrotSeed();

    private Cow     cow     = new Cow();
    private Pig     pig     = new Pig();
    private Sheep   sheep   = new Sheep();
    private Chicken chicken = new Chicken();

    private Map<String, PlantSeed> seeds   = new HashMap<>();
    private Map<String, Animal>    animals = new HashMap<>();

    private User user = new User(500,
            3, 2, 1, 5,
            10, 10, 10, 10,
            0, 0, 0, 0,
            5, 5, 5, 5);

    Image groundImage  = new Image(getClass().getResourceAsStream("/fxml/img/grassPlant.png"));
    Image groundImage2 = new Image(getClass().getResourceAsStream("/fxml/img/grassAnimal.jpg"));

    // =========================================================================
    // INITIALISATION
    // =========================================================================

    @FXML
    public void initialize() {
        createFarmGrid();
        addPlantComboBox();
        addAnimalComboBox();
        createAnimalGrid();

        seeds.put(wheatSeed.namePlant,   wheatSeed);
        seeds.put(cornSeed.namePlant,    cornSeed);
        seeds.put(carrotSeed.namePlant,  carrotSeed);
        seeds.put(potatoSeed.namePlant,  potatoSeed);

        animals.put(cow.nameAnimal,     cow);
        animals.put(pig.nameAnimal,     pig);
        animals.put(sheep.nameAnimal,   sheep);
        animals.put(chicken.nameAnimal, chicken);

        // Charge la save si elle existe, sinon garde les valeurs par défaut
        loadGame();

        // Lie les labels APRÈS avoir potentiellement mis à jour user via loadGame
        setUserVarOnLbl();

        // Enregistre la sauvegarde automatiquement à la fermeture de la fenêtre
        // On passe par un runLater pour être sûr que la Scene/Stage est disponible
        javafx.application.Platform.runLater(() -> {
            Stage stage = (Stage) farmGrid.getScene().getWindow();
            stage.setOnCloseRequest(event -> saveGame());
        });
    }

    // =========================================================================
    // SAUVEGARDE
    // =========================================================================

    /**
     * Sauvegarde l'état complet du jeu (User + tiles) dans un fichier JSON.
     * Appelée automatiquement à la fermeture de la fenêtre.
     */
    public void saveGame() {
        GameSave save = new GameSave();

        // --- Données du joueur ---
        save.money      = user.getMoney();
        save.wheatSeed  = user.getWheatSeedStock();
        save.cornSeed   = user.getCornSeedStock();
        save.carrotSeed = user.getCarrotSeedStock();
        save.potatoSeed = user.getPotatoSeedStock();
        save.wheat      = user.getWheatStock();
        save.corn       = user.getCornStock();
        save.carrot     = user.getCarrotStock();
        save.potato     = user.getPotatoStock();
        save.milk       = user.getMilkStock();
        save.egg        = user.getEggStock();
        save.leather    = user.getLeatherStock();
        save.wool       = user.getWoolStock();
        save.cow        = user.getCowStock();
        save.chicken    = user.getChickenStock();
        save.pig        = user.getPigStock();
        save.sheep      = user.getSheepStock();

        // --- Tiles de plantes actives ---
        for (Node node : farmGrid.getChildren()) {
            if (node instanceof ImageView) {
                ImageView tile = (ImageView) node;
                String data = (String) tile.getUserData();
                if (data != null && !data.isEmpty()) {
                    Integer col = GridPane.getColumnIndex(tile);
                    Integer row = GridPane.getRowIndex(tile);
                    // Découpe "GROWING:Graine de Blé" → state="GROWING", type="Graine de Blé"
                    int sep = data.indexOf(':');
                    String state = data.substring(0, sep);
                    String type  = data.substring(sep + 1);
                    save.plantedTiles.add(new TileSave(
                            col == null ? 0 : col,
                            row == null ? 0 : row,
                            type,
                            state
                    ));
                }
            }
        }

        // --- Tiles d'animaux actives ---
        for (Node node : animalGrid.getChildren()) {
            if (node instanceof ImageView) {
                ImageView tile = (ImageView) node;
                String data = (String) tile.getUserData();
                if (data != null && !data.isEmpty()) {
                    Integer col = GridPane.getColumnIndex(tile);
                    Integer row = GridPane.getRowIndex(tile);
                    int sep = data.indexOf(':');
                    String state = data.substring(0, sep);
                    String type  = data.substring(sep + 1);
                    save.animalTiles.add(new TileSave(
                            col == null ? 0 : col,
                            row == null ? 0 : row,
                            type,
                            state
                    ));
                }
            }
        }

        // --- Écriture JSON ---
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(save);
            Files.writeString(Path.of(SAVE_FILE), json);
            System.out.println("Sauvegarde réussie → " + SAVE_FILE);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    // =========================================================================
    // CHARGEMENT
    // =========================================================================

    /**
     * Charge la sauvegarde JSON et restaure l'état du jeu.
     * Si le fichier n'existe pas, rien ne se passe (valeurs par défaut conservées).
     */
    public void loadGame() {
        File saveFile = new File(SAVE_FILE);
        if (!saveFile.exists()) {
            System.out.println("Aucune sauvegarde trouvée, nouvelle partie.");
            return;
        }

        try {
            String json = Files.readString(Path.of(SAVE_FILE));
            Gson gson = new Gson();
            GameSave save = gson.fromJson(json, GameSave.class);

            // --- Restauration du joueur ---
            user.setMoney(save.money);
            user.setWheatSeedStock(save.wheatSeed);
            user.setCornSeedStock(save.cornSeed);
            user.setCarrotSeedStock(save.carrotSeed);
            user.setPotatoSeedStock(save.potatoSeed);
            user.setWheatStock(save.wheat);
            user.setCornStock(save.corn);
            user.setCarrotStock(save.carrot);
            user.setPotatoStock(save.potato);
            user.setMilkStock(save.milk);
            user.setEggStock(save.egg);
            user.setLeatherStock(save.leather);
            user.setWoolStock(save.wool);
            user.setCowStock(save.cow);
            user.setChickenStock(save.chicken);
            user.setPigStock(save.pig);
            user.setSheepStock(save.sheep);

            // --- Restauration des tiles de plantes ---
            if (save.plantedTiles != null) {
                for (TileSave ts : save.plantedTiles) {
                    ImageView tile = getTileAt(farmGrid, ts.col, ts.row);
                    if (tile == null) continue;

                    PlantSeed seed = seeds.get(ts.type);
                    if (seed == null) continue;

                    switch (ts.state) {
                        case "GROWING":
                            // On remet l'image "pousse" et on relance le timer
                            tile.setImage(seed.firstImage);
                            tile.setUserData("GROWING:" + ts.type);
                            PauseTransition growTimer = new PauseTransition(Duration.seconds(seed.growthTime));
                            growTimer.setOnFinished(e -> {
                                tile.setImage(seed.secondImage);
                                tile.setUserData("READY:" + ts.type);
                            });
                            growTimer.play();
                            break;

                        case "READY":
                            // Déjà prête à récolter
                            tile.setImage(seed.secondImage);
                            tile.setUserData("READY:" + ts.type);
                            break;
                    }
                }
            }

            // --- Restauration des tiles d'animaux ---
            if (save.animalTiles != null) {
                for (TileSave ts : save.animalTiles) {
                    ImageView tile = getTileAt(animalGrid, ts.col, ts.row);
                    if (tile == null) continue;

                    Animal animal = animals.get(ts.type);
                    if (animal == null) continue;

                    switch (ts.state) {
                        case "WAITING":
                            tile.setImage(animal.firstImage);
                            tile.setUserData("WAITING:" + ts.type);
                            break;

                        case "PRODUCING":
                            // On remet l'image "en production" et on relance le timer
                            tile.setImage(animal.secondImage);
                            tile.setUserData("PRODUCING:" + ts.type);
                            PauseTransition produceTimer = new PauseTransition(Duration.seconds(animal.produceTime));
                            produceTimer.setOnFinished(e -> {
                                tile.setImage(animal.thirdImage);
                                tile.setUserData("READY:" + ts.type);
                            });
                            produceTimer.play();
                            break;

                        case "READY":
                            tile.setImage(animal.thirdImage);
                            tile.setUserData("READY:" + ts.type);
                            break;
                    }
                }
            }

            System.out.println("Sauvegarde chargée avec succès !");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
        }
    }

    // =========================================================================
    // UTILITAIRE : retrouver une ImageView dans un GridPane par col/row
    // =========================================================================

    private ImageView getTileAt(GridPane grid, int col, int row) {
        for (Node node : grid.getChildren()) {
            Integer c = GridPane.getColumnIndex(node);
            Integer r = GridPane.getRowIndex(node);
            if ((c == null ? 0 : c) == col && (r == null ? 0 : r) == row) {
                if (node instanceof ImageView) return (ImageView) node;
            }
        }
        return null;
    }

    // =========================================================================
    // ESPACE PLANTES
    // =========================================================================

    private void addPlantComboBox() {
        comboSeedSelected.setItems(FXCollections.observableArrayList(
                wheatSeed.namePlant,
                cornSeed.namePlant,
                carrotSeed.namePlant,
                potatoSeed.namePlant
        ));
    }

    private void createFarmGrid() {
        farmGrid.setHgap(2);
        farmGrid.setVgap(2);

        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 10; row++) {
                ImageView tile = new ImageView(groundImage);
                tile.setFitWidth(30);
                tile.setFitHeight(30);

                final int finalCol = col;
                final int finalRow = row;

                tile.setOnMouseClicked(e -> handleTileClick(finalCol, finalRow, tile));
                farmGrid.add(tile, col, row);
            }
        }
    }

    private void handleTileClick(int col, int row, ImageView tile) {
        String tileState = (String) tile.getUserData();

        if (tileState != null && tileState.startsWith("READY")) {
            harvest(tile);
            return;
        }
        if (tileState != null && tileState.startsWith("GROWING")) {
            System.out.println("Attendez que ça pousse !");
            return;
        }

        String selectedSeed = (String) comboSeedSelected.getValue();
        if (selectedSeed == null) {
            System.out.println("Veuillez d'abord sélectionner une graine !");
            return;
        }

        PlantSeed seed = seeds.get(selectedSeed);
        boolean canPlant = false;

        switch (selectedSeed) {
            case "Graine de Blé":
                if (user.getWheatSeedStock() > 0) { user.setWheatSeedStock(user.getWheatSeedStock() - 1); canPlant = true; } break;
            case "Graine de Maïs":
                if (user.getCornSeedStock() > 0)  { user.setCornSeedStock(user.getCornSeedStock() - 1);   canPlant = true; } break;
            case "Graine de Carotte":
                if (user.getCarrotSeedStock() > 0){ user.setCarrotSeedStock(user.getCarrotSeedStock() - 1); canPlant = true; } break;
            case "Graine de Pomme de terre":
                if (user.getPotatoSeedStock() > 0){ user.setPotatoSeedStock(user.getPotatoSeedStock() - 1); canPlant = true; } break;
            default:
                System.out.println("Graine inconnue."); break;
        }

        if (canPlant) plantSeed(tile, seed);
        else System.out.println("Plus de stock pour : " + selectedSeed);
    }

    private void plantSeed(ImageView tile, PlantSeed seed) {
        tile.setImage(seed.firstImage);
        tile.setUserData("GROWING:" + seed.namePlant);

        PauseTransition pause = new PauseTransition(Duration.seconds(seed.growthTime));
        pause.setOnFinished(event -> {
            tile.setImage(seed.secondImage);
            tile.setUserData("READY:" + seed.namePlant);
        });
        pause.play();
    }

    private void harvest(ImageView tile) {
        String data = (String) tile.getUserData();
        if (data != null && data.startsWith("READY:")) {
            String plantName = data.substring(6);
            switch (plantName) {
                case "Graine de Blé":          user.setWheatStock(user.getWheatStock() + 1);     break;
                case "Graine de Maïs":         user.setCornStock(user.getCornStock() + 1);       break;
                case "Graine de Carotte":      user.setCarrotStock(user.getCarrotStock() + 1);   break;
                case "Graine de Pomme de terre": user.setPotatoStock(user.getPotatoStock() + 1); break;
            }
            System.out.println("Récolte de " + plantName + " effectuée !");
            tile.setImage(groundImage);
            tile.setUserData(null);
        }
    }

    // =========================================================================
    // ESPACE ANIMAUX
    // =========================================================================

    private void addAnimalComboBox() {
        comboAnimalSelected.setItems(FXCollections.observableArrayList(
                cow.nameAnimal,
                pig.nameAnimal,
                sheep.nameAnimal,
                chicken.nameAnimal
        ));
    }

    private void createAnimalGrid() {
        animalGrid.setHgap(5);
        animalGrid.setVgap(5);

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                ImageView animalTile = new ImageView(groundImage2);
                animalTile.setFitWidth(80);
                animalTile.setFitHeight(60);

                final int finalColAnimal = col;
                final int finalRowAnimal = row;

                animalTile.setOnMouseClicked(e -> handleAnimalTileClick(finalColAnimal, finalRowAnimal, animalTile));
                animalGrid.add(animalTile, col, row);
            }
        }
    }

    private void handleAnimalTileClick(int finalColAnimal, int finalRowAnimal, ImageView animalTile) {
        String AnimalTileState = (String) animalTile.getUserData();

        if (AnimalTileState != null && AnimalTileState.startsWith("READY")) { collect(animalTile); return; }
        if (AnimalTileState != null && AnimalTileState.startsWith("PRODUCING:")) { System.out.println("Vous devez attendre !"); return; }
        if (AnimalTileState != null && AnimalTileState.startsWith("WAITING:"))  { feed(animalTile); return; }

        String selectedAnimal = (String) comboAnimalSelected.getValue();
        if (selectedAnimal == null) { System.out.println("Veuillez d'abord sélectionner un Animal !"); return; }

        Animal animal = animals.get(selectedAnimal);
        boolean canPlace = false;

        switch (selectedAnimal) {
            case "Vache":  if (user.getCowStock()     > 0) { user.setCowStock(user.getCowStock() - 1);         canPlace = true; } break;
            case "Cochon": if (user.getPigStock()     > 0) { user.setPigStock(user.getPigStock() - 1);         canPlace = true; } break;
            case "Mouton": if (user.getSheepStock()   > 0) { user.setSheepStock(user.getSheepStock() - 1);     canPlace = true; } break;
            case "Poule":  if (user.getChickenStock() > 0) { user.setChickenStock(user.getChickenStock() - 1); canPlace = true; } break;
            default: System.out.println("Animal inconnu."); break;
        }

        if (canPlace) placeAnimal(animalTile, animal);
        else System.out.println("Plus de stock pour : " + selectedAnimal);
    }

    private void collect(ImageView animalTile) {
        String data = (String) animalTile.getUserData();
        if (data != null && data.startsWith("READY:")) {
            String animalName = data.substring(6);
            switch (animalName) {
                case "Vache":  user.setMilkStock(user.getMilkStock() + 1);       animalTile.setImage(cow.firstImage);     break;
                case "Cochon": user.setLeatherStock(user.getLeatherStock() + 1); animalTile.setImage(pig.firstImage);     break;
                case "Mouton": user.setWoolStock(user.getWoolStock() + 1);       animalTile.setImage(sheep.firstImage);   break;
                case "Poule":  user.setEggStock(user.getEggStock() + 1);         animalTile.setImage(chicken.firstImage); break;
            }
            System.out.println("Récolte de " + animalName + " effectuée !");
            animalTile.setUserData("WAITING:" + animalName);
        }
    }

    private void feed(ImageView animalTile) {
        String data = (String) animalTile.getUserData();
        if (data == null || !data.startsWith("WAITING:")) return;
        String animalName = data.substring(8);

        switch (animalName) {
            case "Vache":
                if (user.getWheatStock() >= 1) {
                    user.setWheatStock(user.getWheatStock() - 1);
                    animalTile.setUserData("PRODUCING:Vache");
                    animalTile.setImage(cow.secondImage);
                    PauseTransition t1 = new PauseTransition(Duration.seconds(cow.produceTime));
                    t1.setOnFinished(e -> { animalTile.setImage(cow.thirdImage); animalTile.setUserData("READY:Vache"); });
                    t1.play();
                } else System.out.println("Pas assez de blé !");
                break;

            case "Cochon":
                if (user.getCarrotStock() >= 1) {
                    user.setCarrotStock(user.getCarrotStock() - 1);
                    animalTile.setUserData("PRODUCING:Cochon");
                    animalTile.setImage(pig.secondImage);
                    PauseTransition t2 = new PauseTransition(Duration.seconds(pig.produceTime));
                    t2.setOnFinished(e -> { animalTile.setImage(pig.thirdImage); animalTile.setUserData("READY:Cochon"); });
                    t2.play();
                } else System.out.println("Pas assez de carottes !");
                break;

            case "Mouton":
                if (user.getPotatoStock() >= 1) {
                    user.setPotatoStock(user.getPotatoStock() - 1);
                    animalTile.setUserData("PRODUCING:Mouton");
                    animalTile.setImage(sheep.secondImage);
                    PauseTransition t3 = new PauseTransition(Duration.seconds(sheep.produceTime));
                    t3.setOnFinished(e -> { animalTile.setImage(sheep.thirdImage); animalTile.setUserData("READY:Mouton"); });
                    t3.play();
                } else System.out.println("Pas assez de pommes de terre !");
                break;

            case "Poule":
                if (user.getCornStock() >= 1) {
                    user.setCornStock(user.getCornStock() - 1);
                    animalTile.setUserData("PRODUCING:Poule");
                    animalTile.setImage(chicken.secondImage);
                    PauseTransition t4 = new PauseTransition(Duration.seconds(chicken.produceTime));
                    t4.setOnFinished(e -> { animalTile.setImage(chicken.thirdImage); animalTile.setUserData("READY:Poule"); });
                    t4.play();
                } else System.out.println("Pas assez de maïs !");
                break;
        }
    }

    private void placeAnimal(ImageView animalTile, Animal animal) {
        animalTile.setImage(animal.firstImage);
        animalTile.setUserData("WAITING:" + animal.nameAnimal);
    }

    // =========================================================================
    // BOUTIQUE & LABELS
    // =========================================================================

    @FXML
    private void openShop() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shop.fxml"));
            Parent root = loader.load();

            shopController controller = loader.getController();
            controller.setUser(this.user);

            Stage shopStage = new Stage();
            shopStage.setTitle("Boutique de la ferme");
            shopStage.setScene(new Scene(root));
            shopStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur : Vérifiez le chemin du fichier shop.fxml");
        }
    }

    private void setUserVarOnLbl() {
        lblUserMoney.textProperty().bind(user.moneyProperty().asString("Argent: %d$"));

        lblUserWheatSeedStock.textProperty().bind(user.wheatSeedStockProperty().asString("%d Graine de Blé"));
        lblUserCornSeedStock.textProperty().bind(user.cornSeedStockProperty().asString("%d Graine de Maïs"));
        lblUserCarrotSeedStock.textProperty().bind(user.carrotSeedStockProperty().asString("%d Graine de Carotte"));
        lblUserPotatoSeedStock.textProperty().bind(user.potatoSeedStockProperty().asString("%d Graine de Pomme de terre"));

        lblUserWheatStock.textProperty().bind(user.wheatStockProperty().asString("%d Blé"));
        lblUserCornStock.textProperty().bind(user.cornStockProperty().asString("%d Maïs"));
        lblUserCarrotStock.textProperty().bind(user.carrotStockProperty().asString("%d Carotte"));
        lblUserPotatoStock.textProperty().bind(user.potatoStockProperty().asString("%d Pomme de terre"));

        lblUserMilkStock.textProperty().bind(user.milkStockProperty().asString("%d Lait"));
        lblUserEggStock.textProperty().bind(user.eggStockProperty().asString("%d Oeuf"));
        lblUserLeatherStock.textProperty().bind(user.leatherStockProperty().asString("%d Cuir"));
        lblUserWoolStock.textProperty().bind(user.woolStockProperty().asString("%d Laine"));

        lblUserCowStock.textProperty().bind(user.cowStockProperty().asString("%d Vache"));
        lblUserChickenStock.textProperty().bind(user.chickenStockProperty().asString("%d Poule"));
        lblUserPigStock.textProperty().bind(user.pigStockProperty().asString("%d Cochon"));
        lblUserSheepStock.textProperty().bind(user.sheepStockProperty().asString("%d Mouton"));
    }
}