package forza4.ModelMVC;

public interface Player {
    int getId();	//getter dell'id
    String getName(); 	//getter del nome
    void setName(String name);	//setter del nome
    int move();
}
