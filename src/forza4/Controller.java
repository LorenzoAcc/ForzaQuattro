package forza4;



class Controller {
    private InteractiveMatch match;
    //public ComputerPlayer computerPlayer;
    Controller(InteractiveMatch match){
        this.match = match;
    }

    int move(InteractiveMatch match, int col){
        int row = match.getIns(col);
        if(row==6) return 0;
        if(match.checkWin(row , col)) return 1;
        return -1;
    }

    boolean computerMove(InteractiveMatch match, ComputerPlayer computerPlayer){
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
