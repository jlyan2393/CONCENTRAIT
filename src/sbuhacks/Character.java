/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbuhacks;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import static sbuhacks.SBUHacks.MAX_TIME;

/**
 *
 * @author webst
 */
public class Character implements DirectionConstants, MainCollidables{
    public boolean moving=false;
    private ImageView img=new ImageView();
    boolean collidable=true;
    int x;
    int y;
    Rectangle s=new Rectangle(75,75, Color.TRANSPARENT);
    public Character(int x, int y, ImageView img){
        this.x=x;
        this.y=y;
        s.setX(x);
        s.setY(y);
        this.img=img;
        img.setX(x);
        img.setY(y);
    }
    public Shape getShape(){
        return s;
    }
    public void move(int direction){
        switch(direction){
            case UP: 
                for(int i=0;i<20;i++){
                        s.setY(y-=1);
                        getImg().setY(y-=1);
                        if(y<=0){
                           y=5;
                        }
                }
                break;
            case DOWN: 
                for(int i=0;i<20;i++){
                        s.setY(y+=1);
                        getImg().setY(y+=1);
                        if(y>=825){
                            y=835;
                        }
                }
                break;
        }
    }

    @Override
    public boolean getCollidable() {
        return collidable;
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
     * @return the img
     */
    public ImageView getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(ImageView img) {
        this.img.setImage(img.getImage());
        this.img.setX(x);
        this.img.setY(y);
    }
}
