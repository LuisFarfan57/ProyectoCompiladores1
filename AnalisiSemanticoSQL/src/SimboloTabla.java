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
    String nombre; //Identificador de la variable
    String valor; //Valor de la variable
    String tipo; //Tipo de variable: int, String, etc
    int ambito; //Número de ambito al que pertenece, si el ambito es 0, significa que el ámbito ya se cerró y por lo tanto ya no se puede
                //acceder a la variable
    
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
    
    int getAmbito(){
        return ambito;
    }
    
    void setAmbito(int ambito){
        this.ambito = ambito;
    }
}
