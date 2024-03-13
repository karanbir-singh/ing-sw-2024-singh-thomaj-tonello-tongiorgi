package it.polimi.ingsw.gc26.model;
import java.util.*;
public class Point {
    private final int x,y;
    private Side side;
    private int flag;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.flag = 0;
        this.side = null;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setSide(Side side) throws NullPointerException{
        this.side = side;
    }

}
