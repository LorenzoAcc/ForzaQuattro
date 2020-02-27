package forza4.Model;

public interface Match {
    int getIns(int col) throws Exception;
    boolean checkWin(int row, int col) throws Exception;
}
