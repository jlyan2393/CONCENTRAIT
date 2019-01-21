/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbuhacks;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author webst
 */
public interface Collidables extends MainCollidables{
    public Rectangle getShape();
    public void swapCollide();
    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
    public boolean isCurrentlyIntersecting();
    public void setCurrentlyIntersecting(boolean currentlyIntersecting);
}
