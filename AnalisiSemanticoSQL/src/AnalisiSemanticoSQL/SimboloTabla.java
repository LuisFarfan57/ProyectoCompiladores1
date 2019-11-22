package AnalisiSemanticoSQL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luise
 */
public class SimboloTabla {
    private String nombre; //Identificador de la variable
    private String valor; //Valor de la variable
    private String tipo; //Tipo de variable: int, String, etc
    private String tipoDeclaracion; //Si es declarado como funcion, variable, procedimiento, tabla, etc.
    private int ambito; //Número de ambito al que pertenece, si el ambito es 0, significa que el ámbito ya se cerró y por lo tanto ya no se puede
                //acceder a la variable    
    
    public String Tabla;
    public String BaseDeDatos;
    
    
    public boolean yaExiste;
    
    public boolean multiplicacion;
    public boolean suma;
    
    SimboloTabla(){        
        valor = "";        
        tipo = "";
        tipoDeclaracion = "";
        ambito = 0;
        nombre = "";        
    }       
    
    public SimboloTabla getSimbolo(){
        return this;
    }
    
    String getNombre(){
        return nombre;
    }
    
    void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    String getValor(){
        return valor;
    }
    
    void setValor(String valor){
        this.valor = valor;
    }
    
    String getTipo(){
        return tipo;
    }
    
    void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    String getTipoDeclaracion(){
        return tipoDeclaracion;
    }
    
    void setTipoDeclaracion(String tipoDeclaracion){
        this.tipoDeclaracion = tipoDeclaracion;
    }
    
    int getAmbito(){
        return ambito;
    }
    
    void setAmbito(int ambito){
        this.ambito = ambito;
    }        
}
