import processing.core.PImage;

import java.awt.*;

public class chessPiece {
  private float x = 0;
  private float y = 0;
  private float width = 0;
  private float height = 0;
  private String name;
  private PImage img;
  private int value = 0;
  private Color color;
  private Color default_col;
  private boolean isClicked;

  public chessPiece(float x, float y, float width, float height, Color color, PImage img, String name, int value){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.img = img;
    this.name = name;
    this.value = value;
    this.color = color;
    this.default_col = color;
  }

  public void draw(Driver driver){
    driver.stroke(0);
    driver.fill(color.getRGB());
    driver.rect(this.x, this.y, this.width, this.height);
    if(this.img != null) driver.image(img, x, y, width, height);
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public float getHeight() {
    return height;
  }

  public float getWidth() {
    return width;
  }

  public String getName(){return this.name;}

  public PImage getImg(){return this.img;}

  public int getValue(){return this.value;}

  public Color getColor(){return this.color;}

  public boolean getIsClicked(){return this.isClicked;}

  public void setColor(Color color){
    this.color = this.isClicked ? this.color : color;
  }

  public void setDefault(){
    this.color = this.isClicked ? this.color : this.default_col;
  }

  public void setIsClicked(){
    this.isClicked = !this.isClicked;
  }

  public void setCreds(PImage img, int value, String name){
    this.img = img;
    this.value = value;
    this.name = name;
  }
}
