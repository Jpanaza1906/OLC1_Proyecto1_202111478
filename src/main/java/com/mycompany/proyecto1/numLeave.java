package com.mycompany.proyecto1;

public final class numLeave {
    
    public int content;

    public numLeave(Integer content) {
        this.content = content;
    }
    
    public int getNum(){
        content -= 1;
        return content;
    }
    
    
    public int clean(String content){
        return content.replace(".", "").replace("|", "").replace("*", "").length();
    }
}