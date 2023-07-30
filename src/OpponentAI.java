import java.util.ArrayList;
import java.util.HashMap;

public class OpponentAI {
  Movements movements = new Movements();

  public boolean move(Driver driver) {
    if(enemyNear(driver)) return true;

    if(Victory(driver)) {
      driver.setOver();
      return true;
    }

    int[] move = max_value(driver, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, new HashMap<>());

    if(driver.getPieces().get(move[3]).get(move[4]).getName().endsWith("King")) driver.result = 0;

    makeMove(driver, new Move(move[1], move[2], move[3], move[4]), "move", true);

    if(driver.checkOver()){
      driver.setOver();
    }

    return true;
  }

  private int[] max_value(Driver board, int depth, int alpha, int beta, HashMap<Storage, int[]> saver) {
    if (depth == 0 || board.checkOver()) {
      int score = evaluate(board.getPieces(), false);
      return new int[]{score, 1};
    }

    int[] res = saver.get(new Storage(board.getPieces(), true));
    if(res != null) return res;

    int value = Integer.MIN_VALUE;
    int[] best_move = null;

    ArrayList<Move> moves = getMoves(board.getPieces(), true);

    int Alpha = alpha;

    for (Move move : moves) {

      makeMove(board, move, "move", false);

      int val = min_value(board, depth - 1, Alpha, beta, saver)[0];

      if (val > value) {
        value = val;
        best_move = new int[]{move.current_x, move.current_y, move.dest_x, move.dest_y};
      }

      if (val >= beta) {
        saver.put(new Storage(board.getPieces(), true), new int[]{value, best_move[0], best_move[1], best_move[2], best_move[3]});
        makeMove(board, move.reverse(), "rev", false);
        return new int[]{value, best_move[0], best_move[1], best_move[2], best_move[3]};
      }

      makeMove(board, move.reverse(), "rev", false);

      if (val > Alpha) Alpha = val;
    }

    saver.put(new Storage(board.getPieces(), true), new int[]{value, best_move[0], best_move[1], best_move[2], best_move[3]});
    return best_move == null ? new int[]{value, 0} : new int[]{value, best_move[0], best_move[1], best_move[2], best_move[3]};

  }

  private int[] min_value(Driver board, int depth, int alpha, int beta, HashMap<Storage, int[]> saver) {
    if (depth == 0 || board.checkOver()) {
      int score = evaluate(board.getPieces(), true);
      return new int[]{score, 0};
    }

    int[] res = saver.get(new Storage(board.getPieces(), false));
    if(res != null) return res;

    int value = Integer.MAX_VALUE;
    int[] best_move = null;
    int Beta = beta;

    ArrayList<Move> moves = getMoves(board.getPieces(), false);

    for (Move move : moves) {
      makeMove(board, move, "move", false);

      int val = max_value(board, depth - 1, alpha, Beta, saver)[0];

      if (val < value) {
        value = val;
        best_move = new int[]{move.current_x, move.current_y, move.dest_x, move.dest_y};
      }

      if (val <= alpha) {
        saver.put(new Storage(board.getPieces(), false), new int[]{value, best_move[0], best_move[1], best_move[2], best_move[3], 0});
        makeMove(board, move.reverse(), "rev", false);
        return new int[]{value, best_move[0], best_move[1], best_move[2], best_move[3]};
      }

      makeMove(board, move.reverse(), "rev", false);
      if (val < Beta) Beta = val;
    }

    saver.put(new Storage(board.getPieces(), false), new int[]{value, best_move[0], best_move[1], best_move[2], best_move[3], 0});
    return best_move == null ? new int[]{value, 0} : new int[]{value, best_move[0], best_move[1], best_move[2], best_move[3]};
  }

  private ArrayList<Move> getMoves(HashMap<Integer, ArrayList<chessPiece>> pieces, boolean decision) {
    String player;
    String opp;
    if (decision) {
      player = "light";
      opp = "dark";
    } else {
      player = "dark";
      opp = "light";
    }

    ArrayList<Move> all_moves = new ArrayList<>();

    for (int index : pieces.keySet()) {
      ArrayList<chessPiece> piece = pieces.get(index);
      for (int pos = 0; pos < piece.size(); pos++) {
        if (piece.get(pos).getName().startsWith(player)) {
          ArrayList<Integer> moves = getEachMove(pieces, index, pos, opp);
          int x = 0;
          int y = x + 1;
          while (y < moves.size() && x < moves.size() - 1) {
            Move move = new Move(index, pos, moves.get(x), moves.get(y));
            move.setPiece(pieces.get(moves.get(x)).get(moves.get(y)));
            all_moves.add(move);
            x += 2;
            y += 2;
          }
        }
      }
    }
    return all_moves;
  }

