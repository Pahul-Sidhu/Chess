import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Effects {
  private boolean isClicked = false;
  Movements movements = new Movements();

  public boolean displayEffects(Driver driver, HashMap<Integer, ArrayList<chessPiece>> pieces) {
      for (int i = 1; i <= pieces.size(); i++) {
        ArrayList<chessPiece> square_pieces = pieces.get(i);
        // Iterating over each slot
        for (int j = 0; j < square_pieces.size(); j++) {
          chessPiece square = square_pieces.get(j);
          if (circleRect(driver.mouseX, driver.mouseY, square.getWidth() / 100, square.getX(), square.getY(), square.getWidth(), square.getHeight())) {

            // Only hovering over possible movements and pieces controlled by player
            if (!checkIfClicked(pieces)) {
              if (pieces.get(i).get(j) != null) {
                if (pieces.get(i).get(j).getName().startsWith("dark")) {
                  Color color = new Color(87, 32, 32);
                  square.setColor(color);
                }
              }
            } else {
              // Allowing hover only over possible movements
              analyzeSlots(driver, movements.showViableMovement(pieces), pieces, square, i, j, "hover");
            }

            // Handling the click events
            if (isClicked) {
            /*
            If the mouse is clicked and a slot is already locked, it is unlocked.
            Otherwise, the slot on which mouse is hovering is locked
             */
              if (!checkIfClicked(pieces) && pieces.get(i).get(j) != null && pieces.get(i).get(j).getName().startsWith("dark")) {
                square.setIsClicked();
              } else {
                boolean decision = analyzeSlots(driver, movements.showViableMovement(pieces), pieces, square, i, j, "swap");
                removeLock(pieces);
                isClicked = false;
                return decision;
              }
            }
            isClicked = false;
          } else {
            square.setDefault();
          }
        }
      }
      return false;
  }

  private boolean circleRect(float px, float py, float radius, float rx, float ry, float width, float height) {
    //Setting variables for testing
    float tempx = px;
    float tempy = py;

    //Seeing which edge is closest
    if (px < rx) {
      tempx = rx; //left edge
    } else if (px > (rx + width)) {
      tempx = rx + width; //right edge
    }

    if (py < ry) {
      tempy = ry; //top edge
    } else if (py > (ry + height)) {
      tempy = ry + height; //bottom edge
    }

    //Getting dist from closest edges
    float distX = px - tempx;
    float distY = py - tempy;
    float distance = (float) Math.sqrt((distX * distX) + (distY * distY));

    //if distance is less than radius, collision!!!
    return distance <= radius;
  }

  public void setIsClicked() {
    this.isClicked = !this.isClicked;
  }

  private boolean checkIfClicked(HashMap<Integer, ArrayList<chessPiece>> positions) {
    for (int i = 1; i <= positions.size(); i++) {
      ArrayList<chessPiece> square_piece = positions.get(i);
      // Iterating over each slot to check if the slot is locked
      for (chessPiece square : square_piece) {
        if (square.getIsClicked()) return true;
      }
    }
    return false;
  }

  private void removeLock(HashMap<Integer, ArrayList<chessPiece>> positions) {
    for (int i = 1; i <= positions.size(); i++) {
      ArrayList<chessPiece> square_piece = positions.get(i);
      // Iterating over each slot to check if the slot is locked
      for (chessPiece square : square_piece) {
        if (square.getIsClicked()) square.setIsClicked();
      }
    }
  }

  private boolean analyzeSlots(Driver driver, ArrayList<Integer> possible_slots, HashMap<Integer, ArrayList<chessPiece>> pieces, chessPiece square, int i, int j, String msg) {
    switch (msg) {
      case "hover":
        if (possible_slots != null) {
          for (int x = 0; x < possible_slots.size() - 1; x++) {
            if (possible_slots.get(x) == i && x % 2 == 0) {
              if (possible_slots.get(x + 1) == j) {
                Color color = new Color(87, 32, 32);
                square.setColor(color);
                break;
              }
            }
          }
          return false;
        }

        break;
      case "swap":
        if (possible_slots != null) {
          for (int x = 0; x < possible_slots.size() - 1; x++) {
            if (possible_slots.get(x) == i && x % 2 == 0) {
              if (possible_slots.get(x + 1) == j) {
                int[] getClicked = movements.getClicked(pieces);
                chessPiece cp = pieces.get(getClicked[0]).get(getClicked[1]);
                if(pieces.get(i).get(j).getName().endsWith("King")) {
                  driver.setOver();
                  driver.result = 1;
                }

                if(cp.getName().endsWith("Pawn") && i == 8){
                  pieces.get(i).get(j).setCreds(driver.loadImage("F:\\chess\\pieces\\dark queen.png"), 9, "darkQueen");
                }else{
                  pieces.get(i).get(j).setCreds(cp.getImg(), cp.getValue(), cp.getName());
                }

                pieces.get(getClicked[0]).get(getClicked[1]).setCreds(null, 0, "none");
                return true;
              }
            }
          }
        }
        break;
    }
    return false;
  }

}
