package com.mycompany.proyecto1;

import com.mycompany.proyecto1.type.Types;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;


public class Tree {
    node root;
    String titulo;
    Integer cont = 0;
    public Tree(String er, ArrayList<node> leaves, ArrayList<ArrayList> table){
        String divisores = ".|*?+";
        String[] erSplit = er.split(",");
        int conthojas = 0;
        for (int i =0; i < erSplit.length; i++){
            if(divisores.indexOf(erSplit[i]) == -1){
                conthojas++;
            }
        }

        numLeave numHoja =  new numLeave(conthojas);
        Stack<node> pila = new Stack();

        

        ArrayList<String> strList = new ArrayList<>(Arrays.asList(erSplit));
        Collections.reverse(strList);
        cont = 0;
        strList.forEach((character) -> {
            switch(character){
                case "|":
                    node lefto = (node) pila.pop();
                    node righto = (node) pila.pop();

                    node no = new node(character, Types.OR, 0, cont, lefto, righto, leaves, table);
                    cont++;
                    pila.push(no);
                    break;
                case ".":
                    node lefta = (node) pila.pop();
                    node righta = (node) pila.pop();

                    node na = new node(character, Types.AND, 0, cont,lefta, righta, leaves, table);
                    cont++;
                    pila.push(na);
                    break;
                case "*":
                    node unario = (node) pila.pop();
                    node ncom = new node(character, Types.CERO_MAS, 0,cont, unario, null, leaves, table);
                    cont++;
                    pila.push(ncom);
                    break;
                case "+":
                    node unario1 = (node) pila.pop();
                    node nuom = new node(character, Types.UNO_MAS, 0, cont, unario1, null, leaves, table);
                    cont++;
                    pila.push(nuom);
                    break;
                case "?":
                    node unario2 = (node) pila.pop();
                    node ncou = new node(character, Types.CERO_UNO, 0, cont, unario2, null, leaves, table);
                    cont++;
                    pila.push(ncou);
                    break;
                default:
                    node nd = new node(character, Types.HOJA, numHoja.getNum(), cont, null, null, leaves, table);
                    cont++;
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
    public void GraficarArbol(String titulo){
        String grafica = "digraph G {\n\n node[shape=record] \n\n" + GraficaNodos(this.root) + "\n\n}";
        String grafica2 = "digraph G {\n rankdir=\"LR\" \n" + GraficaANDF((node)this.root.left) + "\nS" + conts + "[shape = doublecircle]; \n}";        
        crearGraficaArbol(titulo, grafica);
        crearGraficaAFND(titulo, grafica2);
    }
    private String GraficaNodos(node nodo){        
        String etiqueta = "";
        String primeros = "";
        String ultimos = "";
        String anulable = "";
        if(nodo.anullable){
            anulable = "A";
        }
        else{
            anulable = "N";
        }
        if(nodo.first.size() > 0){
            for(int i = 0; i<nodo.first.size();i++){
                if(i == nodo.first.size() - 1){
                    primeros += nodo.first.get(i);
                }
                else{
                    primeros += nodo.first.get(i) + ",";
                }
            }
        }
        if(nodo.last.size() > 0){
            for(int i = 0; i<nodo.last.size();i++){
                if(i == nodo.last.size() - 1){
                    ultimos += nodo.last.get(i);
                }
                else{
                    ultimos += nodo.last.get(i) + ",";
                }
            }
        }
        var arraylex = nodo.lexeme.toCharArray();
        if (nodo.left == null && nodo.right == null){
            if(arraylex[0] == '\\'){    
                etiqueta += "nodo" + nodo.id + "[label = \"" + primeros + "|{" + anulable + "|\\" + nodo.lexeme + "|" + nodo.number + "}|" + ultimos + "\"];\n" ;
                //etiqueta += "nodo" + nodo.id + "[label = \"\\" + nodo.lexeme +  "\"];\n";
            }
            else if(arraylex[0] == '\"'){
                etiqueta += "nodo" + nodo.id + "[label = \"" + primeros + "|{" + anulable + "|\\" + arraylex[0] + arraylex[1] + "\\" + arraylex[2] + "|" + nodo.number + "}|" + ultimos + "\"];\n" ;
                //etiqueta += "nodo" + nodo.id + "[label = " + nodo.lexeme +  "];\n";
            }
            else if(arraylex[0] == '|'){
                etiqueta += "nodo" + nodo.id + "[label = \"" + primeros + "|{" + anulable + "|\\" + nodo.lexeme + "|" + nodo.number + "}|" + ultimos + "\"];\n" ;
            }
            else{
                etiqueta += "nodo" + nodo.id + "[label = \"" + primeros + "|{" + anulable + "|" + nodo.lexeme + "|" + nodo.number + "}|" + ultimos + "\"];\n" ;
                //etiqueta += "nodo" + nodo.id + "[label = \"" + nodo.lexeme +  "\"];\n";
            }
        }
        else{
            if(arraylex[0] == '\\'){    
                etiqueta += "nodo" + nodo.id + "[label = \"" + primeros + "|{" + anulable + "|\\" + nodo.lexeme + "|" + nodo.number + "}|" + ultimos + "\"];\n" ;
                //etiqueta += "nodo" + nodo.id + "[label = \"\\" + nodo.lexeme +  "\"];\n";
            }
            else if(arraylex[0] == '\"'){
                etiqueta += "nodo" + nodo.id + "[label = \"" + primeros + "|{" + anulable + "|\\" + arraylex[0] + arraylex[1] + "\\" + arraylex[2] + "|" + nodo.number + "}|" + ultimos + "\"];\n" ;
                //etiqueta += "nodo" + nodo.id + "[label = " + nodo.lexeme +  "];\n";
            }
            else if(arraylex[0] == '|'){
                etiqueta += "nodo" + nodo.id + "[label = \"" + primeros + "|{" + anulable + "|\\" + nodo.lexeme + "|" + nodo.number + "}|" + ultimos + "\"];\n" ;
            }
            else{
                etiqueta += "nodo" + nodo.id + "[label = \"" + primeros + "|{" + anulable + "|" + nodo.lexeme + "|" + nodo.number + "}|" + ultimos + "\"];\n" ;
                //etiqueta += "nodo" + nodo.id + "[label = \"" + nodo.lexeme +  "\"];\n";
            }    

            if (nodo.left != null){
                var left = (node) nodo.left;
                etiqueta += GraficaNodos(left) + "nodo" + nodo.id + "-> nodo" + left.id + ";\n";
            }
            if (nodo.right != null){
                var right = (node) nodo.right;
                etiqueta += GraficaNodos(right) + "nodo" + nodo.id + "-> nodo" + right.id + ";\n";
            }
        }
        return etiqueta;
    }
    //GRAFICAR AFND
    int conts = 0;
    private String GraficaANDF(node nodo){        
        String etiqueta = "";
        var arraylex = nodo.lexeme.toCharArray();
        if (nodo.left == null && nodo.right == null){
            if(arraylex[0] == '\\'){
                etiqueta +=  "[label=\"\\" + nodo.lexeme + "\"]";
            }
            else if(arraylex[0] == '\"'){
                etiqueta +=  "[label=\"\\" + arraylex[0] + arraylex[1] + "\\" + arraylex[2] +"\"]";
            }
            else{
                etiqueta +=  "[label=\"" + nodo.lexeme + "\"]";
            }
        }
        else{
            if(arraylex[0] == '.'){
                if(nohijos((node) nodo.left)){
                    etiqueta += "\nS" + conts + " -> S" + (++conts) + GraficaANDF((node)nodo.left);
                }
                else{
                    etiqueta += GraficaANDF((node) nodo.left);
                }
                if(nohijos((node) nodo.right)){
                    etiqueta += "\nS" + conts + " -> S" + (++conts) + GraficaANDF((node)nodo.right);
                }
                else{
                    etiqueta += GraficaANDF((node) nodo.right);
                }
                
            }
            else if(arraylex[0] == '|'){   
                var contor = conts;             
                etiqueta += "\nS" + contor + " -> S" + (++conts) + "[label=\"e\"]";
                if(nohijos((node) nodo.left)){
                    etiqueta += "\nS" + conts + " -> S" + (++conts) + GraficaANDF((node)nodo.left);
                }
                else{
                    etiqueta += GraficaANDF((node) nodo.left);
                }
                var contfinal = conts;
                etiqueta += "\nS" + contor + " -> S" + (++conts) + "[label=\"e\"]";
                if(nohijos((node) nodo.right)){
                    etiqueta += "\nS" + conts + " -> S" + (++conts) + GraficaANDF((node)nodo.right);
                }
                else{
                    etiqueta += GraficaANDF((node) nodo.right);
                }
                etiqueta += "\nS" + conts + " -> S" + (++conts) + "[label=\"e\"]";

                etiqueta += "\nS" + contfinal + " -> S" + conts + "[label=\"e\"]";
            }
            else if(arraylex[0] == '*'){
                var contcom = conts;
                etiqueta += "\nS" + contcom + " -> S" + (++conts) + "[label=\"e\"]";
                var contini = conts;
                if(nohijos((node) nodo.left)){
                    etiqueta += "\nS" + conts + " -> S" + (++conts) + GraficaANDF((node)nodo.left);
                }
                else{
                    etiqueta += GraficaANDF((node) nodo.left);
                }
                etiqueta += "\nS" + conts + " -> S" + contini + "[label=\"e\"]";
                etiqueta += "\nS" + conts + " -> S" + (++conts) + "[label=\"e\"]";
                etiqueta += "\nS" + contcom + " -> S" + conts + "[label=\"e\"]";
            }
            else if(arraylex[0] == '+'){
                etiqueta += "\nS" + conts + " -> S" + (++conts) + "[label=\"e\"]";
                var contini = conts;
                if(nohijos((node)nodo.left)){
                    etiqueta += "\nS" + conts + " -> S" + (++conts) + GraficaANDF((node)nodo.left);
                }
                else{
                    etiqueta += GraficaANDF((node) nodo.left);
                }
                etiqueta += "\nS" + conts + " -> S" + contini + "[label=\"e\"]";
                etiqueta += "\nS" + conts + " -> S" + (++conts) + "[label=\"e\"]";
            }
            else if(arraylex[0] == '?'){
                var contcou = conts;
                etiqueta += "\nS" + contcou + " -> S" + (++conts) + "[label=\"e\"]";
                if(nohijos((node)nodo.left)){
                    etiqueta += "\nS" + conts + " -> S" + (++conts) + GraficaANDF((node) nodo.left);
                }
                else{
                    etiqueta += GraficaANDF((node) nodo.left);
                }
                etiqueta += "\nS" + conts + " -> S" + (++conts) + "[label=\"e\"]";
                etiqueta += "\nS" + contcou + " -> S"+ conts + "[label=\"e\"]";
            }
        }
        return etiqueta;
    }
    private Boolean nohijos(node nodo){
        var nodol = (node) nodo.left;
        var nodor = (node) nodo.right;
        if(nodol != null){
            return false;
        }
        else if(nodor != null){
            return false;
        }
        else{
            return true;
        }
    }
    private void crearGraficaArbol(String titulo, String grafica){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("src/main/java/Reportes/ARBOLES_202111478/" + titulo + ".dot");
            pw = new PrintWriter(fichero);
            pw.println(grafica);
        } catch (Exception e) {
            System.out.println("error, no se realizo el archivo"+e);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                    System.out.println("METODO DEL ARBOL GENERADO CORRECTAMENTE");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //para compilar el archivo dot y obtener la imagen
        try {
            //"C:\\Program Files\\Graphviz\\bin\\dot.exe"
            //dirección doonde se ecnuentra el compilador de graphviz
            String dotPath = "C:\\Program Files\\Graphviz\\bin\\dot.exe";
            //dirección del archivo dot
            String fileInputPath = "src/main/java/Reportes/ARBOLES_202111478/" + titulo + ".dot";
            //dirección donde se creara la magen
            String fileOutputPath = "src/main/java/Reportes/ARBOLES_202111478/" + titulo + ".jpg";
            //tipo de conversón
            String tParam = "-Tjpg";
            String tOParam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec(cmd);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }
    private void crearGraficaAFND(String titulo, String grafica){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("src/main/java/Reportes/AFND_202111478/" + titulo + ".dot");
            pw = new PrintWriter(fichero);
            pw.println(grafica);
        } catch (Exception e) {
            System.out.println("error, no se realizo el archivo"+e);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                    System.out.println("AUTOMATA FINITO NO DETERMINISTA GENERADO CORRECTAMENTE");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //para compilar el archivo dot y obtener la imagen
        try {
            //"C:\\Program Files\\Graphviz\\bin\\dot.exe"
            //dirección doonde se ecnuentra el compilador de graphviz
            String dotPath = "C:\\Program Files\\Graphviz\\bin\\dot.exe";
            //dirección del archivo dot
            String fileInputPath = "src/main/java/Reportes/AFND_202111478/" + titulo + ".dot";
            //dirección donde se creara la magen
            String fileOutputPath = "src/main/java/Reportes/AFND_202111478/" + titulo + ".jpg";
            //tipo de conversón
            String tParam = "-Tjpg";
            String tOParam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec(cmd);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }

}
