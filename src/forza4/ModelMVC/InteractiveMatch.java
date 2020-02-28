/*
Created by L.Acciarri
and D. Rosati
 */
package forza4.ModelMVC;

import java.util.HashMap;
import java.util.Random;


public class InteractiveMatch implements Match {
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
    //Metodo che verifica la vittoria in una data posizione
    public boolean checkWin(int row , int col) {
        // TODO Auto-generated method stub
        ACTUAL_ROW = row;
        ACTUAL_COLUMN = col;

        //HashMap contenente le posizioni delle pedine adiacenti
        HashMap<Integer, int[]> adj_pos = adjacentPositionsChecker(ACTUAL_ROW,ACTUAL_COLUMN);

        if(getAdiacentPositionWin(adj_pos)) return true;
        return false;
    }

    //Metodo per la creazione dell'HashMap contenente le posizioni adiacenti
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


    //Metodo che controlla determina se la mossa effettuata in relazione alle pedine adiacenti sia vincente
    private boolean getAdiacentPositionWin(HashMap<Integer, int[]> adj_pos) {
        //scorro l'hashmap, k che è la chiave corrisponde all'indice della colonna
        for(int k = 0; k<6; k++) {
            //vedo se la riga esiste
            if(adj_pos.containsKey(k)) {
                // salvo le colonne occupare della riga in un array
                int[] pos_in_the_row = adj_pos.get(k);
                // l'array relativo ad una chiave può essere lungo massimo 3.. Perche la matrice di adiacenza può avere massimo tre colonne
                //L'array non può essere vuoto in quanto si presuppone che se viene controllato abbia almeno un elemento
                for(int v=0; v!=pos_in_the_row.length;v++) {
                    if(checkLineFromAdjacentPosition(k,pos_in_the_row[v])) return true;

                }
            }
        }

        return false;
    }
    //Metodo che determina quale controllo deve essere fatto conoscendo la posizione della pedina inserita e di quella adiacete
    private boolean checkLineFromAdjacentPosition(int row_adj, int col_adj) {
        //variabili per determinare la linea da controllare sulla base della relazione della
        //pedina adiacente con quella inserita

        if(col_adj==ACTUAL_COLUMN) return checkColumn(row_adj,col_adj);

        if(row_adj==ACTUAL_ROW) return  checkRow(row_adj,col_adj);

        if(row_adj!=ACTUAL_ROW && col_adj!=ACTUAL_COLUMN) return checkDiagonal(row_adj,col_adj);

        return false;
    }

    //Metodo che controlla se la pedina inserita con quella adiacente che si stà studiando porti ad una vittoria sulla linea verticale
    private boolean checkColumn(int adj_row, int adj_col) {

        int indx_nxt_row = adj_row - ACTUAL_ROW ;
        int pawnCounterInColumn = 2;
        int increaseIndex = 1;

        while(matr.checkPositionValidity((adj_row+(increaseIndex*indx_nxt_row)),adj_col) &&
                checkColor((adj_row+(increaseIndex*indx_nxt_row)),adj_col) && pawnCounterInColumn<4) {
            increaseIndex++;
            pawnCounterInColumn++;

        }
        return pawnCounterInColumn == 4;
    }
    //Metodo che controlla se la pedina inserita con quella adiacente che si stà studiando porti ad una vittoria sulla linea orizzontale
    private boolean checkRow(int adj_row, int adj_col) {

        int indx_nxt_col = adj_col - ACTUAL_COLUMN ;
        int pawnCounterInRow = 2;
        int increaseIndex = 1;

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

            while(matr.checkPositionValidity(adj_row,(ACTUAL_COLUMN+(oppositeIndex*indx_nxt_col)))
                    && checkColor(adj_row,(ACTUAL_COLUMN+(oppositeIndex*indx_nxt_col)))
                    && pawnCounterInRow<4 && (ACTUAL_COLUMN+(oppositeIndex*indx_nxt_col))!=ACTUAL_COLUMN) {
                oppositeIndex++;
                pawnCounterInRow++;
            }

        }
        return pawnCounterInRow == 4;

    }

    //Metodo che controlla se la pedina inserita con quella adiacente che si stà studiando porti ad una vittoria sulla linea diagonale
    private boolean checkDiagonal(int adj_row, int adj_col) {

        int indx_adj_row = adj_row - ACTUAL_ROW ;
        int indx_adj_col = adj_col - ACTUAL_COLUMN ;
        int pawnCounterInDiagonal=2;

        int increaseIndex = 1;


        pawnCounterInDiagonal = getPawnCounterInDiagonal(adj_row, adj_col, indx_adj_row, indx_adj_col, pawnCounterInDiagonal, increaseIndex);
        if(pawnCounterInDiagonal==4) {
            return true;
        }else {
            increaseIndex = 2;
            //cambio il verso del controllo
            indx_adj_row = indx_adj_row*-1;
            indx_adj_col = indx_adj_col*-1;
            pawnCounterInDiagonal = getPawnCounterInDiagonal(adj_row, adj_col, indx_adj_row, indx_adj_col, pawnCounterInDiagonal, increaseIndex);

        }
        return pawnCounterInDiagonal == 4;
    }
    //Metodo che ritorna il numero di pedina in riga sulla diagonale
    private int getPawnCounterInDiagonal(int adj_row, int adj_col, int indx_adj_row, int indx_adj_col, int pawnCounterInDiagonal, int increaseIndex) {
        while(matr.checkPositionValidity(adj_row+(increaseIndex*indx_adj_row),adj_col+(increaseIndex*indx_adj_col))
                && checkColor(adj_row+(increaseIndex*indx_adj_row),adj_col+(increaseIndex*indx_adj_col))
                && pawnCounterInDiagonal<4 ) {
            increaseIndex++;
            pawnCounterInDiagonal++;

        }
        return pawnCounterInDiagonal;
    }


    //Metodo che determina se la posizione analizzata contiene
    // una pedina che appartiene al giocatore corrente
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


    //Metodo relativo al cambio di turno
    public void updateCurrentPlayer() {
        if(current_player.equals(p1)){
            current_player=p2;
        }
        else{
            current_player=p1;
        }
    }


}
