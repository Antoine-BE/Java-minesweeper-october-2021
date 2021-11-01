package demineur;

import java.util.Random;
// 
public class Main {

    public static void main(String[] args) {
        //game_status: 0 is ongoing, 1 is player victory, 2 is player defeat
        int size=16, bomb_count=20, game_status=0, compteur_tour=0;
        Case[][] board;
        //size=Util.size();
        //bomb_count=Util.bomb_count(size);

        int[] bomb_locations=new int[bomb_count];

        System.out.println(size);
        Util.setup_bomb_locations(bomb_locations);

        board=Util.board_setup(bomb_locations);
        Util.board_print_test(board);

        //Util.board_print(board);
        Util.board_print_ascii(board);

        long start = System.currentTimeMillis();

        for (int i=0; i<10 && game_status==0; i++) {
            System.out.println(game_status);
            game_status=Util.tour_joueur(board, game_status);
            //Util.board_print(board);
            Util.board_print_ascii(board);
            compteur_tour++;
        }
        long finish = System.currentTimeMillis();
        if (game_status==2) {
            System.out.println();
            System.out.println("Vous avez perdu!");
        }else if (game_status==1){
            System.out.println();
            System.out.println("Bravo pour votre victoire en "+compteur_tour);
            System.out.println("Temps de partie : "+ (finish-start));

        }


    }

}
