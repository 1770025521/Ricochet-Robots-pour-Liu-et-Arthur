package isep.ricochetrobot;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Classe qui represente les Robots du plateau
 */
public class Robot {

    private Color color;
    private int posX;
    private int posY;
    private boolean[] block;
    private String url;
    Node gui;

    /**
     * Constructeur du robot
     * @param color la couleur du robot
     * @param posX la position x du robot
     * @param posY la position y du robot
     */
    public Robot(Color color, int posX, int posY){
        this.color = color;
        this.url = color.name() + "_robot.png";
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Constructeur pour copie
     * @param rb le robot à copier
     */
    public Robot(Robot rb){
        this.color = rb.color;
        this.posX  = rb.posX;
        this.posY  = rb.posY;
    }

    /**
     * Modifie la position du robot
     * @param x la position x du robot
     * @param y la position y du robot
     */
    public void setPos(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    /**
     * Recupere la couleur du robot
     * @return la couleur du robot
     */
    public Color getColor() {
        return color;
    }
    /**
     * Recupere l'url de l'image du robot
     * @return l'url du robot
     */
    public String getUrl() {
        return url;
    }

    /**
     * Modifie le gui du robot
     * @param gui le nouveau gui du robot
     */
    public void setGui(ImageView gui) { this.gui = gui; }

    /**
     * Recupere le gui du robot
     * @return le gui du robot
     */
    public Node getGui() { return gui; }

    /**
     * Recupere la position x du robot
     * @return la position x du robot
     */
    public int getPosX() {
        return posX;
    }
    /**
     * Recupere la position y du robot
     * @return la position y du robot
     */
    public int getPosY() {
        return posY;
    }
}

