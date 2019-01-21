/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbuhacks;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author webst
 */
public class Obstacle implements Collidables{
    boolean collidable=true;
    private boolean currentlyIntersecting=false;
    private int x;
    private int y;
    Rectangle s=new Rectangle(25, (int)(Math.random()*151) + 125, Color.BLACK);
    public Obstacle(int x, int y){
        this.x=x;
        this.y=y;
        s.setX(x);
        s.setY(y);
    }
    
    public Rectangle getShape(){
        return s;
    }

    @Override
    public boolean getCollidable() {
        return collidable;
    }
    
    public void swapCollide(){
        collidable=!collidable;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
        s.setX(x);
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
        s.setY(y);
    }

    /**
     * @return the currentlyIntersecting
     */
    public boolean isCurrentlyIntersecting() {
        return currentlyIntersecting;
    }

    /**
     * @param currentlyIntersecting the currentlyIntersecting to set
     */
    public void setCurrentlyIntersecting(boolean currentlyIntersecting) {
        this.currentlyIntersecting = currentlyIntersecting;
    }
    
}
