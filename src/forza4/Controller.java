package forza4;

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

        int columnComputerMove;
        int rowComputerMove;
        do {
            columnComputerMove = computerPlayer.move();
            rowComputerMove = match.getIns(columnComputerMove);
        } while (rowComputerMove == 6);
        return match.checkWin(rowComputerMove,columnComputerMove);

    }

}
