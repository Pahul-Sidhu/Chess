import java.util.ArrayList;
import java.util.HashMap;

public class Movements {
  public ArrayList<Integer> showViableMovement(HashMap<Integer, ArrayList<chessPiece>> pieces) {
      int[] indices = getClicked(pieces);

      if (indices != null) {
        chessPiece piece = pieces.get(indices[0]).get(indices[1]);

        if (piece != null) {
          switch (piece.getName()) {
            case "darkPawn" -> {
              return PawnMovement(indices[0], indices[1], pieces);
            }
            case "darkKnight" -> {
              return KnightMovement(indices[0], indices[1], pieces, "light");
            }
            case "darkRook" -> {
              return RookMovement(indices[0], indices[1], pieces, "light");
            }
            case "darkBishop" -> {
              return BishopMovement(indices[0], indices[1], pieces, "light");
            }
            case "darkQueen" -> {
              return QueenMovement(indices[0], indices[1], pieces, "light");
            }
            case "darkKing" -> {
              return KingMovement(indices[0], indices[1], pieces, "light");
            }
          }
        }
      }
    return null;
  }

  public int[] getClicked(HashMap<Integer, ArrayList<chessPiece>> positions) {
    int[] indices = new int[2];
    for (int i = 1; i <= positions.size(); i++) {
      ArrayList<chessPiece> square_pieces = positions.get(i);
      // Iterating over each slot to check if the slot is locked
      for (int j = 0; j < square_pieces.size(); j++) {
        if (square_pieces.get(j).getIsClicked()) {
          indices[0] = i;
          indices[1] = j;
          return indices;
        }
        ;
      }
    }
    return null;
  }

  public ArrayList<Integer> PawnMovement(int i, int j, HashMap<Integer, ArrayList<chessPiece>> pieces) {
    ArrayList<Integer> possible_slots = new ArrayList<>();

    if(i + 1 <= 8 && pieces.get(i + 1).get(j).getImg() == null) {
      possible_slots.add(i + 1);
      possible_slots.add(j);
    }
    if(i + 1 <= 8 && j + 1 < 8 && pieces.get(i + 1).get(j + 1).getName().startsWith("light")){
      possible_slots.add(i + 1);
      possible_slots.add(j + 1);
    }
    if(i + 1 <= 8 && j - 1 >= 0 && pieces.get(i + 1).get(j - 1).getName().startsWith("light")){
      possible_slots.add(i + 1);
      possible_slots.add(j - 1);
    }

    if(i == 2 && pieces.get(i + 2).get(j).getImg() == null){
      possible_slots.add(i + 2);
      possible_slots.add(j);
    }

    return possible_slots;

  }

  public ArrayList<Integer> KnightMovement(int i, int j, HashMap<Integer, ArrayList<chessPiece>> pieces, String opponent) {
    ArrayList<Integer> possible_slots = new ArrayList<>();
    int[] arr = {i + 2, j + 1, i - 2, j + 1, i + 2, j - 1, i - 2, j - 1, i + 1, j + 2, i + 1, j - 2, i - 1, j + 2, i - 1, j - 2};
    int x = 0;
    int y = 1;

    while(y < arr.length){
      if((arr[x] >= 1 && arr[x] <= 8 && arr[y] >= 0 && arr[y] < 8) && (pieces.get(arr[x]).get(arr[y]).getImg() == null || pieces.get(arr[x]).get(arr[y]).getName().startsWith(opponent))) {
        possible_slots.add(arr[x]);
        possible_slots.add(arr[y]);
      }
      x += 2;
      y += 2;
    }
    return possible_slots;
  }

  public ArrayList<Integer> RookMovement(int i, int j, HashMap<Integer, ArrayList<chessPiece>> pieces, String opponent) {
    ArrayList<Integer> possible_slots = new ArrayList<>();

    int row = i - 1;
    int col = j - 1;

    while(col >= 0 && (pieces.get(i).get(col).getImg() == null || pieces.get(i).get(col).getName().startsWith(opponent))){
      possible_slots.add(i);
      possible_slots.add(col);
      if(pieces.get(i).get(col).getImg() != null && pieces.get(i).get(col).getName().startsWith(opponent)) break;
      col--;
    }
    col = j + 1;
    while(col < 8 && (pieces.get(i).get(col).getImg() == null || pieces.get(i).get(col).getName().startsWith(opponent))){
      possible_slots.add(i);
      possible_slots.add(col);
      if(pieces.get(i).get(col).getImg() != null && pieces.get(i).get(col).getName().startsWith(opponent)) break;
      col++;
    }
    while(row > 0 && (pieces.get(row).get(j).getImg() == null || pieces.get(row).get(j).getName().startsWith(opponent))){
      possible_slots.add(row);
      possible_slots.add(j);
      if(pieces.get(row).get(j).getImg() != null && pieces.get(row).get(j).getName().startsWith(opponent)) break;
      row--;
    }
    row = i + 1;
    while(row < 9 && (pieces.get(row).get(j).getImg() == null || pieces.get(row).get(j).getName().startsWith(opponent))){
      possible_slots.add(row);
      possible_slots.add(j);
      if(pieces.get(row).get(j).getImg() != null && pieces.get(row).get(j).getName().startsWith(opponent)) break;
      row++;
    }
    return possible_slots;
  }

