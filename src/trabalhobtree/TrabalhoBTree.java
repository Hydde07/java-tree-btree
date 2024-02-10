package trabalhobtree;

import BMais.BMais;
import BTree.BTree;

public class TrabalhoBTree {

    public static void main(String[] args) {
        int n = 2;
        int[] vet = {30,10,20,40,50,3,4,8,9,11,13,17,25,28,33,36,43,45,48,52,55};
        int[] exc = {45,30,28,48,8,10,4,13,40,55,17,33,11,36,3,9,52};
        int[] vet2 = {23,32,12,11,4,7,8,2,1};
        System.out.println("BTREE INSERÇÃO----------------------------");
        BTree btree = new BTree(n);
        btree.insertVet(vet);
        btree.exibe();
        System.out.println("\nBTREE EXCLUSÃO----------------------------");
        for(int i = 0; i < exc.length; i++)
            btree.delete(exc[i]);
        btree.exibe();
        System.out.println("\nB+ INSERÇÃO-------------------------------");
        BMais bmais = new BMais(n);
        bmais.insertVet(vet2);
        bmais.exibe();
    }
}
