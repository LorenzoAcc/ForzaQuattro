package forza4.Controller;


import forza4.Model.ComputerPlayer;
import forza4.Model.InteractiveMatch;

public class Controller {
    private InteractiveMatch match;
    //public ComputerPlayer computerPlayer;
    public Controller(InteractiveMatch match){
        this.match = match;
    }

    public int move(InteractiveMatch match, int col){
        int row = match.getIns(col);
        if(row==6) return 0;
        if(match.checkWin(row , col)) return 1;
        return -1;
    }

    public boolean computerMove(InteractiveMatch match, ComputerPlayer computerPlayer){
        int count = 1;
        int columnComputerMove;
        int rowComputerMove;
        do {
            System.out.println("CONTATORE" + count);
            columnComputerMove = computerPlayer.move();
            rowComputerMove = match.getIns(columnComputerMove);
            count++;
        } while (rowComputerMove == 6);
        return match.checkWin(rowComputerMove,columnComputerMove);

    }

}
