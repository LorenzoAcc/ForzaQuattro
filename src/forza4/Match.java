package forza4;

public interface Match {
    int getIns(int col) throws Exception;
    boolean checkWin(int row, int col) throws Exception;
}