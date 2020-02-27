package forza4.Model;

import java.util.HashMap;
import java.util.Random;


public class InteractiveMatch implements Match{
    private final Player p1;
    private final Player p2;
    private Player current_player;
    private int ACTUAL_ROW;
    private int ACTUAL_COLUMN;
    private final Matrix matr = new Matrix();

    //Metodo costruttore del match
    public InteractiveMatch(Player p1, Player p2) {
        this.p1=p1;
        this.p2=p2;
        if(this.p2 instanceof ComputerPlayer) {
            this.current_player=p1;
        }else {
            headOrTail();
        }
    }
    public int getIns(int col) {
        Disk d = new Disk(getCurrentPlayer().getId());
        return matr.insertDisk(d, col);

    }

    public boolean checkWin(int row , int col) {
        // TODO Auto-generated method stub
        ACTUAL_ROW = row;
        ACTUAL_COLUMN = col;

        HashMap<Integer, int[]> adj_pos = adjacentPositionsChecker(ACTUAL_ROW,ACTUAL_COLUMN);
        // incapsulo il risultato del controllo della mossa vincente nella variabile responce → se è true allora la partita è finita
        int responce = checkLines(adj_pos); //1 se c'è una mossa vincente
        return responce != 0;
    }

    //Quando richiamiamo la funzione il parametro row è checkInsertion(col)
    private HashMap<Integer,int[]> adjacentPositionsChecker(int row, int col) {
        HashMap<Integer,int[]> adjMap  = new HashMap<>();


        for(int r = -1 ; r <= 1 ; r++) {
            for(int c = -1 ; c <= 1 ; c++) {
                int adj_row = row+(r);
                int adj_col = col+(c);
                if(matr.checkPositionValidity(adj_row, adj_col)
                        && !(adj_row== row && adj_col== col)){
                            Disk adj_disk = matr.getElem(adj_row,adj_col);
                    if(adj_disk != null){
                        if(adj_disk.getPlayer() == current_player.getId()){
                            if(adjMap.containsKey(adj_row)){
                                int[] temporaryArray = new int[adjMap.get(adj_row).length+1];
                                for (int i = 0; i == adjMap.get(adj_row).length; i++){
                                    temporaryArray[i]=adjMap.get(adj_row)[i];
                                }
                                temporaryArray[temporaryArray.length-1]= adj_col;
                                adjMap.put(adj_row, temporaryArray);
                            }
                            else{
                                adjMap.put(adj_row,new int[]{adj_col});
                            }
                        }
                    }
                }
            }
        }

        return adjMap;

    }


    //metodo che controla se le pos. adj della
    private int checkLines(HashMap<Integer, int[]> adj_pos) {
        int win = 0 ;
        //scorro l'hashmap, k che è la chiave corrisponde all'indice della colonna
        // quindi va da 1 a 6
        for(int k = 0; k<6; k++) {
            //vedo se la riga esiste
            //boolean responce = adj_pos.containsKey(k);

            if(adj_pos.containsKey(k)) {
                // salvo le colonne occupare della riga in un array
                int[] pos_in_the_row = adj_pos.get(k);
                // l'array relativo ad una chiave può essere lungo massimo 3.. Perche la matrice di adiacenza può avere massimo tre colonne
                //L'array non può essere vuoto in quanto si presuppone che se viene controllato abbia almeno un elemento
                for(int v=0; v!=pos_in_the_row.length;v++) {
                    if(checkLineFromAdjacentPosition(k,pos_in_the_row[v]))
                        win = current_player.getId();

                }
            }
        }
        return win;
    }

    private boolean checkLineFromAdjacentPosition(int row_adj, int col_adj) {
        //variabili per determinare la linea da controllare sulla base della relazione della
        //pedina adiacente con quella inserita

        //Indici della posizione della pedina adiacente presi come -1/0/+1
        //boolean answer = false;
        if(col_adj==ACTUAL_COLUMN) return checkColumn(row_adj,col_adj);

        if(row_adj==ACTUAL_ROW) return  checkRow(row_adj,col_adj);

        //L'indice della colonna o della riga adiacente è uguale a quello della posizione solo se sono adicenti
        //In verticale oppure in orizzontale
        if(row_adj!=ACTUAL_ROW && col_adj!=ACTUAL_COLUMN) return checkDiagonal(row_adj,col_adj);

        return false;
    }

