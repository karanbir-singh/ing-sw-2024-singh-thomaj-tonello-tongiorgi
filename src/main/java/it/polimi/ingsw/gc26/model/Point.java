package it.polimi.ingsw.gc26.model;
import java.util.*;
public class Point {
    private final int x,y;
    private Side side;
    private final Map<Integer,Boolean> flags;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.side = null;
        flags = new HashMap<Integer,Boolean>();
        for(int i= 0; i < 16; i++){
            flags.put(i,false);
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean getFlag(int flag){
        return flags.get(flag);
    }
    public void setFlag(int flag, boolean value){
        flags.put(flag,value);
    }
    public void setSide(Side side) throws NullPointerException{
        this.side = side;
    }

}
