public class Move {
   int current_x;
   int current_y;
   int dest_x;
   int dest_y;
   chessPiece piece = null;

  public Move(int x, int y, int x1, int y1){
    this.current_x = x;
    this.current_y = y;
    this.dest_x = x1;
    this.dest_y = y1;
  }

  public Move reverse(){
    Move move = new Move(dest_x, dest_y, current_x, current_y);
    move.setPiece(this.piece);
    return move;
  }

  public void setPiece(chessPiece cp){
    this.piece = new chessPiece(cp.getX(), cp.getY(), cp.getWidth(), cp.getHeight(), cp.getColor(), cp.getImg(), cp.getName(), cp.getValue());
  }

}
