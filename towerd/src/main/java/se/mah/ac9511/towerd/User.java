package se.mah.ac9511.towerd;

import android.graphics.Color;

/**
 * Created by David on 2015-10-21.
 */
public class User  implements Comparable<User> {
    private String id;
    private int xPos;
    private int yPos;
    private double xRel=0;
    private double yRel=0;
    private int c = Color.BLUE;


    public User(String id, int xPos, int yPos) {
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getxPos() {
        return xPos;
    }
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    public int getyPos() {
        return yPos;
    }
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public double getyRel() {
        return yRel;
    }
    public void setyRel(double yRel) {
        this.yRel = yRel;
    }
    public double getxRel() {
        return xRel;
    }
    public void setxRel(double xRel) {
        this.xRel = xRel;
    }

    @Override
    public int compareTo(User o) {
        return id.compareTo(o.getId());
    }
    public int getColor() {
        return c;
    }
    public void setColor(int c) {
        this.c = c;
    }

}
