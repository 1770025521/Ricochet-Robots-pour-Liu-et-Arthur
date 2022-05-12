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

    public Robot(Color color){
        this.color = color;
        this.url = color.name() + "_robot.png";
        Random rand = new Random();
        posX = posY = 8;
        while (!((posX != 8 && posX != 7) || (posY != 7 && posY != 8))){
            setPos( rand.nextInt(GameBoard.SIZE), rand.nextInt(GameBoard.SIZE) );
            //System.out.println(posX + " " + posY);
        }

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

