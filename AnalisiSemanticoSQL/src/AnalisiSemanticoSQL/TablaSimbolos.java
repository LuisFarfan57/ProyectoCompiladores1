/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalisiSemanticoSQL;

import java.util.ArrayList;

/**
 *
 * @author luise
 */
public class TablaSimbolos {
    private static ArrayList<SimboloTabla> tablaSimbolos = new ArrayList<SimboloTabla>(); 
    private static int ambitoActual;   
    private static String nombreAmbito;
    public static ArrayList<SimboloTabla> listaTemporal = new ArrayList<>();
    
    public static ArrayList<SimboloTabla> getTablaSimbolos(){
        return tablaSimbolos;
    }
    
    public static void add(SimboloTabla sim){
        tablaSimbolos.add(sim);
    }
    
    public static int getAmbitoActual(){
        return ambitoActual;
    }
    
    public static void cerrarAmbitoActual(){
        if(ambitoActual != 0){
            for (int i = 0; i < tablaSimbolos.size(); i++) {
                if(tablaSimbolos.get(i).getAmbito() == ambitoActual){
                    tablaSimbolos.get(i).setAmbito(0);
                }
            }
                
            ambitoActual--;        
            nombreAmbito = "";
        }        
    }
    
    public static void nuevoAmbito(String nombre){
        ambitoActual++;
        nombreAmbito = nombre;
    }
    
    public static void nuevoAmbito(){
        ambitoActual++;
    }
    
    public static Boolean buscarIdentificador(String identificador, String tipoDeclaracion){
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            if(tablaSimbolos.get(i).getNombre().equals(identificador) && tablaSimbolos.get(i).getAmbito() == ambitoActual && tablaSimbolos.get(i).getTipoDeclaracion().equals(tipoDeclaracion))
                return true;
        }
        
        return false;
    }
    
    public static void actualizarTipoValorSimbolo(String nombre, int ambito, String tipo, String valor){
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            if(tablaSimbolos.get(i).getNombre().equals(nombre) && tablaSimbolos.get(i).getAmbito() == ambito){
                tablaSimbolos.get(i).setTipo(tipo);
                tablaSimbolos.get(i).setValor(valor);
                break;
            }
        }
    }
    
    public static void actualizarValorSimbolo(String nombre, int ambito, String valor){
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            if(tablaSimbolos.get(i).getNombre().equals(nombre) && tablaSimbolos.get(i).getAmbito() == ambito){                
                tablaSimbolos.get(i).setValor(valor);
                break;
            }            
        }
    }
    
}
