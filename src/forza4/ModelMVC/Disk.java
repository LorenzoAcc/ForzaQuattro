/*
Created by L.Acciarri
and D. Rosati
 */
package forza4.ModelMVC;

public class Disk {
    private final int id_player;

    public Disk(int id_p){
        this.id_player=id_p;
    }

    public int getPlayer(){
        return id_player;
    }
}
