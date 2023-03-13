package com.mycompany.proyecto1;

import java.util.ArrayList;


public class leave {
    
    public void addLeave(node nodo, ArrayList<node> leaves){
        leaves.add(nodo);
    }
    
    public node getLeave(int numLeave, ArrayList<node> leaves){
        for (node item : leaves) {
            if(item.number == numLeave) return item;
        }
        return null;
    }
    
    public boolean isAccept(int numLeave, ArrayList<node> leaves){
        for (node item : leaves) {
            if(item.number == numLeave) return item.accept;
        }
        return false;
    }
}


