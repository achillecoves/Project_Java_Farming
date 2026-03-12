package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class User {

    private IntegerProperty money = new SimpleIntegerProperty();

    // Stocks des graines
    private IntegerProperty wheatSeedStock = new SimpleIntegerProperty();
    private IntegerProperty cornSeedStock = new SimpleIntegerProperty();
    private IntegerProperty carrotSeedStock = new SimpleIntegerProperty();
    private IntegerProperty potatoSeedStock = new SimpleIntegerProperty();

    // Stocks des récoltes de plantes
    private IntegerProperty wheatStock = new SimpleIntegerProperty();
    private IntegerProperty cornStock = new SimpleIntegerProperty();
    private IntegerProperty carrotStock = new SimpleIntegerProperty();
    private IntegerProperty potatoStock = new SimpleIntegerProperty();


    // Stocks des récoltes animales
    private IntegerProperty milkStock = new SimpleIntegerProperty();
    private IntegerProperty eggStock = new SimpleIntegerProperty();
    private IntegerProperty woolStock = new SimpleIntegerProperty();
    private IntegerProperty leatherStock = new SimpleIntegerProperty();

    // Stocks des animaux
    private IntegerProperty cowStock = new SimpleIntegerProperty();
    private IntegerProperty chickenStock = new SimpleIntegerProperty();
    private IntegerProperty sheepStock = new SimpleIntegerProperty();
    private IntegerProperty pigStock = new SimpleIntegerProperty();

    // Constructeur complet
    public User(int money, int wheatSeed, int cornSeed, int carrotSeed, int potatoSeed,
                int wheat, int corn, int carrot, int potato, int milkStock, int eggStock, int woolStock,
                int leatherStock, int cowStock, int chickenStock, int sheepStock, int pigStock) {

        this.money.set(money);

        // Initialisation Graines
        this.wheatSeedStock.set(wheatSeed);
        this.cornSeedStock.set(cornSeed);
        this.carrotSeedStock.set(carrotSeed);
        this.potatoSeedStock.set(potatoSeed);

        // Initialisation récoltes de plantes
        this.wheatStock.set(wheat);
        this.cornStock.set(corn);
        this.carrotStock.set(carrot);
        this.potatoStock.set(potato);

        // Initialisation récoltes des animaux
        this.milkStock.set(milkStock);
        this.eggStock.set(eggStock);
        this.woolStock.set(woolStock);
        this.leatherStock.set(leatherStock);

        // Initialisation récoltes des animaux
        this.cowStock.set(cowStock);
        this.chickenStock.set(chickenStock);
        this.sheepStock.set(sheepStock);
        this.pigStock.set(pigStock);

    }

    // --- MONEY ---
    public IntegerProperty moneyProperty() { return money; }
    public int getMoney() { return money.get(); }
    public void setMoney(int money) { this.money.set(money); }

    // --- GETTERS / SETTERS / PROPERTIES DES GRAINES ---

    // wheatSeedStock
    public IntegerProperty wheatSeedStockProperty() { return wheatSeedStock; }
    public int getWheatSeedStock() { return wheatSeedStock.get(); }
    public void setWheatSeedStock(int value) { this.wheatSeedStock.set(value); }

    // cornSeedStock
    public IntegerProperty cornSeedStockProperty() { return cornSeedStock; }
    public int getCornSeedStock() { return cornSeedStock.get(); }
    public void setCornSeedStock(int value) { this.cornSeedStock.set(value); }

    // carrotSeedStock
    public IntegerProperty carrotSeedStockProperty() { return carrotSeedStock; }
    public int getCarrotSeedStock() { return carrotSeedStock.get(); }
    public void setCarrotSeedStock(int value) { this.carrotSeedStock.set(value); }

    // potatoSeedStock
    public IntegerProperty potatoSeedStockProperty() { return potatoSeedStock; }
    public int getPotatoSeedStock() { return potatoSeedStock.get(); }
    public void setPotatoSeedStock(int value) { this.potatoSeedStock.set(value); }

    // --- GETTERS / SETTERS / PROPERTIES DES RÉCOLTES DE PLANTES ---

    // wheatStock
    public IntegerProperty wheatStockProperty() { return wheatStock; }
    public int getWheatStock() { return wheatStock.get(); }
    public void setWheatStock(int value) { this.wheatStock.set(value); }

    // cornStock
    public IntegerProperty cornStockProperty() { return cornStock; }
    public int getCornStock() { return cornStock.get(); }
    public void setCornStock(int value) { this.cornStock.set(value); }

    // carrotStock
    public IntegerProperty carrotStockProperty() { return carrotStock; }
    public int getCarrotStock() { return carrotStock.get(); }
    public void setCarrotStock(int value) { this.carrotStock.set(value); }

    // potatoStock
    public IntegerProperty potatoStockProperty() { return potatoStock; }
    public int getPotatoStock() { return potatoStock.get(); }
    public void setPotatoStock(int value) { this.potatoStock.set(value); }


    // --- GETTERS / SETTERS / PROPERTIES DES RÉCOLTES ANIMAL ---


    // milkStock
    public IntegerProperty milkStockProperty() { return milkStock; }
    public int getMilkStock() { return milkStock.get(); }
    public void setMilkStock(int value) { this.milkStock.set(value); }


    // woolStock
    public IntegerProperty woolStockProperty() { return woolStock; }
    public int getWoolStock() { return woolStock.get(); }
    public void setWoolStock(int value) { this.woolStock.set(value); }

    // eggStock
    public IntegerProperty eggStockProperty() { return eggStock; }
    public int getEggStock() { return eggStock.get(); }
    public void setEggStock(int value) { this.eggStock.set(value); }

    // eggStock
    public IntegerProperty leatherStockProperty() { return leatherStock; }
    public int getLeatherStock() { return leatherStock.get(); }
    public void setLeatherStock(int value) { this.leatherStock.set(value); }


    // --- GETTERS / SETTERS / PROPERTIES DES ANIMAUX ---

    // milkStock
    public IntegerProperty cowStockProperty() { return cowStock; }
    public int getCowStock() { return cowStock.get(); }
    public void setCowStock(int value) { this.cowStock.set(value); }

    // chickenStock
    public IntegerProperty chickenStockProperty() { return chickenStock; }
    public int getChickenStock() { return chickenStock.get(); }
    public void setChickenStock(int value) { this.chickenStock.set(value); }

    // sheepStock
    public IntegerProperty sheepStockProperty() { return sheepStock; }
    public int getSheepStock() { return sheepStock.get(); }
    public void setSheepStock(int value) { this.sheepStock.set(value); }

    // sheepStock
    public IntegerProperty pigStockProperty() { return pigStock; }
    public int getPigStock() { return pigStock.get(); }
    public void setPigStock(int value) { this.pigStock.set(value); }
}