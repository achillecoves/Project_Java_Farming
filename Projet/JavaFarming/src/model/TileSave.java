package model;

// Une classe interne pour représenter une case active
public class TileSave {
    public int col;
    public int row;
    public String type; // ex: "Graine de Blé" ou "Vache"
    public String state; // ex: "READY", "GROWING", "WAITING"

    public TileSave(int col, int row, String type, String state) {
        this.col = col;
        this.row = row;
        this.type = type;
        this.state = state;
    }
}
