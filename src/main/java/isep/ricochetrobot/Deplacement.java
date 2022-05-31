package isep.ricochetrobot;

public class Deplacement{
    private final Color color;
    private final Cell.Direction direction;
    public Deplacement(Color color, Cell.Direction direction){
        this.color = color;
        this.direction = direction;
    }
    @Override
    public String toString(){
        return this.color + " " + this.direction ;
    }
    public Color getColor() {
        return color;
    }
    public Cell.Direction getDirection() {
        return direction;
    }
}