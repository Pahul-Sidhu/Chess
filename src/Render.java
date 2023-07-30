import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Render {
  public void RenderBoard(Driver driver) {
    RenderSlots(driver, driver.getPieces());
  }

  private void RenderSlots(Driver driver, HashMap<Integer, ArrayList<chessPiece>> positions) {
    // Making square slots on the board
    float x = 0;
    float y = 1;
    float width = 92;
    float height = 90;
    float col;

    for (int i = 8; i >= 1; i--) {
      col = i % 2 == 0 ? 0 : 1;
      ArrayList<chessPiece> arr = new ArrayList<>();
      for (int j = 0; j <= 7; j++) {
        chessPiece slot;
        Color color;
        PImage img;

        if (col == 0) {
          color = new Color(234, 131, 131);
        } else {
          color = new Color(126, 32, 32);
        }

        if (i == 7) {
          img = driver.loadImage("D:\\chess\\pieces\\light pawn.png");
          slot = new chessPiece(x, y, width, height, color, img, "lightPawn", 1);
        } else if (i == 2) {
          img = driver.loadImage("D:\\chess\\pieces\\dark pawn.png");
          slot = new chessPiece(x, y, width, height, color, img, "darkPawn", 1);
        } else if (i == 8) {
          if (j == 0 || j == 7) {
            img = driver.loadImage("D:\\chess\\pieces\\light rook.png");
            slot = new chessPiece(x, y, width, height, color, img, "lightRook", 5);
          } else if (j == 1 || j == 6) {
            img = driver.loadImage("D:\\chess\\pieces\\light knight.png");
            slot = new chessPiece(x, y, width, height, color, img, "lightKnight", 3);
          } else if (j == 2 || j == 5) {
            img = driver.loadImage("D:\\chess\\pieces\\light bishop.png");
            slot = new chessPiece(x, y, width, height, color, img, "lightBishop", 3);
          } else if (j == 3) {
            img = driver.loadImage("D:\\chess\\pieces\\light queen.png");
            slot = new chessPiece(x, y, width, height, color, img, "lightQueen", 9);
          } else {
            img = driver.loadImage("D:\\chess\\pieces\\light king.png");
            slot = new chessPiece(x, y, width, height, color, img, "lightKing", 1000);
          }
        } else if (i == 1) {
          if (j == 0 || j == 7) {
            img = driver.loadImage("D:\\chess\\pieces\\dark rook.png");
            slot = new chessPiece(x, y, width, height, color, img, "darkRook", 5);
          } else if (j == 1 || j == 6) {
            img = driver.loadImage("D:\\chess\\pieces\\dark knight.png");
            slot = new chessPiece(x, y, width, height, color, img, "darkKnight", 3);
          } else if (j == 2 || j == 5) {
            img = driver.loadImage("D:\\chess\\pieces\\dark bishop.png");
            slot = new chessPiece(x, y, width, height, color, img, "darkBishop", 3);
          } else if (j == 3) {
            img = driver.loadImage("D:\\chess\\pieces\\dark queen.png");
            slot = new chessPiece(x, y, width, height, color, img, "darkQueen", 9);
          } else {
            img = driver.loadImage("D:\\chess\\pieces\\dark king.png");
            slot = new chessPiece(x, y, width, height, color, img, "darkKing", 1000);
          }
        } else {
          slot = new chessPiece(x, y, width, height, color, null, "none", 0);
        }

        x += width;
        arr.add(slot);
        col = col == 0 ? 1 : 0;
      }
      positions.put(i, arr);
      x = 0;
      y += height;
    }
  }

  public void draw(Driver driver, HashMap<Integer, ArrayList<chessPiece>> pieces) {
    // Rendering chess pieces
    for (int i = 1; i <= 8; i++) {
      for (chessPiece cp : pieces.get(i)
      ) {
        cp.draw(driver);
      }
    }
  }
}
