/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladoresf1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author luise
 */
public class Escritor {
    public static boolean Escribir(String strPath,String strContenido)
    {        
        try
        {
            File Archivo = new File(strPath);        
            
            if(!Archivo.exists()){
                Archivo.createNewFile();
            }
            else{                
                Archivo.delete();
                Archivo.createNewFile();
            }
            
            FileWriter Escribir = new FileWriter(Archivo,true);
            BufferedWriter bw = new BufferedWriter(Escribir);
            
            bw.write(strContenido);                
                                            
            bw.close();
            Escribir.close();
                
            return true;
        }
        catch(IOException ex)
        {
            return false;
        }         
    }
    
}