  public ArrayList<Integer> BishopMovement(int i, int j, HashMap<Integer, ArrayList<chessPiece>> pieces, String opponent){
    ArrayList<Integer> possible_slots = new ArrayList<>();

    int row = i + 1;
    int col = j + 1;

    while(row < 9 && col < 8 && (pieces.get(row).get(col).getImg() == null || pieces.get(row).get(col).getName().startsWith(opponent))){
      possible_slots.add(row);
      possible_slots.add(col);
      if(pieces.get(row).get(col).getImg() != null && pieces.get(row).get(col).getName().startsWith(opponent)) break;
      row++;
      col++;
    }

    row = i - 1;
    col = j - 1;
    while(row > 0 && col >= 0 && (pieces.get(row).get(col).getImg() == null || pieces.get(row).get(col).getName().startsWith(opponent))){
      possible_slots.add(row);
      possible_slots.add(col);
      if(pieces.get(row).get(col).getImg() != null && pieces.get(row).get(col).getName().startsWith(opponent)) break;
      row--;
      col--;
    }

    row = i + 1;
    col = j - 1;
    while(row < 9 && col >= 0 && (pieces.get(row).get(col).getImg() == null || pieces.get(row).get(col).getName().startsWith(opponent))){
      possible_slots.add(row);
      possible_slots.add(col);
      if(pieces.get(row).get(col).getImg() != null && pieces.get(row).get(col).getName().startsWith(opponent)) break;
      row++;
      col--;
    }

    row = i - 1;
    col = j + 1;
    while(row > 0 && col < 8 && (pieces.get(row).get(col).getImg() == null || pieces.get(row).get(col).getName().startsWith(opponent))){
      possible_slots.add(row);
      possible_slots.add(col);
      if(pieces.get(row).get(col).getImg() != null && pieces.get(row).get(col).getName().startsWith(opponent)) break;
      row--;
      col++;
    }

    return possible_slots;
  }

  public ArrayList<Integer> QueenMovement(int i, int j, HashMap<Integer, ArrayList<chessPiece>> pieces, String opponent){
    ArrayList<Integer> possible_slots = new ArrayList<>(RookMovement(i, j, pieces, opponent));

    possible_slots.addAll(BishopMovement(i, j, pieces, opponent));
    return possible_slots;
  }

  public ArrayList<Integer> KingMovement(int i, int j, HashMap<Integer, ArrayList<chessPiece>> pieces, String opponent) {
    ArrayList<java.lang.Integer> possible_slots = new ArrayList<>();
    int[] arr = {i, j + 1, i, j - 1, i + 1, j, i - 1, j, i + 1, j + 1, i + 1, j - 1, i - 1, j + 1, i - 1, j - 1};
    int x = 0;
    int y = 1;

    while(y < arr.length){
      if((arr[x] >= 1 && arr[x] <= 8 && arr[y] >= 0 && arr[y] < 8) && (pieces.get(arr[x]).get(arr[y]).getImg() == null || pieces.get(arr[x]).get(arr[y]).getName().startsWith(opponent))){
        possible_slots.add(arr[x]);
        possible_slots.add(arr[y]);
      }
      x += 2;
      y += 2;
    }
    return possible_slots;
  }

  public ArrayList<Integer> Generate_Pawn(int i, int j, HashMap<Integer, ArrayList<chessPiece>> pieces){
    ArrayList<Integer> possible_slots = new ArrayList<>();
    if(i - 1 >= 1 && pieces.get(i - 1).get(j).getImg() == null) {
      possible_slots.add(i - 1);
      possible_slots.add(j);
    }
    if(i - 1 >= 1 && j - 1 >= 0 && pieces.get(i - 1).get(j - 1).getImg() != null && pieces.get(i - 1).get(j - 1).getName().startsWith("dark")){
      possible_slots.add(i - 1);
      possible_slots.add(j - 1);
    }
    if(i - 1 >= 1 && j + 1 < 8 && pieces.get(i - 1).get(j + 1).getImg() != null && pieces.get(i - 1).get(j + 1).getName().startsWith("dark")){
      possible_slots.add(i - 1);
      possible_slots.add(j + 1);
    }

    if(i == 7 && pieces.get(i - 2).get(j).getImg() == null){
      possible_slots.add(i - 2);
      possible_slots.add(j);
    }

    return possible_slots;
  }

}
