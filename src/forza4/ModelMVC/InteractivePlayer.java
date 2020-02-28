package forza4.ModelMVC;

public class InteractivePlayer implements Player {
    //Fields
    private String name;
    private int id_player;

    public InteractivePlayer(String name,int id) {
        this.name=name;
        this.id_player=id;
    }

    @Override
    public int getId() {
        return id_player;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return this.name;
    }

    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub
        this.name=name;
    }

    @Override
    public int move() {
        return 0;
    }

}