  private ArrayList<Integer> getEachMove(HashMap<Integer, ArrayList<chessPiece>> pieces, int i, int j, String opponent) {
    if (pieces.get(i).get(j).getName().endsWith("Pawn")) {
      if (opponent.equals("light")) {
        return movements.PawnMovement(i, j, pieces);
      }
      return movements.Generate_Pawn(i, j, pieces);
    } else if (pieces.get(i).get(j).getName().endsWith("Knight"))
      return movements.KnightMovement(i, j, pieces, opponent);
    else if (pieces.get(i).get(j).getName().endsWith("Rook")) return movements.RookMovement(i, j, pieces, opponent);
    else if (pieces.get(i).get(j).getName().endsWith("Bishop")) return movements.BishopMovement(i, j, pieces, opponent);
    else if (pieces.get(i).get(j).getName().endsWith("Queen")) return movements.QueenMovement(i, j, pieces, opponent);
    else return movements.KingMovement(i, j, pieces, opponent);
  }

  private void makeMove(Driver driver, Move move, String msg, boolean change) {
    HashMap<Integer, ArrayList<chessPiece>> pieces  = driver.getPieces();
    int i = move.current_x;
    int j = move.current_y;
    int destx = move.dest_x;
    int desty = move.dest_y;

    if (i >= 1 && i <= 8 && j >= 0 && j < 8 && destx >= 1 && destx <= 8 && desty >= 0 && desty < 8) {
      chessPiece cp = pieces.get(i).get(j);
      if (msg.equals("move")) {
        if(change && cp.getName().endsWith("Pawn") && destx == 1){
          pieces.get(destx).get(desty).setCreds(driver.loadImage("F:\\chess\\pieces\\light queen.png"), 9, "lightQueen");
        }else{
          pieces.get(destx).get(desty).setCreds(cp.getImg(), cp.getValue(), cp.getName());
        }
        pieces.get(i).get(j).setCreds(null, 0, "none");
      } else {
        pieces.get(destx).get(desty).setCreds(cp.getImg(), cp.getValue(), cp.getName());
        pieces.get(i).get(j).setCreds(move.piece.getImg(), move.piece.getValue(), move.piece.getName());
      }
    }
  }

  private int evaluate(HashMap<Integer, ArrayList<chessPiece>> pieces, boolean decision) {
    String player;
    if (decision) player = "light";
    else player = "dark";
    int score = 0;
    for (int i = 1; i <= 8; i++) {
      ArrayList<chessPiece> arr = pieces.get(i);
      for (int j = 0; j < 8; j++) {
        if (arr.get(j) != null && arr.get(j).getName().startsWith(player)) {
          score += arr.get(j).getValue();
        }
      }
    }
    return score;
  }

  private boolean enemyNear(Driver driver){
    ArrayList<Move> moves = getMoves(driver.getPieces(), false);
    for(int i = 8; i >= 1; i--){
      for(int j = 0; j < 8; j++){
        if(driver.getPieces().get(i).get(j).getName().equals("lightKing")){
          for(Move move : moves){
            if(move.dest_x == i && move.dest_y == j){
              ArrayList<Integer> each_move = getEachMove(driver.getPieces(), i, j, "dark");
              int x = 0;
              int y = 1;

              for(int k = 0; k < each_move.size() - 1; k++){
                if(each_move.get(k) == move.current_x && each_move.get(k + 1) == move.current_y){
                  makeMove(driver, new Move(i, j, each_move.get(k), each_move.get(k + 1)), "move", false);
                  return true;
                }
              }

              while(y < each_move.size()){
                if(driver.getPieces().get(each_move.get(x)).get(each_move.get(y)).getValue() == 0){
                  makeMove(driver, new Move(i, j, each_move.get(x), each_move.get(y)), "move", false);
                  return true;
                }
              }
            }
          }
        };
      }
    }
    return  false;

  }

  private boolean Victory(Driver driver){
    ArrayList<Move> moves = getMoves(driver.getPieces(), true);
    for(int i = 8; i >= 1; i--){
      for(int j = 0; j < 8; j++){
        if(driver.getPieces().get(i).get(j).getName().equals("darkKing")){
          for(Move move : moves){
            if(move.dest_x == i && move.dest_y == j){
              makeMove(driver, move, "move", true);
              return true;
            }
          }
        };
      }
    }
    return false;
  }

}
