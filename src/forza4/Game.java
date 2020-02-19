package forza4;

//import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.concurrent.ExecutionException;

public class Game {
    private JPanel gamePage;
    private InteractivePlayer player1;
    private InteractivePlayer player2;
    private InteractiveMatch match ;
    private ComputerPlayer computer;
    private Matrix matr;
    private JFrame frame = new JFrame();
    private JTextPane TextP = new JTextPane();
    private JButton[] btn;
    private Controller controller = new Controller(match,computer);


    Game(String nome1, String nome2) {
        this.player1 = new InteractivePlayer(nome1, 1);
        this.player2 = new InteractivePlayer(nome2, 2);
        this.match = new InteractiveMatch(player1, player2);
        btn = new JButton[7];
        initializeGame();
        ActionList();

    }

    Game(String nome1) {
        this.player1 = new InteractivePlayer(nome1, 1);
        this.computer = new ComputerPlayer("Computer", 2);
        this.match = new InteractiveMatch(player1, computer);
        btn = new JButton[7];
        initializeGame();
        ActionList();
    }

    private void initializeGame() {
        frame = new JFrame();
        frame.setBounds(500, 500, 660, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(new Color(-7105645));

        frame.setTitle("Connect Four");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        gamePage = new JPanel();
        gamePage.setBounds(12,82,625,550);
        gamePage.setBackground(new Color(-7105645));
        frame.getContentPane().add(gamePage);
        gamePage.setLayout(new GridLayout(6,7,0,0));
        gamePage.setVisible(true);

        PanelDisplay();


        for(int i=0;i<btn.length;i++){
            btn[i]=new JButton(Integer.toString(i+1));
            btn[i].setBounds(12+(i*92), 45, 80, 25);
            btn[i].setBackground(new Color(-4867910));
            frame.getContentPane().add(btn[i]);
        }
        TextP.setEditable(false);
        TextP.setBounds(12,15,625,21);
        TextP.setFont(new Font("Courier New", Font.ITALIC, 12));
        frame.getContentPane().add(TextP);
        TurnSetterText();


        frame.setVisible(true);

        JOptionPane.showMessageDialog(null, "Inizia :   " + match.getCurrentPlayer().getName());

    }

    private void PanelDisplay(){
        matr = match.getMatrix();
        for(int i=0; i <6 ; i++){
            for(int k = 0 ; k < 7 ; k++){
                Disk d = matr.getElem(i,k);
                if(d==null){
                        ImageIcon icon = new ImageIcon("src/forza4/images/null.png");
                        JLabel lab = new JLabel(icon,JLabel.CENTER);
                        gamePage.add(lab);
                }

                else if (d.getPlayer()==1){

                        ImageIcon icon = new ImageIcon("src/forza4/images/red.png");
                        JLabel lab = new JLabel(icon,JLabel.CENTER);
                        gamePage.add(lab);
                        lab.setVisible(true);

                }
                else{

                        ImageIcon icon = new ImageIcon("src/forza4/images/yellow.png");
                        JLabel lab = new JLabel(icon,JLabel.CENTER);
                        gamePage.add(lab);
                        lab.setVisible(true);


                }
            }
        }
        TurnSetterText();
    }
    public void TurnSetterText(){
        Player P = match.getCurrentPlayer();
        if(P.equals(player1))
            TextP.setText("Turn → "+player1.getName());
        else if(P.equals(player2))
            TextP.setText("Turn → "+player2.getName());
        else
            TextP.setText("");
    }

    public void updatePanel(){
        gamePage.removeAll();
        PanelDisplay();
        gamePage.revalidate();
    }

    private void ActionList() {
        btn[0].addActionListener(e->onClick(0));
        btn[1].addActionListener(e->onClick(1));
        btn[2].addActionListener(e->onClick(2));
        btn[3].addActionListener(e->onClick(3));
        btn[4].addActionListener(e->onClick(4));
        btn[5].addActionListener(e->onClick(5));
        btn[6].addActionListener(e->onClick(6));
    }
    /*
    private void onClick(int col){
        System.out.println(col);

        int row = controller.onClick(col,match);
        if(row!=6){
            if(controller.check(row,col,match)){
                updatePanel();
                TurnSetterText();
                Full();
                winFrame(match.getCurrentPlayer().getName()+" WIN");
            }
            else if(computer!=null){
                controller.computerMove(match,computer);
            }

        }else{
            JOptionPane.showMessageDialog(null, "ATTENZIONE LA COLONNA "+(col+1)+" E' PIENA.. ESEGUIRE UN'ALTRA MOSSA");
        }
    }
    */

    private void onClick(int col){
        int row = match.getIns(col);
        if (row != 6){
            if(!Check(row, col)) {
                match.updateCurrentPlayer();
                if (computer != null) {
                    boolean alert = false;
                    while (!alert) {
                        int columnComputerMove = computer.move();
                        int rowComputerMove = match.getIns(columnComputerMove);
                        if (rowComputerMove != 6) {
                            alert = true;
                            Check(rowComputerMove, columnComputerMove);
                        }
                    }
                    match.updateCurrentPlayer();
                }

            }
        }
        else {
            JOptionPane.showMessageDialog(null, "ATTENZIONE LA COLONNA "+(col+1)+" E' PIENA.. ESEGUIRE UN'ALTRA MOSSA");
        }

    }

    /*
    private void onClick( int col){

        int moveMade = controller.move(match,col);
        updateGamePage();
        switch (moveMade){
            case 0:
                JOptionPane.showMessageDialog(null, "ATTENZIONE LA COLONNA "+(col+1)+" E' PIENA.. ESEGUIRE UN'ALTRA MOSSA");
                break;
            case 1:
                winFrame(match.getCurrentPlayer().getName()+" WIN");
                break;
        }
        match.updateCurrentPlayer();

        if (computer != null && controller.computerMove(computer)) {
            updateGamePage();
            winFrame(match.getCurrentPlayer().getName()+" WIN");
        }

    }
    */

    private boolean Check(int row, int col){

        updatePanel();
        TurnSetterText();
        //win(row,col);
        Full();
        return win(row,col);
    }

    private void updateGamePage(){

        updatePanel();
        TurnSetterText();
        Full();

    }


    public void Full(){
        if(match.isFull())
            winFrame("PATTA");
    }
    public void winFrame(String str){
        JOptionPane.showMessageDialog(null, str);
        //frame.enable(false);
        frame.dispose();

    }

    private boolean win(int row, int col){
        boolean responce = match.checkWin(row,col);
        if (responce){
            winFrame(match.getCurrentPlayer().getName()+" WIN");
        }
        return responce;


    }

}
