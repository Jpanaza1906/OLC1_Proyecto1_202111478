package com.mycompany.proyecto1;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class followTable {
    public void append(int numNode, String lexeme, ArrayList flwList, ArrayList<ArrayList> table){
        for (ArrayList item : table){
            if( (int) item.get(0) == numNode && item.get(1) == lexeme ){
                for (Object flwItem : flwList){
                    if(! ((ArrayList)item.get(2)).contains((int)flwItem) ){
                       ((ArrayList)item.get(2)).add(flwItem);
                    }
                }
                return;
            }
        }
        ArrayList dato = new ArrayList();
        dato.add(numNode);
        dato.add(lexeme);
        dato.add(flwList);
        
        table.add(dato);
    }
    
    public ArrayList next(int numNode, ArrayList<ArrayList> table){
        ArrayList result = new ArrayList();
        for(ArrayList item : table){
            if( (int) item.get(0) == numNode ){
                result.add(item.get(1));
                result.add(((ArrayList)item.get(2)).clone());
                return result;
            }
        }
        result.add("");
        result.add(new ArrayList());
    return result;
    }
    
    public void printTable(ArrayList<ArrayList> table, String titulo){//TABLA SIGUIENTES
        String tabla = "digraph G { \n node[shape=none fontname=Helvetica]\n";
        tabla += "n1[label = < \n <table>";
        tabla += "<tr><td colspan=\"2\">Hojas</td><td>Siguientes</td></tr>\n";
        String[] sigs = new String[table.size()];
        for(ArrayList item : table){
            sigs[(int)item.get(0)] = "<tr><td>" + item.get(1) + "</td><td>" + item.get(0) + "</td><td>" + item.get(2) + "</td></tr>\n";            
        }
        for(int i = 0; i < sigs.length; i++){
            tabla += sigs[i];
        }
        tabla += "</table>\n>]\n}";
        crearTabla(titulo, tabla);
    }
    
    private void crearTabla(String titulo, String grafica){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("src/main/java/Reportes/SIGUIENTES_202111478/" + titulo + ".dot");
            pw = new PrintWriter(fichero);
            pw.println(grafica);
        } catch (Exception e) {
            System.out.println("error, no se realizo el archivo"+e);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();                    
                    System.out.println("TABLA DE SIGUIENTES GENERADOS CORRECTAMENTE");
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
            String fileInputPath = "src/main/java/Reportes/SIGUIENTES_202111478/" + titulo + ".dot";
            //dirección donde se creara la magen
            String fileOutputPath = "src/main/java/Reportes/SIGUIENTES_202111478/" + titulo + ".jpg";
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