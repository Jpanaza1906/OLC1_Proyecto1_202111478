package com.mycompany.proyecto1;

public class transicion {

    public String initialState;
    public String transition;
    public String finalState;
    
    public transicion( String initialState, String transition, String finalState ) {
        this.initialState = initialState;
        this.transition = transition;
        this.finalState = finalState;
    }
    
    public boolean compare(String initialState, String transition){
        
        return this.initialState.equals(initialState) && this.transition.equals(transition);
    }
    
    @Override
    public String toString(){
        return this.initialState + " -> " + this.transition + " -> " + this.finalState;
    }
    
    public String graph(){
        var letra = this.transition;
        var arrayletra = letra.toCharArray();
        if(arrayletra[0] == '\"'){
            return this.initialState +  "->"  + this.finalState + "[label=\"\\" + arrayletra[0] + arrayletra[1] + "\\" + arrayletra[2] + "\"]";
        }
        else if(arrayletra[0] == '\\'){
            return this.initialState +  "->"  + this.finalState + "[label=\"\\" + this.transition + "\"]";
        }
        else{
            return this.initialState +  "->"  + this.finalState + "[label=\"" + this.transition + "\"]";
        }
    }
}