    private boolean checkColumn(int adj_row, int adj_col) {
        int increaseIndex = 1;
        int pawnCounterInColumn = 2;
        int indx_nxt_row = adj_row - ACTUAL_ROW ;
        //controllo
        while(matr.checkPositionValidity((adj_row+(increaseIndex*indx_nxt_row)),adj_col) &&
                checkColor((adj_row+(increaseIndex*indx_nxt_row)),adj_col) && pawnCounterInColumn<4) {
            increaseIndex++;
            pawnCounterInColumn++;

        }
        return pawnCounterInColumn == 4;

    }
    private boolean checkRow(int adj_row, int adj_col) {


        int indx_nxt_col = adj_col - ACTUAL_COLUMN ;
        int increaseIndex = 1;
        int pawnCounterInRow = 2;

        while(matr.checkPositionValidity(adj_row,(adj_col+(increaseIndex*indx_nxt_col))) &&
                checkColor(adj_row,adj_col+(increaseIndex*indx_nxt_col)) &&
                pawnCounterInRow<4 && (adj_col+(increaseIndex*indx_nxt_col))!=ACTUAL_COLUMN ) {

            increaseIndex++;
            pawnCounterInRow++;




        }
        if(pawnCounterInRow==4) {
            return true;
        }
        else {
            indx_nxt_col = indx_nxt_col*-1;
            int oppositeIndex = 1;
            //L'INDICE -1 SERVE A RIPOSIZIONARE IL PUNTATORE SULLA POSIZIONE OPPOSTA ALLA POSIZIONE ATTUALE
            while(matr.checkPositionValidity(adj_row,(ACTUAL_COLUMN+(oppositeIndex*indx_nxt_col)))
                    && checkColor(adj_row,(ACTUAL_COLUMN+(oppositeIndex*indx_nxt_col)))
                    && pawnCounterInRow<4 && (ACTUAL_COLUMN+(oppositeIndex*indx_nxt_col))!=ACTUAL_COLUMN) {
                oppositeIndex++;
                pawnCounterInRow++;
            }

        }
        return pawnCounterInRow == 4;

    }
    private boolean checkDiagonal(int adj_row, int adj_col) {
        //il counter è relativo alle pedine già in linea e parte da 2 perchè si ha quella inserita e quella adiacente
        int increaseIndex = 1;
        int pawnCounterInDiagonal=2;

        //Indici della posizione della pedina adiacente presi come -1/0/+1
        int indx_adj_row = adj_row - ACTUAL_ROW ;
        int indx_adj_col = adj_col - ACTUAL_COLUMN ;




        while(matr.checkPositionValidity(adj_row+(increaseIndex*indx_adj_row),adj_col+(increaseIndex*indx_adj_col))
                && checkColor(adj_row+(increaseIndex*indx_adj_row),adj_col+(increaseIndex*indx_adj_col))
                && pawnCounterInDiagonal<4 ) {
            increaseIndex++;
            pawnCounterInDiagonal++;

        }
        if(pawnCounterInDiagonal==4) {
            return true;
        }else {
            increaseIndex = 2;
            //cambio il verso del controllo
            indx_adj_row = indx_adj_row*-1;
            indx_adj_col = indx_adj_col*-1;
            while(matr.checkPositionValidity(adj_row+(increaseIndex*indx_adj_row),adj_col+(increaseIndex*indx_adj_col))
                    && checkColor(adj_row+(increaseIndex*indx_adj_row),adj_col+(increaseIndex*indx_adj_col))
                    && pawnCounterInDiagonal<4 ) {
                increaseIndex++;
                pawnCounterInDiagonal++;


            }

        }
        return pawnCounterInDiagonal == 4;
    }


    //Utilizza parametri matriciali quindi la prima posizione è [0,0]
    private boolean checkColor(int row, int col) {
        if(matr.checkPositionValidity(row, col)) {
            Disk D=matr.getElem(row, col);
            if(D==null) {
                return false;
            }
            else {
                int id=D.getPlayer();

                return id == current_player.getId();

            }
        }
        return false;

    }

    public Player headOrTail() {
        Random r = new Random();
        if(r.nextInt(2)+1==p1.getId()){
            current_player=p1;
            return p1;
        }
        current_player=p2;
        return p2;


    }

    public Matrix getMatrix() {
        // TODO Auto-generated method stub
        return this.matr;
    }
    public Player getCurrentPlayer() {

        return current_player;
    }

    public boolean isFull() {
        return matr.getNumDisk() == 42;
    }


    //metodo relativo al cambio di turno
    public void updateCurrentPlayer() {
        if(current_player.equals(p1)){
            current_player=p2;
        }
        else{
            current_player=p1;
        }
    }


}
