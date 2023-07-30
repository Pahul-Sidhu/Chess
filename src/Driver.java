import processing.core.PApplet;
import java.util.ArrayList;
import java.util.HashMap;

public class Driver extends PApplet {
  private HashMap<Integer, ArrayList<chessPiece>> pieces;
  private Render render;
  private Effects effects;
  private OpponentAI ai;
  private int turn = 0;
  private boolean isOver = false;
  public int result = -3;

  public void setup(){
    render = new Render();
    pieces = new HashMap<>();
    effects = new Effects();
    render.RenderBoard(this);
    ai = new OpponentAI();
  }

  public void draw(){
    stroke(0);
    background(0);
    if(!this.isOver) {
      render.draw(this, pieces);
      if (turn == 0) {
        boolean decision = effects.displayEffects(this, pieces);
        if (decision) turn = 1;
      } else {
        boolean decision = ai.move(this);
        if (decision) turn = 0;
      }
    }else{
      if(result == 1){
        textSize(90);
        fill(126, 32, 32);
        text("You Won !!!", 160, 320);
      }else{
        textSize(90);
        fill(126, 32, 32);
        text("You Lost !!!", 160, 320);
      }
    }

  }

  @Override
  public void mouseClicked() {
    super.mouseClicked();
    effects.setIsClicked();
  }

  public HashMap<Integer, ArrayList<chessPiece>> getPieces(){return this.pieces;}

  public boolean checkOver(){
    int count = 0;
    for(int i = 1; i <= 8; i++){
      for(int j = 0; j < 8; j++){
        if(pieces.get(i).get(j).getName().equals("lightKing")) count++;
        else if(pieces.get(i).get(j).getName().equals("darkKing")) count++;
      }
    }
    return count != 2;
  }

  public void setOver(){this.isOver = true;}

  public void settings() {
    size(737, 721);
  }

  public static void main(String[] args) {
    String[] appletArgs = new String[]{"game"};
    Driver window = new Driver();
    PApplet.runSketch(appletArgs, window);
  }
}
