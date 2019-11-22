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
    private static boolean error;
    private static String errores = "";
    public static boolean inicioTransaccion;      
    
    public static String BaseDeDatosActual;
    public static String TablaActual;
    
    public static String obtenerValorVariable(String identificador, String tipoDeclaracion){
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            if(tablaSimbolos.get(i).getNombre().equals(identificador) && tablaSimbolos.get(i).getAmbito() == ambitoActual && tablaSimbolos.get(i).getTipoDeclaracion().equals(tipoDeclaracion))
                return tablaSimbolos.get(i).getValor();
        }

        return "";
    }
    
    public static String obtenerTipoVariable(String identificador, String tipoDeclaracion){
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            if(tablaSimbolos.get(i).getNombre().equals(identificador) && tablaSimbolos.get(i).getAmbito() == ambitoActual && tablaSimbolos.get(i).getTipoDeclaracion().equals(tipoDeclaracion))
                return tablaSimbolos.get(i).getTipo();
        }

        return "";
    }
    
    public static void ERROR(String descripcion){
        setError(true);
        errores += descripcion + "\n";
    }
    
    public static String getErrores(){
        return errores;
    }
    
    public static boolean getError(){        
        return error;
    }
    
    public static void setError(boolean error2){
        error = error2;
    }
    
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
    
    public static Boolean buscarIdentificadorGeneral(String identificador, String tipoDeclaracion){
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            if(tablaSimbolos.get(i).getNombre().equals(identificador) && tablaSimbolos.get(i).getAmbito() != 0 && tablaSimbolos.get(i).getTipoDeclaracion().equals(tipoDeclaracion))
                return true;
        }
        
        return false;
    }
    
    public static Boolean buscarIdentificadorSinAmbito(String identificador, String tipoDeclaracion){
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            if(tablaSimbolos.get(i).getNombre().equals(identificador) && tablaSimbolos.get(i).getTipoDeclaracion().equals(tipoDeclaracion))
                return true;
        }
        
        return false;
    }

    public static void eliminarSimbolo(String identificador, String tipoDeclaracion){
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            if(tablaSimbolos.get(i).getNombre().equals(identificador) && tablaSimbolos.get(i).getTipoDeclaracion().equals(tipoDeclaracion))
                tablaSimbolos.remove(i);
        }
    }

    public static void eliminarElementosBase(String identificador){
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            if(tablaSimbolos.get(i).BaseDeDatos.equals(identificador))
                tablaSimbolos.remove(i);
        }
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
     public static String EscribirArchivo(){
            String texto="";
            for(int i=0;i<tablaSimbolos.size();i++){
                texto+=tablaSimbolos.get(i).getNombre()+","+tablaSimbolos.get(i).getTipo()+","+tablaSimbolos.get(i).getTipoDeclaracion()+","+tablaSimbolos.get(i).getValor()+","+tablaSimbolos.get(i).getAmbito();
            }
         return texto;   
    }
}
