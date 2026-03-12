package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.User;

public class shopController {

    private User user;

    @FXML
    private Label lblShopMoney;

    // --- Boutons d'Achat ---
    @FXML
    private Button btnBuyWheatSeed;
    @FXML
    private Button btnBuyCornSeed;
    @FXML
    private Button btnBuyCarrotSeed;
    @FXML
    private Button btnBuyPotatoSeed;

    @FXML
    private Button btnBuyCow;

    @FXML
    private Button btnBuyChicken;

    @FXML
    private Button btnBuyPig;

    @FXML
    private Button btnBuySheep;

    // --- Nouveaux Boutons de Vente ---
    @FXML
    private Button btnSellWheat;

    @FXML
    private Button btnSellCorn;

    @FXML
    private Button btnSellCarrot;

    @FXML
    private Button btnSellPotato;

    @FXML
    private Button btnSellMilk;

    @FXML
    private Button btnSellEgg;

    @FXML
    private Button btnSellLeather;

    @FXML
    private Button btnSellWool;

    @FXML
    public void initialize() {

        // On configure les listeners pour chaque bouton
        btnBuyWheatSeed.setOnAction(event -> processPurchase(10, "wheat"));
        btnBuyCornSeed.setOnAction(event -> processPurchase(15, "corn"));
        btnBuyCarrotSeed.setOnAction(event -> processPurchase(5, "carrot"));
        btnBuyPotatoSeed.setOnAction(event -> processPurchase(12, "potato"));
        btnBuyCow.setOnAction(event -> processPurchase(100, "cow"));
        btnBuyChicken.setOnAction(event -> processPurchase(150, "chicken"));
        btnBuyPig.setOnAction(event -> processPurchase(50, "pig"));
        btnBuySheep.setOnAction(event -> processPurchase(120, "sheep"));

        // On configure les listeners pour chaque bouton
        btnSellWheat.setOnAction(event -> processSale(20, "wheat"));
        btnSellCorn.setOnAction(event -> processSale(30, "corn"));
        btnSellCarrot.setOnAction(event -> processSale(10, "carrot"));
        btnSellPotato.setOnAction(event -> processSale(24, "potato"));
        btnSellMilk.setOnAction(event -> processSale(40, "milk"));
        btnSellEgg.setOnAction(event -> processSale(60, "egg"));
        btnSellLeather.setOnAction(event -> processSale(20, "leather"));
        btnSellWool.setOnAction(event -> processSale(48, "wool"));

    }

    public void setUser(User user) {

        this.user = user;
        lblShopMoney.textProperty().bind(user.moneyProperty().asString("Mon Argent : %d$"));
    }

    private void processPurchase(int price, String type) {
        if (user != null && user.getMoney() >= price) {
            user.setMoney(user.getMoney() - price);

            switch (type) {
                case "wheat" -> user.setWheatSeedStock(user.getWheatSeedStock() + 1);
                case "corn" -> user.setCornSeedStock(user.getCornSeedStock() + 1);
                case "carrot" -> user.setCarrotSeedStock(user.getCarrotSeedStock() + 1);
                case "potato" -> user.setPotatoSeedStock(user.getPotatoSeedStock() + 1);
                case "cow" -> user.setCowStock(user.getCowStock() + 1);
                case "chicken" -> user.setChickenStock(user.getChickenStock() + 1);
                case "pig" -> user.setPigStock(user.getPigStock() + 1);
                case "sheep" -> user.setSheepStock(user.getSheepStock() + 1);
            }
            System.out.println("Achat réussi : +1 " + type);
        } else if (user != null) {
            System.out.println("Fonds insuffisants !");
        }
    }

    private void processSale(int price, String type) {
        if (user == null) return;

        boolean canSell = false;


        switch (type) {
            case "wheat" -> {
                if (user.getWheatStock() > 0) {
                    user.setWheatStock(user.getWheatStock() - 1);
                    canSell = true;
                }
            }
            case "corn" -> {
                if (user.getCornStock() > 0) {
                    user.setCornStock(user.getCornStock() - 1);
                    canSell = true;
                }
            }
            case "carrot" -> {
                if (user.getCarrotStock() > 0) {
                    user.setCarrotStock(user.getCarrotStock() - 1);
                    canSell = true;
                }
            }
            case "potato" -> {
                if (user.getPotatoStock() > 0) {
                    user.setPotatoStock(user.getPotatoStock() - 1);
                    canSell = true;
                }
            }
            case "milk" -> {
                if (user.getMilkStock() > 0) {
                    user.setMilkStock(user.getMilkStock() - 1);
                    canSell = true;
                }
            }
            case "egg" -> {
                if (user.getEggStock() > 0) {
                    user.setEggStock(user.getEggStock() - 1);
                    canSell = true;
                }
            }
            case "leather" -> {
                if (user.getLeatherStock() > 0) {
                    user.setLeatherStock(user.getLeatherStock() - 1);
                    canSell = true;
                }
            }
            case "wool" -> {
                if (user.getWoolStock() > 0) {
                    user.setWoolStock(user.getWoolStock() - 1);
                    canSell = true;
                }
            }
        }

        // Si on a pu retirer un légume, on donne l'argent
        if (canSell) {
            user.setMoney(user.getMoney() + price);
        }
    }
}