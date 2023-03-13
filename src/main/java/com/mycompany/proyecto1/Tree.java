package com.mycompany.proyecto1;

import com.mycompany.proyecto1.type.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Tree {
    node root;
    public Tree(String er, ArrayList<node> leaves, ArrayList<ArrayList> table){
        numLeave numHoja =  new numLeave(er);
        Stack<node> pila = new Stack<>();

        String[] erSplit = er.split(",");

        ArrayList<String> strList = new ArrayList<>(Arrays.asList(erSplit));
        Collections.reverse(strList);

        strList.forEach((character) -> {
            switch(character){
                case "|":
                    node lefto = (node) pila.pop();
                    node righto = (node) pila.pop();

                    node no = new node(character, Types.OR, 0, lefto, righto, leaves, table);
                    pila.push(no);
                    break;
                case ".":
                    node lefta = (node) pila.pop();
                    node righta = (node) pila.pop();

                    node na = new node(character, Types.AND, 0, lefta, righta, leaves, table);
                    pila.push(na);
                    break;
                case "*":
                    node unario = (node) pila.pop();
                    node ncom = new node(character, Types.CERO_MAS, 0, unario, null, leaves, table);
                    pila.push(ncom);
                    break;
                case "+":
                    node unario2 = (node) pila.pop();
                    node nuom = new node(character, Types.UNO_MAS, 0, unario2, null, leaves, table);
                    pila.push(nuom);
                    break;
                case "?":
                    node unario3 = (node) pila.pop();
                    node ncou = new node(character, Types.CERO_UNO, 0, unario3, null, leaves, table);
                    pila.push(ncou);
                    break;
                default:
                    node nd = new node(character, Types.HOJA, numHoja.getNum(), null, null, leaves, table);
                    pila.push(nd); //construir el arbol
                    leave hoja = new leave();
                    hoja.addLeave(nd, leaves); // TABLA DE SIGUIENTES O TRANSICIONES
                    break;
            }
        });
        this.root = (node) pila.pop();

    }
    public node getRoot(){
        return this.root;
    }
}
