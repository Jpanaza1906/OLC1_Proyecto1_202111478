
/* 1. Package e importaciones */
package Analizadores;
import java_cup.runtime.Symbol;

%%
/* 2. Configuraciones para el analisis (Opciones y Declaraciones) */
%{
    //Codigo de usuario en sintaxis java
    //Agregar clases, variables, arreglos, objetos etc...
%}

//Directivas
%class Lexico
%public 
%cup
%char
%column
%full
%line
%unicode

//Inicializar el contador de columna y fila con 1
%init{ 
    yyline = 1; 
    yychar = 1; 
%init}

//Expresiones regulares

//ER dinamicas

//ER estaticas
BLANCOS = [ \r\t]+
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
comentariosimple = "//" {InputCharacter}* {LineTerminator}
multicomentario = "<!" [^"!>"]* "!>"
titulo = [[A-Z]|[a-z]|[0-9]]+
rangomayus = [A-Z] {BLANCOS}* ("~"|"-") {BLANCOS}* [A-Z]
rangominus = [a-z] {BLANCOS}* ("~"|"-") {BLANCOS}* [a-z]
sletras = (([A-Z]|[a-z]){BLANCOS}*(","){BLANCOS}*)(([A-Z]|[a-z]){BLANCOS}*(",")?{BLANCOS}*)*
snums = ([0-9]{BLANCOS}*(","){BLANCOS}*)([0-9]{BLANCOS}*(",")?{BLANCOS}*)*
rangonum = [0-9]{BLANCOS}* ("~"|"-"){BLANCOS}* [0-9]
char = ("\""|"#"|"$"|"%"|"&"|"'"|"("|")"|"*"|"+"|","|"-"|"."|"/"|":"|"="|"?"|"@"|"["|"\\"|"]"|"^"|"_"|"`"|"{"|"|"|"}"|"~"|"!"|"<")
rangochar = {char}{BLANCOS}* ("~"|"-") {BLANCOS}*{char}
schar = {char}{BLANCOS}*(","){BLANCOS}*({char}(",")?{BLANCOS}*)*

%%
/* 3. Reglas Semanticas */
//Palabras y simbolos reservados
"CONJ" { System.out.println("Reconocio \""+yytext()+"\" : nuevo conjunto"); return new Symbol(sym.CONJUNTO,yyline,yychar,yytext());}
"-" { System.out.println("Reconocio \""+yytext()+"\" : guion"); return new Symbol(sym.GUION,yyline,yychar,yytext());} 
"~" { System.out.println("Reconocio \""+yytext()+"\" : rango letras"); return new Symbol(sym.RANGO,yyline,yychar,yytext());} 
">" { System.out.println("Reconocio \""+yytext()+"\" : mayor que"); return new Symbol(sym.MAYORQ,yyline,yychar,yytext());} 
";" { System.out.println("Reconocio \""+yytext()+"\" : punto y coma"); return new Symbol(sym.PTCOMA,yyline,yychar, yytext());} 
":" { System.out.println("Reconocio \""+yytext()+"\" : dos puntos"); return new Symbol(sym.DOSPT,yyline,yychar, yytext());} 
"," { System.out.println("Reconocio \""+yytext()+"\" : coma"); return new Symbol(sym.COMA,yyline,yychar, yytext());} 
"(" { System.out.println("Reconocio \""+yytext()+"\" : parentesis abre"); return new Symbol(sym.PAR_A,yyline,yychar, yytext());} 
")" { System.out.println("Reconocio \""+yytext()+"\" : parentesis cierra"); return new Symbol(sym.PAR_C,yyline,yychar, yytext());} 
"[" { System.out.println("Reconocio \""+yytext()+"\" : corchete abre"); return new Symbol(sym.COR_A,yyline,yychar, yytext());} 
"]" { System.out.println("Reconocio \""+yytext()+"\" : corchete cierra"); return new Symbol(sym.COR_C,yyline,yychar, yytext());} 
"{" { System.out.println("Reconocio \""+yytext()+"\" : llave abre"); return new Symbol(sym.LLAVE_A,yyline,yychar, yytext());} 
"}" { System.out.println("Reconocio \""+yytext()+"\" : llave cierra"); return new Symbol(sym.LLAVE_C,yyline,yychar, yytext());} 
"%" { System.out.println("Reconocio \""+yytext()+"\" : porcentaje"); return new Symbol(sym.PORCEN,yyline,yychar, yytext());}
"'" { System.out.println("Reconocio \""+yytext()+"\" : comilla simple"); return new Symbol(sym.COMILLA_S,yyline,yychar, yytext());} 
"\"" { System.out.println("Reconocio \""+yytext()+"\" : comilla doble"); return new Symbol(sym.COMILLA_D,yyline,yychar, yytext());}


// Operadores notacion
"." {System.out.println("Reconocio \""+yytext()+"\" : CONCATENACION"); return new Symbol(sym.CONCAT,yyline,yychar, yytext());} 
"|" {System.out.println("Reconocio \""+yytext()+"\" : DISYUNCION");return new Symbol(sym.OR,yyline,yychar, yytext());}
"*" {System.out.println("Reconocio \""+yytext()+"\" : 0 o mas veces");return new Symbol(sym.CERO_MAS,yyline,yychar, yytext());} 
"+" {System.out.println("Reconocio \""+yytext()+"\" : 1 o mas veces");return new Symbol(sym.UNO_MAS,yyline,yychar, yytext());} 
"?" {System.out.println("Reconocio \""+yytext()+"\" : 0 o una vez");return new Symbol(sym.CERO_UNO,yyline,yychar, yytext());} 

\n {yychar=1;}

//Expresiones definidas

{BLANCOS} {/*ignore*/} 
{comentariosimple} {/*ignore*/}
{multicomentario} {/*ignore*/}
{titulo} {System.out.println("Reconocio titulo: "+yytext()); return new Symbol(sym.TITULO,yyline,yychar, yytext());}
{rangomayus} {System.out.println("Reconocio rango letras mayusculas: "+yytext()); return new Symbol(sym.RANGO_MAYUS,yyline,yychar, yytext());}
{rangominus} {System.out.println("Reconocio rango letras minusculas: "+yytext()); return new Symbol(sym.RANGO_MINUS,yyline,yychar, yytext());}
{sletras} {System.out.println("Reconocio serie de letras: "+yytext()); return new Symbol(sym.SLETRAS,yyline,yychar, yytext());}
{snums} {System.out.println("Reconocio serie de numeros: "+yytext()); return new Symbol(sym.SNUMS,yyline,yychar, yytext());}
{rangonum} {System.out.println("Reconocio rango de numeros: "+yytext()); return new Symbol(sym.RANGO_NUM,yyline,yychar, yytext());}
{rangochar} {System.out.println("Reconocio rango de caracteres especiales: "+yytext()); return new Symbol(sym.RANGO_CHAR,yyline,yychar, yytext());}
{schar} {System.out.println("Reconocio serie de caracteres especiales: "+yytext()); return new Symbol(sym.SCHAR,yyline,yychar, yytext());}
{char} {System.out.println("Reconocio serie de caracter: "+yytext()); return new Symbol(sym.CHAR,yyline,yychar, yytext());}
. {
    //Aqui se debe guardar los valores (yytext(), yyline, yychar ) para posteriormente generar el reporte de errores Léxicos.
    System.out.println("Este es un error lexico: "+yytext()+ ", en la linea: "+yyline+", en la columna: "+yychar);
}
