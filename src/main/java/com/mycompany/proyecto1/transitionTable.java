package com.mycompany.proyecto1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class transitionTable {

    public ArrayList<ArrayList> states;
    public int cont;
    
    public transitionTable(node root, ArrayList tabla, ArrayList<node> leaves) {
        this.states = new ArrayList();
        
        ArrayList datos = new ArrayList();
        datos.add("S0");
        datos.add(root.first);
        datos.add(new ArrayList());
        datos.add(false);
        
        this.states.add(datos);
        this.cont = 1;
        
        for(int i = 0; i < states.size(); i++){
            ArrayList state = states.get(i);
            ArrayList<Integer> elementos = (ArrayList) state.get(1);
            
            // TODO  Aqui se encuentra el bug sobre las AFD
            // Sucede cuando existe dos transisiciones con el mismo simbolos y diferentes AFD
            // Ej: S0 ={1,2,3}  se ve la tabla de siguiente y se tiene  (S0,a) = {1,2,3} y (S0,a) = {4} el nuevo estado quedaria de la union de estos dos S1 = {1,2,3,4}
            // Lo que hace ahora es remplazar el estado final entonces queda asi S1 = {4} y no S1 = {1,2,3,4}
            
            for(int hoja : elementos){
                followTable sigTabla = new followTable();
                ArrayList lexemeNext = (ArrayList) sigTabla.next(hoja, tabla).clone();
                
                
                boolean existe = false;
                String found = "";
                
                for( ArrayList e : states ){
                    if(e.get(1).equals(lexemeNext.get(1))){
                        existe = true;
                        found = (String)e.get(0);
                        break;
                    }
                }
                
                if(!existe){
                    leave hojas = new leave();
                    if(hojas.isAccept(hoja, leaves)){
                        state.set(3, true);
                    }
                    if(lexemeNext.get(0)==""){
                        continue;
                    }
                    
                    ArrayList nuevo = new ArrayList();
                    nuevo.add("S"+cont);
                    nuevo.add(lexemeNext.get(1));
                    nuevo.add(new ArrayList());
                    nuevo.add(false);
                    
                    transicion trans = new transicion(state.get(0) + "", lexemeNext.get(0) + "", nuevo.get(0) + "");
                    ((ArrayList)state.get(2)).add(trans);
                    
                    cont += 1;
                    states.add(nuevo);
                
                }
                else{
                    leave hojas = new leave();
                    if(hojas.isAccept(hoja, leaves)){
                        state.set(3, true);
                    }
                    
                    boolean trans_exist = false;
                    
                    for(Object trans : (ArrayList)state.get(2)){
                        transicion t = (transicion) trans;
                        if(t.compare(state.get(0) + "", lexemeNext.get(0) + "")){
                            trans_exist = true;
                            break;
                        }
                    }
                    if(!trans_exist){
                        transicion trans = new transicion(state.get(0) + "", lexemeNext.get(0) + "", found + "");
                        ((ArrayList)state.get(2)).add(trans);
                    }
                }
            }
            
        }
    }
    
    public void impTable(String titulo){//TABLA AFD
        /*
         * State[0] -> estado
         * State[1] -> [numeros]
         * State[2] -> [Sn -> lexema -> Sn] / [InitialState -> Transition -> FinalState]
         * State[3] -> true/false
         */
        ArrayList<String> terminales =new ArrayList();

        for(ArrayList state : states){ 

            for(Object tr : (ArrayList)state.get(2)){
                transicion t = (transicion) tr;
                if(terminales.size() == 0){
                    terminales.add(t.transition);
                }
                else{
                    boolean repe = false;
                    for (int i=0;i<terminales.size();i++){
                        if(t.transition.equals(terminales.get(i))){
                            repe = true;
                        }
                    }
                    if(!repe){                        
                        terminales.add(t.transition);
                    }
                }  
            }
        }         
        String tabla = "digraph G { \n node[shape=none fontname=Helvetica]\n";
        tabla += "n1[label = < \n <table>";     
        tabla += "<tr><td rowspan=\"2\">Estado</td><td colspan=\""+terminales.size()+"\">Terminales</td></tr>\n";
        tabla += "<tr>\n";
        for (int i=0;i<terminales.size();i++){
            tabla += "<td>" + terminales.get(i) + "</td>\n";
        }
        tabla += "</tr>\n";
        String[] trans = new String[terminales.size()];
        for(ArrayList state : states){
            for(int i= 0; i<trans.length;i++){
                trans[i] = "--";
            }
            tabla += "<tr>\n";            
            tabla += "<td>" + state.get(0) + " " + state.get(1) + "</td>\n";
            for(Object tr : (ArrayList)state.get(2)){
                transicion t = (transicion) tr;
                for (int i = 0; i< terminales.size();i++){
                    if(t.transition.equals(terminales.get(i))){
                        trans[i] = t.finalState;
                    }
                }
            }
            for(int i =0; i<trans.length;i++){
                tabla += "<td>" + trans[i] + "</td>\n";
            }
            tabla += "</tr>\n";
        }
        tabla += "</table>\n>]\n}";
        crearTabla(titulo, tabla);
    }
    
    public void impGraph(String titulo){//AFD
        String graph = "digraph G { \n rankdir=\"LR\"";
        for(ArrayList state : states){  
            if((boolean)state.get(3) == true){
                graph += state.get(0) + "[shape = doublecircle];" + "\n";
            }        
            for(Object tr : (ArrayList)state.get(2)){
                transicion t = (transicion) tr;
                graph += t.graph() + "\n";
            }
        }
        graph += "}";
        crearGrafica(titulo, graph);
    }

    private void crearTabla(String titulo, String grafica){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("src/main/java/Reportes/TRANSICIONES_202111478/" + titulo + ".dot");
            pw = new PrintWriter(fichero);
            pw.println(grafica);
        } catch (Exception e) {
            System.out.println("error, no se realizo el archivo"+e);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                    System.out.println("TABLA DE TRANSICIONES GENERADA CORRECTAMENTE");
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
            String fileInputPath = "src/main/java/Reportes/TRANSICIONES_202111478/" + titulo + ".dot";
            //dirección donde se creara la magen
            String fileOutputPath = "src/main/java/Reportes/TRANSICIONES_202111478/" + titulo + ".jpg";
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
    
    private void crearGrafica(String titulo, String grafica){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("src/main/java/Reportes/AFD_202111478/" + titulo + ".dot");
            pw = new PrintWriter(fichero);
            pw.println(grafica);
        } catch (Exception e) {
            System.out.println("error, no se realizo el archivo"+e);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();                    
                    System.out.println("AUTOMATAS FINITOS DETERMINISTAS GENERADOS CORRECTAMENTE");
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
            String fileInputPath = "src/main/java/Reportes/AFD_202111478/" + titulo + ".dot";
            //dirección donde se creara la magen
            String fileOutputPath = "src/main/java/Reportes/AFD_202111478/" + titulo + ".jpg";
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


