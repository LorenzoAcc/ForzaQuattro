package forza4.JUnitTest;

import forza4.Disk;
import forza4.Matrix;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MatrixTest {
    int count = 0;
    Matrix matr = new Matrix();

    @BeforeClass
    public static void checkEmpty(){
        Matrix matr = new Matrix();
        assertEquals(0,matr.getNumDisk());
        for (int i = 0 ; i <= 5; i++){
            for (int j=0;j<=6;j++){
                assertEquals(null,matr.getElem(i,j));
            }
        }
    }
    @Test
    public void checkPositionValidity() {
        //controllo una serien di posizioni non valide che vanno da row=[6...106] col=[7...107]
        for(int i = 0; i <= 100000; i++){
            assertEquals(false,matr.checkPositionValidity(new Random().nextInt(100)+6,new Random().nextInt(100)+7));

        }
        for(int i = 0 ; i <= 10000; i++){
            assertEquals(true,matr.checkPositionValidity(new Random().nextInt(5),new Random().nextInt(6)));

        }

    }

    @Test
    public void insertDisk() {
        for(int i=0; i<=6;i++){
            for (int j = 5; j>=0;j--){
                //riempio le colonne
                assertEquals(j,matr.insertDisk(new Disk(1),i));
                //Indica che la colonna è piena
            }

            //Stresso l'inserimento l'inserimento di una pedina nelle colonne già piene eseguendo dai 3000 ai 100000 test
            int numberOfRandomTest = new Random().nextInt(100000) +3000;
            for (int z = 0 ; z != numberOfRandomTest ; z++){
                assertEquals(6,matr.insertDisk(new Disk(1),0));
            }
            //DECOMMENTARE IL SEGUENTE CODICE PER VEDERE CHE IL TEST FALLISCE
            //IN QUANTO LA COLONNA E' PIENA QUINDI DOVREMMO AVERE 6, CHE INDICA
            //IL FATTO CHE NON PUO' ESSERE INSERITA ALCUNA PEDINA
            //assertEquals(0,matr.insertDisk(new Disk(1),0));

        }





    }
    @Test
    public void getElem() {

    }

    @Test
    public void getNumDisk() {
        /*Vado a stressare il sistema inserendo ed eliminando dalla matrice delle pedine per un quantitativo
        di tentativi infinitesimale randomatico. Memorizzo il numero delle pedine di partenza in una variabile count la quale
        viene aggiornata ogni volta che si effettua una operazione di scrittura sulla matrice. Alla fine si confronta tale variabile
        con il metodo getNumDisk
        */
        Matrix matrice = new Matrix();
        for(int i=0; i<=6;i++) {
            for (int j = 5; j >= 0; j--) {
                //riempio le colonne
                assertEquals(j, matrice.insertDisk(new Disk(1), i));
            }
        }
        int count = matrice.getNumDisk();
        int stressingTestCount = new Random().nextInt(100000); // 5000...100000
        for (int i = 1; i <= stressingTestCount;i++){
            int colDelete = new Random().nextInt(6);
            if(matrice.deleteDisk(colDelete)) count--;

            int colInsert = new Random().nextInt(6);

            if(matrice.insertDisk(new Disk(1),colInsert)!=6) count++;
        }
        //Verifico se il counter interno coincide con il ritorno di getNumDisk()
        assertEquals(count,matrice.getNumDisk());
    }
}