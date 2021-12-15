package BMais;

public class No {
    private int[] info;
    private No[] no;
    private No prox, ant;
    private int TL;

    public No(int n) {
        this.info = new int[2*n+1];
        this.no = new No[2*n+2];
        this.TL = 0;
    }
    public No(int n, int TL) {
        this.info = new int[2*n+1];
        this.no = new No[2*n+2];
        this.TL = TL;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }

    public void setPont(No ant, No prox) {
        this.ant = ant;
        this.prox = prox;
    }
    

    public int[] getInfo() {
        return info;
    }

    public int getInfo(int pos) {
        return info[pos];
    }

    public void setInfo(int[] info) {
        this.info = info;
    }

    public void setInfo(int info, int pos) {
        this.info[pos] = info;
    }

    public No[] getNo() {
        return no;
    }

    public No getNo(int pos) {
        return no[pos];
    }

    public void setNo(No[] no) {
        this.no = no;
    }

    public void setNo(No no, int pos) {
        this.no[pos] = no;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }
    
    public void add(int n){
        int p = getPos(n);
        insertPosition(p);
        info[p] = n;
    }
    
    public int getPos(int n){
        int i = 0;
        while(i < TL && info[i] < n)
            i++;
        return i;
    }
    
    private void insertPosition(int pos){
        no[TL+1] = no[TL];
        int p = TL++;
        while(p > pos){
            info[p] = info[p-1];
            no[p] = no[p-1];
            p--;
        }
    }
    
    public void deletePosition(int pos){
        while(pos < TL-1){
            info[pos] = info[pos+1];
            no[pos] = no[pos+1];
            pos++;
        }
        no[pos] = no[pos+1];
        TL--;
    }
    
    public boolean isFolha(){
        return no[0] == null;
    }
    
    public boolean needSplit(int n){
        return TL > 2*n;
    }
    
    public boolean insuficiente(int n){
        return TL < n;
    }
}