package forza4.ControllerMVC;
/*
Created by L.Acciarri
and D. Rosati
 */
import forza4.ModelMVC.ComputerPlayer;
import forza4.ModelMVC.InteractiveMatch;

public class Controller {
    private InteractiveMatch match;
    public Controller(InteractiveMatch match){
        this.match = match;
    }

    //Metodo che comunica al model che è stata richiesta una mossa da parte
    //del giocatore su una determinata colonna e richiede a sua volta i controlli sulla mossa
    public int move(InteractiveMatch match, int col){
        int row = match.getIns(col);
        if(row==6) return 0;
        if(match.checkWin(row , col)) return 1;
        return -1;
    }

    //Metodo che richiede alla classe computer di effettuare una mossa
    public boolean computerMove(InteractiveMatch match, ComputerPlayer computerPlayer){

        int columnComputerMove;
        int rowComputerMove;
        do {
            columnComputerMove = computerPlayer.move();
            rowComputerMove = match.getIns(columnComputerMove);
        } while (rowComputerMove == 6);
        return match.checkWin(rowComputerMove,columnComputerMove);

    }

}
