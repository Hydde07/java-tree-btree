package BMais;

public class BMais {
    private No raiz;
    private int n;
    
    public BMais(int n) {
        this.n = n;
        raiz = null;
    }
    
    private No encontraFolha(int n){
        No walk = raiz;
        int pos = walk.getPos(n);
        while(!walk.isFolha()){
            walk = walk.getNo(pos);
            pos = walk.getPos(n);
        }
        return walk;
    }
    
    private No procurarPai(int info, No no){
        No walk = raiz;
        if(walk == no)
            return no;
        int pos = walk.getPos(info);
        while(walk.getNo(pos) != no){
            walk = walk.getNo(pos);
            pos = walk.getPos(info);
        }
        return walk;
    }
    
    public void split(No pai, No nozin) {
        int quo = this.n, info, npos, ed, i = 0;
        No aux = new No(this.n);
        No aux2 = new No(this.n);
        if(nozin == pai){
            No caixa = new No(this.n);
            pai = raiz = caixa;
        }
        if(!nozin.isFolha())
            quo--;
        ed = quo = quo/2;
        if(!nozin.isFolha())
            ed++;
        info = nozin.getInfo(quo);
        while(i < quo){
            aux.add(nozin.getInfo(i));
            aux.setNo(nozin.getNo(i++), aux.getTL()-1);
        }
        aux.setNo(nozin.getNo(i),aux.getTL());
        while(ed < nozin.getTL()){
            aux2.add(nozin.getInfo(ed));
            aux2.setNo(nozin.getNo(ed++),aux2.getTL()-1);
        }
        aux2.setNo(nozin.getNo(ed),aux2.getTL());
        pai.add(info);
        npos = pai.getPos(info);
        pai.setNo(aux, npos);
        pai.setNo(aux2, npos+1);
        if(nozin.isFolha()){
            aux.setPont(nozin.getAnt(),aux2);
            aux2.setPont(aux, nozin.getProx());
            if(nozin.getAnt() != null)
                nozin.getAnt().setProx(aux);
            if(nozin.getProx() != null)
                nozin.getProx().setAnt(aux2);
        }
        if(pai.getTL() == this.n){
            nozin = pai;
            pai = procurarPai(pai.getInfo(0), pai);
            split(pai, nozin);
        }
    }
    
    public void insertVet(int[] info){
        for(int i = 0; i < info.length; i++)
            insert(info[i]);
    }
    
    public void insert(int info){
        if(raiz == null){
            raiz = new No(n);
            raiz.add(info);
        }
        else{
            No no = encontraFolha(info);
            no.add(info);
            if(no.needSplit(n)){
                No pai = procurarPai(info,no);
                split(pai, no);
            }
        }
    }
    
    public void exibe(){
        exibe(raiz);
    }
    
    private void exibe(No walk){
        if(walk != null){
            for(int i = 0; i < walk.getTL(); i++){
                exibe(walk.getNo(i));
                System.out.println(walk.getInfo(i));
            }
            exibe(walk.getNo(walk.getTL()));
        }
    }
}
