package demineur;

public class Case {
    // 0 is hidden, 1 is revealed, 2 is flagged
    private int revealed=0;
    private int valeur=0;
    private int row=0;
    private int column=0;

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
    
    // below are abandoned methods
    public Case n(Case[][] board) {
        return board[row-1][column];
    }

    public Case  no(Case[][] board) {
        return board[row-1][column-1];
    }

    public Case  e(Case[][] board) {
        return board[row][column-1];
    }

    public Case  se(Case[][] board) {
        return board[row+1][column-1];
    }

    public Case  s(Case[][] board) {
        return board[row+1][column];
    }

    public Case  sw(Case[][] board) {
        return board[row+1][column+1];
    }

    public Case  w(Case[][] board) {
        return board[row][column+1];
    }

    public Case  nw(Case[][] board) {

        return board[row-1][column+1];
    }
    //end of the abandoned methods

    public static void afficher() {
        System.out.println("");
    }

    public int getRevealed() {
        return revealed;
    }


    public void setRevealed(int revealed, Case[][] board, int size) {
    	//When water (value 0) is played, it should contaminate all adjacent waters to be revealed as well. 
    	//Can't solve this glitch. 
        if (this.revealed==0) {
            if (this.valeur==0) {
                for (int i=-1; i<=1; i++) {
                    for (int j=-1; j<=1; j++) {
                        if (row+i>=0 && row+i<size && column+j>=0 && column+j<size && !(i==0 && j==0))
                            System.out.println("test 5");
                        board[this.row+i][this.column+j].setRevealed(1, board, size);
                    }
                }

            }
        }
        this.revealed = revealed;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }




}
