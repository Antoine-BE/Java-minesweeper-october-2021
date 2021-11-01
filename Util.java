package demineur;

import java.util.Random;
import java.util.Scanner;

public class Util {

	private static int size=16;// =size();
	private static int bomb_count=20;//=bomb_count(size);
	public Case[][] board;

	public static int size() {
		// user input for the board size.
		int size=0;
		while (size<5 || size>50) {
			System.out.println("Veuillez entrer la taille du plateau de demineur. Min 4, max 50. ");
			size=(new Scanner(System.in).nextInt());
		}
		Util.size=size;
		return size;
	}

	public static int bomb_count(int size) {
		// user input for the bomb count.
		int bomb_count=0;
		while (bomb_count<1 || bomb_count>((size*size)/10)+2) {
			System.out.println("Veuillez entrer le nombre de bombe. Min 1 et max "+(size*size/10+2));
			bomb_count=(new Scanner(System.in).nextInt());
		}
		Util.bomb_count=bomb_count;
		return bomb_count;

	}

	public static Case[][] board_setup(int[] bomb_locations){
		//bomb_location is an array containing the 1d index of the randomized bomb locations.
		//board_setup() build the Case 2d array, puts the bombs in it and increment by 1 all the adjacent Case.
		
		//This below builds the 2d Case array
		Case[][] board=new Case[bomb_count][bomb_count];
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				board[i][j]= new Case();
				board[i][j].setRow(i);
				board[i][j].setColumn(j);
			}
		}
		
		//This calculates the 2d indexes of the bombs and increments adjacent Case.
		for (int i=0; i<bomb_count; i++) {
			int row=0, column=0;

			row=bomb_locations[i]/size;
			column=bomb_locations[i]%size;

			board[row][column].setValeur(9);
			if (row!=0 && (board[row-1][column].getValeur() != 9) ) {
				board[row-1][column].setValeur(board[row-1][column].getValeur()+1);
			}
			if (row!=0 && column!=0 && (board[row-1][column-1].getValeur() != 9)) {
				board[row-1][column-1].setValeur(board[row-1][column-1].getValeur()+1);
			}
			if (column!=0 && (board[row][column-1].getValeur() != 9)) {
				board[row][column-1].setValeur(board[row][column-1].getValeur()+1);
			}
			if (row!=size-1 && column!=0 && (board[row+1][column-1].getValeur() != 9)) {
				board[row+1][column-1].setValeur(board[row+1][column-1].getValeur()+1);
			}
			if (row!=size-1 && (board[row+1][column].getValeur() != 9)) {
				board[row+1][column].setValeur(board[row+1][column].getValeur()+1);
			}
			if (row!=size-1 && column!=size-1 && (board[row+1][column+1].getValeur() != 9)) {
				board[row+1][column+1].setValeur(board[row+1][column+1].getValeur()+1);
			}
			if (column!=size-1 && (board[row][column+1].getValeur() != 9)) {
				board[row][column+1].setValeur(board[row][column+1].getValeur()+1);
			}
			if (row!=0 && column!=size-1 && (board[row-1][column+1].getValeur() != 9)) {
				board[row-1][column+1].setValeur(board[row-1][column+1].getValeur()+1);
			}
		}
		
		//returns the randomized board, 0 is water, 9 is bombs and numbers are the count of adjacent bombs
		return board;
	}

	public static void setup_bomb_locations(int[] bomb_location) {
		//this randomizes the bomb locations in a 1d array. 
		//Example: numbers are the 1d array index positions for a 2d array with (0,0) being at the top left.
		// 1 2 3 
		// 4 5 6
		// 7 8 9
		Random r=new Random();
		int nb;
		boolean in_array=false;

		for (int i=0; i<bomb_count; i++) {
			bomb_location[i]=-99;
		}

		for (int i=0; i<bomb_count; i++) {
			nb=r.nextInt(size*size);
			if (in_array(bomb_location, nb)==false) {
				bomb_location[i]=nb;
			}else {
				i--;
			}
		}
	}

	public static boolean in_array(int[] bomb_locations, int nb) {
		// intermediary method used to avoid randomizing twice the same bomb location.
		boolean in_array=false;
		for (int j=0; j<bomb_count && in_array==false; j++) {
			if (bomb_locations[j]==nb) {
				in_array=true;
			}
		}
		return in_array;
	}

	public static void board_print_test(Case[][] board) {
		// This prints the solved board (without fog of war). For testing purposes only.
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				System.out.print(board[i][j].getValeur()+" ");
			}
			System.out.println();
		}
	}

	public static void board_print_ascii(Case[][] board) {
		// This prints the board using ASCII characters
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				switch (board[i][j].getRevealed()) {
				case 0:
					System.out.print(". ");
					break;
				case 1:
					System.out.print(board[i][j].getValeur()+" ");
					break;
					
				case 2:
					System.out.print("F ");
					break;
				}
			}
			System.out.println();
		}
	}

	public static int tour_joueur(Case[][] board, int game_status) {
		//this method is basically what happens when the player plays a move.

		Case coup_joue = null;
		boolean flagging=false, sortie_boucle=false;
		int row=-2, column=-2;

		while (row<-1 || row>size-1) {
			System.out.println("Choisissez l'ordonnee de votre coup entre 1 et "+size+". 0 pour drapeau-iser une bombe");
			row=((new Scanner(System.in).nextInt())-1);
		}

		if (row==-1) {
			coup_joue=flag(row, column, board);
			flagging=true;

		}else {
			while ((column<=0 || column>size-1) && (flagging==false || sortie_boucle==false)) {
				System.out.println("Choisissez l'abscisse de votre coup entre 1 et "+size+". 0 pour drapeau-iser une bombe");
				column=((new Scanner(System.in).nextInt())-1);
			}
			if (column==-1) {
				coup_joue=flag(row, column, board);
				flagging=true;
			}else if (flagging=false){
				sortie_boucle=true;
				System.out.println("sortie boucle activee");
			}
		}	

		if (flagging==false) {
			coup_joue=board[row][column];
			coup_joue.setRevealed(1, board, size);
			System.out.println("getvaleur"+coup_joue.getValeur());

			if (coup_joue.getValeur()==9) {
				game_status=2;
			}

		}else if (flagging==true){
			if (coup_joue.getRevealed()==2) {
				coup_joue.setRevealed(0, board, size);
			}else {
				coup_joue.setRevealed(2, board, size);
			}
		}

		System.out.println(coup_joue.getRevealed());
		System.out.println(coup_joue.getColumn());
		System.out.println(coup_joue.getRow());
		return game_status;

	}
	
	public static void board_print(Case[][] board) {
		//This prints the board using UTF-8 characters
		
		System.out.println();
		System.out.print("        ");
		for (int i=0; i<size; i++) {
			System.out.print((i+1)+"     ");
		}

		System.out.println();
		System.out.print("     ");

		for (int i=0; i<size; i++) {		
			System.out.print("   ▼  ");
		}
		System.out.println();

		for (int i=0; i<size; i++) {
			if (i==0) {
				System.out.print("     ┌─────");
			}else {
				if (i==size-1) {
					System.out.print("┬─────┐");
				}else {
					System.out.print("┬─────");
				}
			}
		}

		System.out.println();
		for (int i=0; i<size; i++) {
			if (i<9) {
				System.out.print(i+1+"  ► │");
			}else {
				System.out.print(i+1+" ► │");
			}
			for (int j=0; j<size; j++) {
				if (board[i][j].getRevealed()==1) {
					if (board[i][j].getValeur()==9) {
						System.out.print("  "+"x"+" │");
					}else {
						System.out.print("  "+board[i][j].getValeur()+"  │");
					}
				}else if (board[i][j].getRevealed()==0){
					System.out.print("     │");
				}else if (board[i][j].getRevealed()==2) {
					System.out.print("  F  │");
				}

				//	System.out.print("     ");
			}
			if (i!=size-1) {
				System.out.println();
			}
			System.out.print("     ");
			for (int k=0; k<size && i!=size-1; k++) {
				if (k==0) {
					System.out.print("├─────");
				}else {
					if (k==size-1){
						System.out.print("┼─────┤");
					}else {
						System.out.print("┼─────");
					}
				}
			}
			System.out.println();
		}
		for (int i=0; i<size; i++) {
			if (i==0) {
				System.out.print("     └─────┴");
			}else {
				if (i==size-1) {
					System.out.print("─────┘");
				}else {
					System.out.print("─────┴");
				}
			}
		}
		System.out.println();
	}

	public static Case flag(int row, int column, Case[][] board) {
		while (row<=0 || row>size-1) {
			System.out.println("Choisissez l'ordonnee de la bombe a  drapeau-iser entre 1 et "+size);
			row=((new Scanner(System.in).nextInt())-1);
		}
		while (column<=0 || column>size-1) {
			System.out.println("Choisissez l'abscisse de la bombe a  drapeau-iser entre 1 et "+size);
			column=((new Scanner(System.in).nextInt())-1);
		}
		return board[row][column];	
	}

}
