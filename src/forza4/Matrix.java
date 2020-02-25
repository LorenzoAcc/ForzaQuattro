package forza4;

public class Matrix {
        // Fields
        private static final int DEFAULT_COL = 7;
        private static final int DEFAULT_ROW = 6;
        private int NUM_DISK = 0;

        //Creazione matrice
        public final Disk[][] field;

        public Matrix(){
            this.field = new Disk[DEFAULT_ROW][DEFAULT_COL];
        }

        //inserimeto del disco nella colonna
        public int insertDisk(Disk d, int col) {
            int rowIndex ;
            rowIndex = this.checkInsertion(col);
            if(rowIndex != 0) {
                field[rowIndex-1][col] = d;
                NUM_DISK++;
                return rowIndex-1;
            }
            return DEFAULT_ROW;
        }

        //Metodo che scorre la matrice dall'alto verso il basso relativamente ad una colonna
        //per cercare la prima casella occupata
        private int checkInsertion(int col) {
            for(int r = 0; r < DEFAULT_ROW; r++) {
                //L'if non ritorna r-1 nel caso in la colonna è piena → ritorna DEFAULT_ROW
                if(this.field[r][col]!=null) {
                    return r;
                }
            }
            return DEFAULT_ROW;
        }


        //Metodo che controlla le coordinate
        public boolean checkPositionValidity(int row, int col) {
            if(row>-1 && row<6 && col>-1 && col<7)  return true;
            return false;

        }
        // Metodo per eseguire il getter dell'elemento della matrice
        // Può essere utilizzato ad esempio per i controlli di vincita
        public Disk getElem(int row, int col) {

            return field[row][col];
        }

        //Metodo che ritorna il numero di dischi
        public int getNumDisk() {

            return NUM_DISK;
        }
        public boolean deleteDisk(int colDelete){
            int rowDelete = checkInsertion(colDelete);
            if(rowDelete!= 5 && getElem(rowDelete,colDelete)!=null){
                field[rowDelete][colDelete]=null;
                NUM_DISK--;
                return true;
            }
            return false;
        }
        public void makeEmpty() {
            for (int i = 0; i <= 5; i++) {
                for (int j = 0; j <= 6; j++) {
                    field[i][j]=null;
                }
            }
        }


    }


