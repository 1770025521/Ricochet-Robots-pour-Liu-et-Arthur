package isep.ricochetrobot;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Robot {

    private Color color;
    private int posX;
    private int posY;
    private boolean[] block;
    private String url;
    Node gui;

    public Robot(Color color, int posX, int posY){
        this.color = color;
        this.url = color.name() + "_robot.png";
        this.posX = posX;
        this.posY = posY;
    }

    //Constructeur pour copie
    public Robot(Robot rb){
        this.color = rb.color;
        this.posX  = rb.posX;
        this.posY  = rb.posY;
    }

    public void setPos(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public Color getColor() {
        return color;
    }

    public String getUrl() {
        return url;
    }

    // Composant "JFX" associé
    public void setGui(ImageView gui) { this.gui = gui; }
    public Node getGui() { return gui; }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
}

