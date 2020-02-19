package forza4;

import javax.sound.midi.SysexMessage;

public class Controller {
    private InteractiveMatch match;
    private ComputerPlayer computerPlayer;
    public Controller(InteractiveMatch match , ComputerPlayer computerPlayer){
        this.match = match;
        this.computerPlayer = computerPlayer;
    }
    /*
    public int onClick(int col, InteractiveMatch match) {
        System.out.println(col);

        int row = match.getIns(col);
        System.out.println(row);
        if (row != 6) return row; //mossa effettuata
        return 6;
    }
    public boolean check(int row, int col, InteractiveMatch match){
        if(win(row,col,match)) return true;
        return false;


    }
    public boolean win(int row, int col,InteractiveMatch match) {
        boolean responce = match.checkWin(row,col);
        if(responce) return true;
        return false;
    }
    public void computerMove(InteractiveMatch match,ComputerPlayer computer){
        boolean alert = false;
        while (!alert){
            int columnComputerMove = computer.move();
            int rowComputerMove = match.getIns(columnComputerMove);
            if (rowComputerMove != 6) {
                alert = true;
                check(rowComputerMove, columnComputerMove, match);
            }
        }
        match.updateCurrentPlayer();
    }
    */
    public int move(InteractiveMatch match , int col){
        int row = match.getIns(col);
        if(row==6) return 0;
        if(match.checkWin(row , col)) return 1;
        return -1;
    }

    public boolean computerMove(ComputerPlayer computerPlayer){
        boolean alert = false;
        while (!alert) {
            int columnComputerMove = computerPlayer.move();
            System.out.println(columnComputerMove);
            int rowComputerMove = match.getIns(columnComputerMove);
            if (rowComputerMove != 6) {
                alert = true;
                return match.checkWin(rowComputerMove, columnComputerMove);
            }
        }
    return false;
    }

}
