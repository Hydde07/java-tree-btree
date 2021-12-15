package BTree;

public class BTree {
    private No raiz;
    private int n;
    
    public BTree(int n) {
        this.n = n;
        raiz = null;
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
    
    private No encontraNo(int num){
        No walk = raiz;
        return encontraNo(walk,num);
    }
    
    public No encontraNo(No no, int num){
        if(!no.isFolha()){
            int pos = no.getPos(num);
            return encontraNo(no.getNo(pos), num);
        }
        return no;
    }
    
    public void delete(int info) {
        No walk = encontraNo(info);
        int pos = walk.getPos(info);
        
        if(walk != null) {
            if(!walk.isFolha()) {
                No nozin = percorreEsq(walk.getNo(pos));
                int naux = 0;
                
                if(nozin.insuficiente(n-1)) {
                    nozin = percorreDir(walk.getNo(pos+1));
                }
                else {
                    naux = nozin.getTL()-1;
                }
                    walk.setInfo(nozin.getInfo(naux),pos);
                    walk = nozin;
                    pos = naux;
            }
            walk.deletePosition(pos);
            while(walk != raiz && walk.getTL()<n) {
                No pai = procurarPai(walk.getInfo(0), walk);
                pos = pai.getPos(walk.getInfo(0));
                No esq = null, dir = null;
                
                if(pos > 0)
                    esq = pai.getNo(pos-1);
                if(pos < pai.getTL())
                    dir = pai.getNo(pos+1);
                
                if(esq != null && esq.getTL()>n) {
                    walk.add(pai.getInfo(pos-1));
                    walk.setNo(esq.getNo(esq.getTL()),0);
                    pai.setInfo(esq.getInfo(esq.getTL()-1),pos-1);
                    esq.deletePosition(esq.getTL()-1);
                }
                else if(dir != null && dir.getTL() > n) {
                    walk.add(pai.getInfo(pos));
                    walk.setNo(dir.getNo(0),walk.getTL());
                    pai.setInfo(dir.getInfo(0),pos);
                    dir.deletePosition(0);
                }
                else {
                    int ed;
                    if(esq != null){
                        ed = 0;
                        esq.add(pai.getInfo(pos-1));
                        while(ed<walk.getTL()){
                            esq.add(walk.getInfo(ed));
                            esq.setNo(walk.getNo(ed++),esq.getTL()-1);
                        }
                        esq.setNo(walk.getNo(ed),esq.getTL());
                        
                        if(pai == this.raiz && pai.getTL()==1)
                            raiz = pai = esq;
                        else{
                            pai.setNo(pai.getNo(pos-1),pos);
                            pai.deletePosition(pos-1);
                        }
                    }
                    else {
                        ed = walk.getTL()-1;
                        dir.add(pai.getInfo(pos));
                        while(ed>=0){
                            dir.add(walk.getInfo(ed));
                            dir.setNo(walk.getNo((ed--)+1),1);
                        }
                        dir.setNo(walk.getNo(0),0);
                        if(pai==this.raiz && pai.getTL()==1)
                            raiz = pai = dir;
                        else
                            pai.deletePosition(pos);
                    }
                    walk = pai;
                }
            }
        }
    }
    
    private void split(No p, No f){
        No esq = new No(n,n), dir = new No(n,n);
        for(int i = 0; i < n; i++){
            esq.setInfo(f.getInfo(i), i);
            esq.setNo(f.getNo(i), i);
            
            dir.setInfo(f.getInfo(i+n+1), i);
            dir.setNo(f.getNo(i+n+1), i);
        }
        esq.setNo(f.getNo(n), n);
        dir.setNo(f.getNo(n*2+1), n);
        
        if(p == f){
            p.setInfo(p.getInfo(n), 0);
            p.setNo(esq, 0);
            p.setNo(dir, 1);
            p.setTL(1);
        }
        else{
            p.add(f.getInfo(n));
            int pos = p.getPos(f.getInfo(n));
            p.setNo(esq, pos);
            p.setNo(dir, pos+1);
            if(p.needSplit(n)){
                f = p;
                p = procurarPai(p.getInfo(0), p);
                split(p,f);
            }
        }
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
    
    private No percorreDir(No walk){
        while(walk.getNo(0)!=null)
            walk = walk.getNo(0);
        return walk;
    }
    
    private No percorreEsq(No walk){
        while(walk.getNo(walk.getTL())!=null)
            walk = walk.getNo(walk.getTL());
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