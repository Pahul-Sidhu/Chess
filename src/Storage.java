import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
  HashMap<Integer, ArrayList<chessPiece>> board;
  boolean turn;

  Storage(HashMap<Integer, ArrayList<chessPiece>> board, boolean turn){
    this.board = new HashMap<>(board);
    this.turn = turn;
  }

  public boolean equals(Object o){
    if(o instanceof Storage key){
      return key.board.equals(this.board) && this.turn == key.turn;
    }
    return false;

  }
}
