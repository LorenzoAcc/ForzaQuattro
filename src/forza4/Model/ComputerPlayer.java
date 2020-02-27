package forza4.Model;

import java.util.Random;

public class ComputerPlayer implements Player {
    private String name;
    private int id_player;
    public ComputerPlayer(String n,int id){
        name=n;
        id_player=id;
    }
    @Override
    public int getId() {
        return id_player;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public int move() {
        Random rand = new Random();
        return rand.nextInt(7);

    }




}
