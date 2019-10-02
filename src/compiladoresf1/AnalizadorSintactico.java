/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladoresf1;

import java.util.ArrayList;

/**
 *
 * @author luise
 */

public class AnalizadorSintactico {    
    ArrayList<Tokens> tokens;
    ArrayList<String> lineas;
    int numeroToken;
    String errores;
    Tokens token;   
    
    public AnalizadorSintactico(ArrayList<Tokens> palabras, ArrayList<String> lineas){
        tokens = new ArrayList<Tokens>();
        this.lineas = new ArrayList<String>();
        
        numeroToken = -1;
        errores = "Descripción (Linea, columna inicial, columna final)\n";
        token = null;                 
        for (int i = 0; i < palabras.size(); i++) {
            this.tokens.add((palabras.get(i)));
            this.lineas.add(lineas.get(i));
        }
    }
    
    public String analizar(){
        siguienteToken();
        
        while(numeroToken != tokens.size()){
            switch (token){
                case INSERT:
                    Insert();
                    break;
                case DELETE:
                    Delete();
                    break;
                case UPDATE:
                    Update();
                    break;
                case SELECT: AbrirParentesis:
                    Select();
                    break;
                case CREATE:
                    Create();
                    break;
                case ALTER:
                    Alter();
                    break;
                case DROP:
                    Drop();
                    break;                    
                case TRUNCATE:
                    Truncate();
                    break;                
                default:
                    ErrorSintactico(Tokens.INSERT.toString()
                    + Tokens.DELETE.toString() + " 0 "
                    + Tokens.UPDATE.toString() + " 0 "
                    + Tokens.SELECT.toString() + " 0 "
                    + Tokens.CREATE.toString() + " 0 "
                    + Tokens.ALTER.toString() + " 0 "
                    + Tokens.DROP.toString() + " 0 "
                    + Tokens.TRUNCATE.toString());
                    break;
            }
        }        
            
        if(errores == "Descripción (Linea, columna inicial, columna final)\n"){
            errores += "Análisis sintáctico correcto";
        }
        return errores;
    }      
    
    void siguienteToken(){
        numeroToken++;
        if(numeroToken != tokens.size())
            token = tokens.get(numeroToken);
    }
    
    int buscarFinExpresion(){
        int fin = numeroToken;
        for (int i = numeroToken + 1; i < tokens.size(); i++) {            
            if(tokens.get(i) == Tokens.GO | tokens.get(i) == Tokens.PuntoComa){
                fin = i;
                break;
            }
        }               
        
        return fin;
    }
    
    boolean verificarToken(Tokens tokenEsperado){
        if(tokenEsperado == token){
            siguienteToken();            
            return true;
        }
        else{                                    
            ErrorSintactico(tokenEsperado.toString());
            return false;
        }
    }
    
    void ErrorSintactico(String tokenEsperado){
        if(numeroToken != tokens.size()){
            errores += "Error: se esperaba " + tokenEsperado + " se encontró " + token.toString() + " " + lineas.get(numeroToken) + "\n";
            numeroToken = buscarFinExpresion();  
            siguienteToken();
        }                        
        else
            errores += "Error: se esperaba " + tokenEsperado + " se encontró Fin de archivo\n";                
    }
    
    boolean fin_state(){
        if(token == Tokens.GO){
            if(!verificarToken(Tokens.GO))
                return false;            
        }
        else if(token == Tokens.PuntoComa){
            if(!verificarToken(Tokens.PuntoComa))
                return false;
        }
        else{
            ErrorSintactico(Tokens.PuntoComa.toString() + " o " + Tokens.GO.toString());
            return false;
        }
        
        return true;
    }
    
    boolean AscDesc(){
        if(token == Tokens.ASC){
            if(!verificarToken(Tokens.ASC))
                return false;
        }
        else if(token == Tokens.DESC){
            if(!verificarToken(Tokens.DESC))
                return false;
        }
        
        return true;
    }
    
    boolean Constraint(){
        if(token == Tokens.CONSTRAINT){
            if(!verificarToken(Tokens.CONSTRAINT))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!ConstraintOpciones())
                return false;            
        }
        else{
            ErrorSintactico(Tokens.CONSTRAINT.toString());
            return false;
        }
        return true;
    }
    
    boolean ConstraintOpciones(){
        if(token == Tokens.PRIMARY){
            if(!verificarToken(Tokens.PRIMARY))
                return false;
            if(!verificarToken(Tokens.KEY))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Constraint1())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else if(token == Tokens.CHECK){
            if(!verificarToken(Tokens.CHECK))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Predicado())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else if(token == Tokens.FOREIGN){
            if(!verificarToken(Tokens.FOREIGN))
                return false;
            if(!verificarToken(Tokens.KEY))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!ColumnRef())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
            if(!verificarToken(Tokens.REFERENCES))
                return false;
            if(!TableRef())
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!ColumnRef())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;            
        }
        else{
            ErrorSintactico(Tokens.PRIMARY.toString() + " o " +
                    Tokens.CHECK.toString() + " o " +
                    Tokens.FOREIGN.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Constraint1(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!AscDesc())
                return false;
            if(!Constraint3())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        return true;
    }
    
    boolean Constraint3(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Constraint1())
                return false;
        }
        
        return true;
    }
    
    boolean Not(){
        if(token == Tokens.NOT){
            if(!verificarToken(Tokens.NOT))
                return false;
        }
        return true;
    }
    
    boolean DataType(){
        if(token == Tokens.INT){
            if(!verificarToken(Tokens.INT))
                return false;
        }
        else if(token == Tokens.FLOAT){
            if(!verificarToken(Tokens.FLOAT))
                return false;
        }
        else if(token == Tokens.BIT){
            if(!verificarToken(Tokens.BIT))
                return false;
        }
        else if(token == Tokens.VARCHAR){
            if(!Varchar())
                return false;
        }
        else{
            ErrorSintactico(Tokens.INT.toString() + " O " +
                    Tokens.FLOAT.toString() + " O " +
                    Tokens.BIT.toString() + " O " +
                    Tokens.VARCHAR.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Varchar(){
        if(token == Tokens.VARCHAR){
            if(!verificarToken(Tokens.VARCHAR))
                return false;
            if(!VarOp())
                return false;
        }
        else{
            ErrorSintactico(Tokens.VARCHAR.toString());
            return false;
        }
            
        return true;
    }
    
    boolean VarOp(){
        if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!verificarToken(Tokens.Numero))
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        return true;
    }        
    
    boolean ColumnRef(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            ColumnRef1();                
        }        
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        return true;
    }
    
    boolean ColumnRef1(){
        if(token == Tokens.Punto){
            if(!verificarToken(Tokens.Punto))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
        }        
        
        return true;
    }
    
    boolean ColumnDef(){
        if(token == Tokens.Identificador){
            if(!ColumnRef())
                return false;
            if(!DataType())
                return false;
            if(!ColumnDef1())
                return false;
            if(!ColumnDef3())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }
    
    boolean ColumnDef1(){
        if(token == Tokens.NOT || token == Tokens.NULL){
            if(!Not())
                return false;
            if(!verificarToken(Tokens.NULL))
                return false;                
        }
        
        return true;
    }        
    
    boolean ColumnDef3(){
        if(token == Tokens.PRIMARY){
            if(!verificarToken(Tokens.PRIMARY))
                return false;
            if(!verificarToken(Tokens.KEY))
                return false;
            if(!AscDesc())
                return false;
        }
        
        return true;
    }
    
    boolean TableRef(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!TableRef1())
                return false;
            if(!TableRef1())
                return false;
        }        
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }
    
    boolean TableRef1(){
        if(token == Tokens.Punto){
            if(!verificarToken(Tokens.Punto))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
        }        
        
        return true;
    }        
    
    boolean IfExists(){
        if(token == Tokens.IF){
            if(!verificarToken(Tokens.IF))
                return false;
            if(!verificarToken(Tokens.EXISTS))
                return false;
        }
        
        return true;
    }
    
    boolean Value(){
        if(token == Tokens.Numero){
            if(!verificarToken(Tokens.Numero))
                return false;
        }
        else if(token == Tokens.Float){
            if(!verificarToken(Tokens.Float))
                return false;
        }
        else if(token == Tokens.Bit){
            if(!verificarToken(Tokens.Bit))
                return false;
        }        
        else{
            ErrorSintactico(Tokens.Numero.toString() + " o " + 
                    Tokens.Float.toString() + " o " +
                    Tokens.Bit.toString());                    
            return false;
        }
        
        return true;
    }
    
    boolean FAvg(){
        if(token == Tokens.AVG){
            if(!verificarToken(Tokens.AVG))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!ConstantTerm())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.AVG.toString());
            return false;
        }
        
        return true;
    }
    
    boolean FCount(){
        if(token == Tokens.COUNT){
            if(!verificarToken(Tokens.COUNT))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!FCount1())
                return false;
            if(!FCount2())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.COUNT.toString());
            return false;
        }       
        
        return true;
    }
    
    boolean FCount1(){
        if(token == Tokens.DISTINCT){
            if(!verificarToken(Tokens.DISTINCT))
                return false;
        }
        
        return true;
    }
    
    boolean FCount2(){
        if(token == Tokens.ClausulaKleant){
            if(!verificarToken(Tokens.ClausulaKleant))
                return false;
        }
        else if(token == Tokens.Multiplicacion){
            if(!verificarToken(Tokens.Multiplicacion))
                return false;
        }
        else if(token == Tokens.Numero || token == Tokens.Float || 
                token == Tokens.Bit || 
                token == Tokens.AVG || token == Tokens.COUNT || 
                token == Tokens.MAX || token == Tokens.MIN || 
                token == Tokens.SUM || 
                token == Tokens.AbrirParentesis || 
                token == Tokens.Identificador){
            if(!ConstantTerm())
                return false;
        }
        else{
            ErrorSintactico(Tokens.ClausulaKleant.toString() + " o " +
            Tokens.Multiplicacion.toString() + " 0 " +
                    Tokens.Numero.toString() + " 0 " +
                    Tokens.Float.toString() + " 0 " +
                    Tokens.Bit.toString() + " 0 " +                    
                    Tokens.AVG.toString() + " 0 " +
                    Tokens.COUNT.toString() + " 0 " +
                    Tokens.MAX.toString() + " 0 " +
                    Tokens.MIN.toString() + " 0 " +
                    Tokens.SUM.toString() + " 0 " +
                    Tokens.AbrirParentesis.toString() + " 0 " +
                    Tokens.Identificador.toString());
            return false;
        }
            
        return true;
    }
    
    boolean FMax(){
        if(token == Tokens.MAX){
            if(!verificarToken(Tokens.MAX))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!ConstantTerm())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.MAX.toString());
        }
        
        return true;
    }
    
    boolean FMin(){
        if(token == Tokens.MIN){
            if(!verificarToken(Tokens.MIN))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!ConstantTerm())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.MIN.toString());
        }
        
        return true;
    }
    
    boolean FSum(){
        if(token == Tokens.SUM){
            if(!verificarToken(Tokens.SUM))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!ConstantTerm())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.SUM.toString());
        }
        
        return true;
    }
    
    boolean Function(){
        if(token == Tokens.AVG){
            if(!FAvg())
                return false;
        }
        else if(token == Tokens.COUNT){
            if(!FCount())
                return false;
        }
        else if(token == Tokens.MAX){
            if(!FMax())
                return false;
        }
        else if(token == Tokens.MIN){
            if(!FMin())
                return false;
        }
        else if(token == Tokens.SUM){
            if(!FSum())
                return false;
        }
        else{
            ErrorSintactico(Tokens.AVG.toString() + " 0 " +
                    Tokens.COUNT.toString() + " 0 " +
                    Tokens.MAX.toString() + " 0 " +
                    Tokens.MIN.toString() + " 0 " +
                    Tokens.SUM.toString());
            return false;
        }
                
        return true;
    }
    
    boolean Const(){
        if(token == Tokens.Numero || token == Tokens.Float || 
                token == Tokens.Bit || 
                token == Tokens.AVG || token == Tokens.COUNT || 
                token == Tokens.MAX || token == Tokens.MIN || 
                token == Tokens.SUM || token == Tokens.CASE ||
                token == Tokens.AbrirParentesis || 
                token == Tokens.Identificador){
            if(!ConstantTerm())
                return false;
        }
        
        return true;
    }
    
    boolean Case1(){
        if(token == Tokens.WHEN){
            if(!verificarToken(Tokens.WHEN))
                return false;
            if(!Expression())
                return false;
            if(!verificarToken(Tokens.THEN))
                return false;
            if(!ConstantTerm())
                return false;
            if(!Case1())
                return false;
        }
        
        return true;
    }
    
    boolean Case2(){
        if(token == Tokens.ELSE){
            if(!verificarToken(Tokens.ELSE))
                return false;
            if(!Expression())
                return false;
        }
        
        return true;
    }
    
    boolean Case(){
        if(token == Tokens.CASE){
            if(!verificarToken(Tokens.CASE))
                return false;
            if(!Const())
                return false;
            if(!Case1())
                return false;
            if(!Case2())
                return false;
            if(!verificarToken(Tokens.END))
                return false;
        }
        else{
            ErrorSintactico(Tokens.CASE.toString());
        }
        
        return true;
    }        
    
    boolean ConstantTerm(){
        if(token == Tokens.AbrirParentesis || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit || token == Tokens.Identificador){                            
            if(!Expression())
                return false;            
        }
        else if(token == Tokens.AVG || token == Tokens.COUNT || 
                token == Tokens.MAX || token == Tokens.MIN || token == Tokens.SUM){
            if(!Function())
                return false;
        }
        else if(token == Tokens.CASE){
            if(!Case())
                return false;
        }                
        else{
            ErrorSintactico(Tokens.Numero.toString() + " 0 " +
                    Tokens.Float.toString() + " 0 " +
                    Tokens.Bit.toString() + " 0 " +                    
                    Tokens.AVG.toString() + " 0 " +
                    Tokens.COUNT.toString() + " 0 " +
                    Tokens.MAX.toString() + " 0 " +
                    Tokens.MIN.toString() + " 0 " +
                    Tokens.SUM.toString() + " 0 " +
                    Tokens.CASE.toString() + " o " +
                    Tokens.AbrirParentesis.toString() + " 0 " +
                    Tokens.Identificador.toString());   
            return false;
        }
        
        return true;
    }          
    
    boolean Compare(){
        if(token == Tokens.MenorIgual){
            if(!verificarToken(Tokens.MenorIgual))
                return false;
        }
        else if(token == Tokens.MayorIgual){
            if(!verificarToken(Tokens.MayorIgual))
                return false;
        }
        else if(token == Tokens.MenorQue){
            if(!verificarToken(Tokens.MenorQue))
                return false;
        }
        else if(token == Tokens.MayorQue){
            if(!verificarToken(Tokens.MayorQue))
                return false;
        }
        else if(token == Tokens.Igual){
            if(!verificarToken(Tokens.Igual))
                return false;
        }
        else if(token == Tokens.DiferenteDe){
            if(!verificarToken(Tokens.DiferenteDe))
                return false;
        }
        else{
            ErrorSintactico(Tokens.MenorIgual.toString() + " o " +
                    Tokens.MayorIgual.toString() + " o " +
                    Tokens.MenorQue.toString() + " o " +
                    Tokens.MayorQue.toString() + " o " +
                    Tokens.Igual.toString() + " o " +
                    Tokens.DiferenteDe.toString());
        }
                
        
        return true;
    }      
    
    boolean TExpression(){
        if(token == Tokens.AbrirParentesis || token == Tokens.Identificador
                || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit){
            if(!FExpression())
                return false;
            if(!Expression3())
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " O " +
                    Tokens.Numero.toString() + " O " +
                    Tokens.Float.toString() + " O " +
                    Tokens.Bit.toString() + " O " +                    
                    Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }
    
    boolean FExpression(){
        if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Expression())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else if(token == Tokens.Identificador || token == Tokens.Numero
                || token == Tokens.Float || token == Tokens.Bit){
            if(!ExpressionOption())
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " O " +
                    Tokens.Numero.toString() + " O " +
                    Tokens.Float.toString() + " O " +
                    Tokens.Bit.toString() + " O " +                    
                    Tokens.Identificador.toString());
            return false;
        }
            
        return true;
    }
    
    boolean Expression2(){
        if(token == Tokens.Suma){
            if(!verificarToken(Tokens.Suma))
                return false;
            if(!TExpression())
                return false;
        }
        else if(token == Tokens.Resta){
            if(!verificarToken(Tokens.Resta))
                return false;
            if(!TExpression())
                return false;
        }
        
        return true;
    }
    
    boolean Expression3(){
        if(token == Tokens.Multiplicacion || token == Tokens.ClausulaKleant){
            if(!verificarToken(token))
                return false;
            if(!FExpression())
                return false;
        }
        else if(token == Tokens.Division){
            if(!verificarToken(Tokens.Division))
                return false;
            if(!FExpression())
                return false;
        }
        
        return true;
    }
    
    boolean ExpressionOption(){
        if(token == Tokens.Identificador){
            if(!TableRef())
                return false;
        }
        else if(token == Tokens.Numero || token == Tokens.Float || 
                token == Tokens.Bit){
            if(!Value())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Numero.toString() + " O " +
                    Tokens.Float.toString() + " O " +
                    Tokens.Bit.toString() + " O " +                    
                    Tokens.Identificador.toString());
        }
        
        return true;
    }
    
    boolean Expression(){
        if(token == Tokens.AbrirParentesis || token == Tokens.Identificador
                || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit){
            if(!TExpression())
                return false;
            if(!Expression2())
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " O " +
                    Tokens.Numero.toString() + " O " +
                    Tokens.Float.toString() + " O " +
                    Tokens.Bit.toString() + " O " +                    
                    Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }        
    
    boolean Predicado(){
        if(token == Tokens.AbrirParentesis || token == Tokens.Identificador
                || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit){
            if(!Expression())
                return false;
            if(!StateLogic())
                return false;
        }
        else if(token == Tokens.Cadena){
            if(!verificarToken(Tokens.Cadena))
                return false;
            if(!Not())
                return false;
            if(!verificarToken(Tokens.LIKE))
                return false;
            if(!verificarToken(Tokens.Cadena))
                return false;
            if(!Escape_exp())
                return false;
        }
        else if(token == Tokens.CONTAINS){
            if(!verificarToken(Tokens.CONTAINS))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Cont_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else if(token == Tokens.FREETEXT){
            if(!verificarToken(Tokens.FREETEXT))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Cont_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.Numero.toString() + " o " +
                    Tokens.Float.toString() + " o " +
                    Tokens.Bit.toString() + " o " +
                    Tokens.Identificador.toString() + " o " +
                    Tokens.Cadena.toString() + " o " +
                    Tokens.CONTAINS.toString() + " o " +
                    Tokens.FREETEXT.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Cont_exp(){
        if(token == Tokens.Identificador){
            if(!TableRef())
                return false;
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!verificarToken(Tokens.Cadena))
                return false;
        }
        else if(token == Tokens.ClausulaKleant || token == Tokens.Multiplicacion){
            if(!verificarToken(token))
                return false;
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!verificarToken(Tokens.Cadena))
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString() + " o " +
                    Tokens.Multiplicacion.toString() + " o " +
                    Tokens.ClausulaKleant.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Escape_exp(){
        if(token == Tokens.ESCAPE){
            if(!verificarToken(Tokens.ESCAPE))
                return false;
            if(!verificarToken(Tokens.Cadena))
                return false;
        }
        
        return true;
    }
    
    boolean StateLogic(){
        if(token == Tokens.MayorIgual || token == Tokens.MenorIgual
                || token == Tokens.MayorQue || token == Tokens.MenorQue
                || token == Tokens.Igual || token == Tokens.DiferenteDe){
            if(!Compare())
                return false;
            if(!Expression())
                return false;
        }
        else if(token == Tokens.IS){
            if(!verificarToken(Tokens.IS))
                return false;
            if(!Not())
                return false;
            if(!verificarToken(Tokens.NULL))
                return false;
        }
        else if(token == Tokens.NOT){
            if(!Not())
                return false;            
            if(!State1())
                return false;
        }
        else if(token == Tokens.IN || token == Tokens.BETWEEN){
            if(!State1())
                return false;
        }
        else{
            ErrorSintactico(Tokens.MayorIgual.toString() + " o " +
                    Tokens.MenorIgual.toString() + " o " +
                    Tokens.MayorQue.toString() + " o " +
                    Tokens.MenorQue.toString() + " o " +
                    Tokens.Igual.toString() + " o " +
                    Tokens.DiferenteDe.toString() + " o " +
                    Tokens.IS.toString() + " o " +
                    Tokens.IN.toString() + " o " +
                    Tokens.BETWEEN.toString() + " o " +
                    Tokens.NOT.toString());
            return false;
        }
        
        return true;
    }
    
    boolean State1(){
        if(token == Tokens.IN){
            if(!verificarToken(Tokens.IN))
                return false;
            if(!Expression())
                return false;
        }
        else if(token == Tokens.BETWEEN){
            if(!verificarToken(Tokens.BETWEEN))
                return false;
            if(!Expression())
                return false;
            if(!verificarToken(Tokens.AND))
                return false;
            if(!Expression())
                return false;
        }
        else{
            ErrorSintactico(Tokens.IN.toString() +  " o " +
                    Tokens.BETWEEN.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Search_condition(){
        if(token == Tokens.NOT || token == Tokens.AbrirParentesis || token == Tokens.Identificador
                || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit
                || token == Tokens.Cadena || token == Tokens.FREETEXT || token == Tokens.CONTAINS){
            if(!Not())
                return false;
            if(!Predicado())
                return false;
            if(!OtroSearch())
                return false;
        }
        else{
            ErrorSintactico(Tokens.NOT.toString() + " o " +
                    Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.Identificador.toString() + " o " +
                    Tokens.Numero.toString() + " o " +
                    Tokens.Float.toString() + " o " +
                    Tokens.Bit.toString() + " o " +
                    Tokens.Cadena.toString() + " o " +
                    Tokens.FREETEXT.toString() + " o " +
                    Tokens.CONTAINS.toString());
            
            return false;
        }
        
        return true;
    }
    
    boolean OtroSearch(){
        if(token == Tokens.AND || token == Tokens.OR || token == Tokens.AbrirParentesis || token == Tokens.Identificador
                || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit
                || token == Tokens.Cadena || token == Tokens.FREETEXT || token == Tokens.CONTAINS){
            if(!Logic_word())
                return false;
            if(!Search_condition())
                return false;
        }
        
        return true;
    }
    
    boolean Logic_word(){
        if(token == Tokens.AND){
            if(!verificarToken(Tokens.AND))
                return false;
        }
        else if(token == Tokens.OR){
            if(!verificarToken(Tokens.OR))
                return false;
        }
        
        return true;
    }
    
    boolean Result_exp(){
        if(token == Tokens.AbrirParentesis || token == Tokens.Identificador
                || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit){
            if(!Expression())
                return false;
        }
        else if(token == Tokens.Cadena){
            if(!verificarToken(Tokens.Cadena))
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.Identificador.toString() + " o " +
                    Tokens.Numero.toString() + " o " +
                    Tokens.Float.toString() + " o " +
                    Tokens.Bit.toString() + " o " +
                    Tokens.Cadena.toString());
        }
        
        return true;
    }
    
    boolean TopExpression(){
        if(token == Tokens.TOP){
            if(!verificarToken(Tokens.TOP))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Expression())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
            if(!Top1())
                return false;
        }        
        
        return true;
    }
    
    boolean Top1(){
        if(token == Tokens.PERCENT){
            if(!verificarToken(Tokens.PERCENT))
                return false;
        }
                    
        return true;
    }
    
    boolean WhereExpression(){
        if(token == Tokens.WHERE){
            if(!verificarToken(Tokens.WHERE))
                return false;
            if(!Search_condition())
                return false;
        }
        else{
            ErrorSintactico(Tokens.WHERE.toString());
            return false;
        }
        return true;
    }
    
    //Insert
    boolean Insert(){       
        if(token == Tokens.INSERT){
            if(!verificarToken(Tokens.INSERT))
                return false;
            if(!Into())
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!Insert1())
                return false;
            if(!verificarToken(Tokens.VALUES))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Insert4())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
            if(!fin_state())
                return false;
        }
        else{
            ErrorSintactico(Tokens.INSERT.toString());
            return false;
        }
            
        return true;
    }
    
    boolean Into(){
        if(token == Tokens.INTO){
            if(!verificarToken(Tokens.INTO))
                return false;
        }
            
        return true;
    }
    
    boolean Insert1(){
        if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Insert2())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        
        return true;
    }
    
    boolean Insert2(){
        if(token == Tokens.Identificador){
            if(!ColumnRef())
                return false;
            if(!Insert3())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Insert3(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Insert2())
                return false;
        }
        
        return true;
    }
    
    boolean Insert4(){
        if(token == Tokens.Numero || token == Tokens.Float || 
                token == Tokens.Bit || 
                token == Tokens.AVG || token == Tokens.COUNT || 
                token == Tokens.MAX || token == Tokens.MIN || 
                token == Tokens.SUM || token == Tokens.CASE ||
                token == Tokens.AbrirParentesis || 
                token == Tokens.Identificador){
            if(!ConstantTerm())
                return false;
            if(!Insert5())
                return false;
        }
        else if(token == Tokens.Cadena){
            if(!verificarToken(Tokens.Cadena))
                return false;
            if(!Insert5())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Numero.toString() + " 0 " +
                    Tokens.Float.toString() + " 0 " +
                    Tokens.Bit.toString() + " 0 " +                    
                    Tokens.AVG.toString() + " 0 " +
                    Tokens.COUNT.toString() + " 0 " +
                    Tokens.MAX.toString() + " 0 " +
                    Tokens.MIN.toString() + " 0 " +
                    Tokens.SUM.toString() + " 0 " +
                    Tokens.CASE.toString() + " o " +
                    Tokens.AbrirParentesis.toString() + " 0 " +
                    Tokens.Cadena.toString() + " 0 " +
                    Tokens.Identificador.toString());   
            return false;
        }
        
        return true;
    }
    
    boolean Insert5(){
        if (token == Tokens.Coma) {
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Insert4())
                return false;
        }
        
        return true;
    }
    
    //Delete
    boolean Delete(){
        if(token == Tokens.DELETE){
            if(!verificarToken(Tokens.DELETE))
                return false;
            if(!TopExpression())
                return false;
            if(!From())
                return false;
            if(!TableRef())
                return false;
            if(!Delete1())
                return false;
            if(!fin_state())
                return false;
        }
        else{
            ErrorSintactico(Tokens.DELETE.toString());
            return false;
        }
        
        return true;
    }
    
    boolean From(){
        if(token == Tokens.FROM){
            if(!verificarToken(Tokens.FROM))
                return false;
        }
        
        return true;
    }
    
    boolean Delete1(){
        if(token == Tokens.WHERE){
            if(!verificarToken(Tokens.WHERE))
                return false;
            if(!Search_condition())
                return false;
        }
        
        return true;
    }
    
    //Update
    boolean Update(){
        if(token == Tokens.UPDATE){
            if(!verificarToken(Tokens.UPDATE))
                return false;
            if(!Update1())
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!Update2())
                return false;
            if(!verificarToken(Tokens.SET))
                return false;
            if(!Update55())
                return false;
            if(!FromU1())
                return false;
            if(!WhereU1())
                return false;
            if(!fin_state())
                return false;
        }
        else{
            ErrorSintactico(Tokens.UPDATE.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Update1(){
        if(token == Tokens.TOP){
            if(!TopExpression())
                return false;
        }
        
        return true;
    }
    
    boolean Update2(){
        if(token == Tokens.WITH || token == Tokens.Identificador){
            if(!verificarToken(Tokens.WITH))
                return false;
            if(!Update3())
                return false;
        }
        return true;
    }
    
    boolean Update3(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!Update4())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        return true;
    }
    
    boolean Update4(){
        if(token == Tokens.Coma || token == Tokens.Identificador){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Update3())
                return false;
        }
        
        return true;
    }
    
    boolean Update55(){
        if(token == Tokens.Identificador){
            if(!Update5())
                return false;
            if(!Rep())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        return true;
    }
    
    boolean Update5(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!Update6814())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Update6814(){
        if(token == Tokens.Igual){
            if(!Update6())
                return false;
        }
        else if(token == Tokens.Punto){
            if(!Update8())
                return false;
        }
        else if(token == Tokens.AbrirParentesis){
            if(!Update14())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Igual.toString() + " o " +
                    Tokens.Punto.toString() + " o " +
                    Tokens.AbrirParentesis.toString());
            return false;
        }
        return true;
    }        
    
    boolean Update6(){
        if(token == Tokens.Igual){
            if(!verificarToken(Tokens.Igual))
                return false;
            if(!Update7())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Igual.toString());
            return false;
        }
        return true;
    }
    
    boolean Update7(){
        if(token == Tokens.AbrirParentesis || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit || token == Tokens.Identificador){
            if(!Expression())
                return false;
        }
        else if(token == Tokens.DEFAULT){
            if(!verificarToken(Tokens.DEFAULT))
                return false;
        }
        else if(token == Tokens.NULL){
            if(!verificarToken(Tokens.NULL))
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.Numero.toString() + " o " +
                    Tokens.Float.toString() + " o " +
                    Tokens.Bit.toString() + " o " +
                    Tokens.Identificador.toString() + " o " +
                    Tokens.DEFAULT.toString() + " o " +
                    Tokens.NULL.toString());
            return false;
        }
        return true;
    }
    
    boolean Update8(){
        if(token == Tokens.Punto){
            if(!verificarToken(Tokens.Punto))
                return false;
            if(!Update9())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Punto.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Update9(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!Update1011()){
                return false;
            }
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Update1011(){
        if(token == Tokens.Igual){
            if(!Update10())
                return false;            
        }
        else if(token == Tokens.AbrirParentesis){
            if(!Update11())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Igual.toString() + " o " +
                    Tokens.AbrirParentesis.toString());
            return false;
        }
        return true;
    }
    
    boolean Update10(){
        if(token == Tokens.Igual){
            if(!verificarToken(Tokens.Igual))
                return false;
            if(!Expression())
                return false;                
        }
        else{
            ErrorSintactico(Tokens.Igual.toString());
            return false;
        }
        return true;
    }
    
    boolean Update11(){
        if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Update12())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Update12(){
        if(token == Tokens.AbrirParentesis || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit || token == Tokens.Identificador){
            if(!Expression())
                return false;
            if(!Update13())
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.Numero.toString() + " o " +
                    Tokens.Float.toString() + " o " +
                    Tokens.Bit.toString() + " o " +
                    Tokens.Identificador.toString());
            return false;
        }
        return true;
    }
    
    boolean Update13(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Update12())
                return false;
        }
        
        return true;
    }
    
    boolean Update14(){
        if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!verificarToken(Tokens.Punto))
                return false;
            if(!verificarToken(Tokens.WRITE))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Expression())
                return false;
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!verificarToken(Tokens.Numero))
                return false;
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!verificarToken(Tokens.Numero))
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Rep(){
        if(token == Tokens.Coma){
           if(!verificarToken(Tokens.Coma))
                return false; 
           if(!Update55())
               return false;
        }
        return true;
    }
    
    boolean FromU1(){
        if(token == Tokens.FROM){
            if(!verificarToken(Tokens.FROM))               
                return false;
            if(!FromU2())
                return false;
        }
        
        return true;
    }
    
    boolean FromU2(){
        if(token == Tokens.Identificador){
            if(!TableRef())
                return false;
            if(!FromU3())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }
    
    boolean FromU3(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!FromU2())
                return false;
        }
        return true;
    }
    
    boolean WhereU1(){
        if(token == Tokens.WHERE){
            if(!WhereExpression())
                return false;
        }
        
        return true;
    }
    
    //Select
    boolean Select(){
        if(token == Tokens.SELECT){
            if(!verificarToken(Tokens.SELECT))
                return false;
            if(!SelState())
                return false;
            if(!Selaction())
                return false;
        }
        else if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Select())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.SELECT.toString() + " o " +
                    Tokens.AbrirParentesis.toString());
            return false;
            
        }
        return true;
    }
    
    boolean Selaction(){
        if(token == Tokens.FROM){
            if(!verificarToken(Tokens.FROM))
                return false;
            if(!TableExp())
                return false;
            if(!SigExp())
                return false;
            if(!GroupBy_exp())
                return false;
            if(!Having_exp())
                return false;
            if(!OrderBy_exp())
                return false;
            if(!fin_state())
                return false;
        }
        else{
            ErrorSintactico(Tokens.FROM.toString());
            return false;
        }
            
        return true;
    }
    
    boolean SigExp(){
        if(token == Tokens.WHERE){
            if(!verificarToken(Tokens.WHERE))
                return false;
            if(!Search_condition())
                return false;
        }
        return true;
    }
    
    boolean GroupBy_exp(){
        if(token == Tokens.GROUP){
            if(!verificarToken(Tokens.GROUP))
                return false;
            if(!verificarToken(Tokens.BY))
                return false;
            if(!Group_State())
                return false;
            if(!SigGroup())
                return false;
        }
        
        return true;
    }
    
    boolean SigGroup(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Group_State())
                return false;
        }
        return true;
    }
    
    boolean Group_State(){
        if(token == Tokens.Identificador){
            if(!TableRef())
                return false;
            if(!SigGroup())
                return false;            
        }
        else if(token == Tokens.CASE){
            if(!Case())
                return false;
            if(!SigGroup())
                return false;            
        }
        else if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Group_State())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString() + " o " +
                    Tokens.CASE.toString() + " o " +
                    Tokens.AbrirParentesis.toString());
            return false;
        }
        return true;
    }        
    
    boolean Having_exp(){
        if(token == Tokens.HAVING){
            if(!verificarToken(Tokens.HAVING))
                return false;            
            if(!Search_conditionHaving())
                return false;            
        }
        return true;
    }
    
    boolean OrderBy_exp(){
        if(token == Tokens.ORDER){
            if(!verificarToken(Tokens.ORDER))
                return false;
            if(!verificarToken(Tokens.BY))
                return false;
            if(!OrderBy_exp1())
                return false;
        }
        else if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!OrderBy_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        
        return true;
    }
    
    boolean OrderBy_exp1(){
        if(token == Tokens.Identificador){
            if(!TableRef())
                return false;
            if(!SigOrder())
                return false;
            if(!AscDesc())
                return false;
        }
        return true;
    }
    
    boolean SigOrder(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!OrderBy_exp1())
                return false;
        }
            
        
        return true;
    }
    
    boolean SelState(){
        if(token == Tokens.DISTINCT || token == Tokens.ALL || token == Tokens.TOP ||
                token == Tokens.Multiplicacion || token == Tokens.CASE || token == Tokens.Cadena
                || token == Tokens.AVG || token == Tokens.COUNT || token == Tokens.SUM || token == Tokens.MAX
                || token == Tokens.MIN || token == Tokens.AbrirParentesis || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit || token == Tokens.Identificador || token == Tokens.CAST
                || token == Tokens.CONVERT){
            if(!Option())
                return false;
            if(!TopExpression())
                return false;
            if(!SelectExpression())
                return false;
        }
        else{
            ErrorSintactico(Tokens.DISTINCT.toString() + " o " + Tokens.ALL.toString() + " o " + Tokens.TOP.toString() + " o " +
                Tokens.Multiplicacion.toString() + " o " + Tokens.CASE.toString() + " o " + Tokens.Cadena.toString()
                + " o " + Tokens.AVG.toString() + " o " + Tokens.COUNT.toString() + " o " + Tokens.SUM.toString() + " o " + Tokens.MAX.toString()
                + " o " + Tokens.MIN.toString() + " o " + Tokens.Numero.toString() + " o " + Tokens.Bit.toString()
                + " o " + Tokens.Float.toString() + " o " + Tokens.Identificador.toString() + " o " + Tokens.CAST.toString()
                + " o " + Tokens.CONVERT.toString() + " o " + Tokens.AbrirParentesis.toString());
            return false;
        }
        return true;
    }
    
    boolean Option(){
        if(token == Tokens.DISTINCT){
            if(!verificarToken(Tokens.DISTINCT))
                return false;
        }
        else if(token == Tokens.ALL){
            if(!verificarToken(Tokens.ALL))
                return false;
        }
        return true;
    }
    
    boolean Search_conditionHaving(){
        if(token == Tokens.NOT || token == Tokens.AbrirParentesis || token == Tokens.Identificador
                || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit
                || token == Tokens.Cadena || token == Tokens.FREETEXT || token == Tokens.CONTAINS ||
                token == Tokens.AVG || token == Tokens.COUNT || token == Tokens.SUM || token == Tokens.MAX
                || token == Tokens.MIN){
            if(!Not())
                return false;
            if(!PredicadoHaving())
                return false;
            if(!OtroSearchHaving())
                return false;
        }
        else{
            ErrorSintactico(Tokens.NOT.toString() + " o " +
                    Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.Identificador.toString() + " o " +
                    Tokens.Numero.toString() + " o " +
                    Tokens.Float.toString() + " o " +
                    Tokens.Bit.toString() + " o " +
                    Tokens.Cadena.toString() + " o " +
                    Tokens.FREETEXT.toString() + " o " +
                    Tokens.CONTAINS.toString()+ " o " + 
                    Tokens.AVG.toString() + " o " + Tokens.COUNT.toString() + " o " + 
                    Tokens.SUM.toString() + " o " + Tokens.MAX.toString()
                + " o " + Tokens.MIN.toString());
            
            return false;
        }
        
        return true;
    }
    
    boolean PredicadoHaving(){
        if(token == Tokens.AbrirParentesis || token == Tokens.Identificador
                || token == Tokens.Numero || token == Tokens.Float || token == Tokens.Bit){
            if(!Expression())
                return false;
            if(!StateLogic())
                return false;
        }
        else if(token == Tokens.Cadena){
            if(!verificarToken(Tokens.Cadena))
                return false;
            if(!Not())
                return false;
            if(!verificarToken(Tokens.LIKE))
                return false;
            if(!verificarToken(Tokens.Cadena))
                return false;
            if(!Escape_exp())
                return false;
        }
        else if(token == Tokens.CONTAINS){
            if(!verificarToken(Tokens.CONTAINS))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Cont_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else if(token == Tokens.FREETEXT){
            if(!verificarToken(Tokens.FREETEXT))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Cont_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else if(token == Tokens.AVG || token == Tokens.COUNT || token == Tokens.SUM || token == Tokens.MAX
                || token == Tokens.MIN){
            if(!Function())
                return false;
            if(!StateLogic())
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.Numero.toString() + " o " +
                    Tokens.Float.toString() + " o " +
                    Tokens.Bit.toString() + " o " +
                    Tokens.Identificador.toString() + " o " +
                    Tokens.Cadena.toString() + " o " +
                    Tokens.CONTAINS.toString() + " o " +
                    Tokens.FREETEXT.toString() + " o "+
                    Tokens.AVG.toString() + " o " + Tokens.COUNT.toString() + " o " + 
                    Tokens.SUM.toString() + " o " + Tokens.MAX.toString()
                + " o " + Tokens.MIN.toString());
            return false;
        }
        return true;
    }
    
    boolean OtroSearchHaving(){
        if(token == Tokens.OR || token == Tokens.AND){
            if(!Logic_word())
                return false;
            if(!Search_conditionHaving())
                return false;
        }
        
        return true;
    }
    
    boolean SelectExpression(){
        if(token == Tokens.Multiplicacion){
            if(!verificarToken(Tokens.Multiplicacion))
                return false;
            if(!ColExp())
                return false;
            if(!OtroSelect())
                return false;
        }
        else if(token == Tokens.CASE){
            if(!Case())
                return false;
            if(!ColExp())
                return false;
            if(!OtroSelect())
                return false;
        }
        else if(token == Tokens.Cadena){
            if(!verificarToken(Tokens.Cadena))
                return false;
            if(!ColExp())
                return false;
            if(!OtroSelect())
                return false;
        }
        else if(token == Tokens.AVG || token == Tokens.COUNT || token == Tokens.SUM || token == Tokens.MAX
                || token == Tokens.MIN){
            if(!Function())
                return false;
            if(!ColExp())
                return false;
            if(!OtroSelect())
                return false;
        }
        else if(token == Tokens.AbrirParentesis || token == Tokens.Numero || token == Tokens.Bit
                || token == Tokens.Float || token == Tokens.Identificador){
            if(!Expression())
                return false;
            if(!ColExp())
                return false;
            if(!OtroSelect())
                return false;
        }
        else if(token == Tokens.CAST){
            if(!verificarToken(Tokens.CAST))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Cast_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else if(token == Tokens.CONVERT){
            if(!verificarToken(Tokens.CONVERT))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Convert_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.Multiplicacion.toString() + " o " + Tokens.CASE.toString() + " o " + Tokens.Cadena.toString()
                + " o " + Tokens.AVG.toString() + " o " + Tokens.COUNT.toString() + " o " + Tokens.SUM.toString() + " o " + Tokens.MAX.toString()
                + " o " + Tokens.MIN.toString() + " o " + Tokens.Numero.toString() + " o " + Tokens.Bit.toString()
                + " o " + Tokens.Float.toString() + " o " + Tokens.Identificador.toString() + " o " + Tokens.CAST.toString()
                + " o " + Tokens.CONVERT.toString() + " o " + Tokens.AbrirParentesis.toString());
            return false;
        }
        return true;
    }
    
    boolean ColExp(){
        if(token == Tokens.AS){
            if(!verificarToken(Tokens.AS))
                return false;           
            if(!Sig_as())
                return false;
        }
        
        return true;
    }
    
    boolean Sig_as(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
        }
        else if(token == Tokens.Cadena){
            if(!verificarToken(Tokens.Cadena))
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString() + " o " +
                    Tokens.Cadena.toString());
            return false;
        }
        
        return true;
    }
    
    boolean OtroSelect(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!SelectExpression())
                return false;
        }
        return true;
    }        
    
    boolean TableExp(){
        if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!TableExp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else if(token == Tokens.Identificador){
            if(!TableRef())
                return false;
            if(!Alias_tabla())
                return false;
            if(!Join_tabla())
                return false;
        }else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.Identificador.toString());
            return false;
            
        }
        
        return true;
    }
    
    boolean Alias_tabla(){
        if(token == Tokens.AS){
            if(!verificarToken(Tokens.AS))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
        }
        else if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
        }
                   
        return true;
    }
    
    boolean Join_tabla(){
        if(token == Tokens.FULL){
            if(!verificarToken(Tokens.FULL))
                return false;
            if(!Outer_exp())
                return false;
            if(!verificarToken(Tokens.JOIN))
                return false;
            if(!TableRef())
                return false;
            if(!Alias_tabla())
                return false;
            if(!On_exp())
                return false;
            if(!Join_tabla())
                return false;
        }
        else if(token == Tokens.LEFT){
            if(!verificarToken(Tokens.LEFT))
                return false;
            if(!Outer_exp())
                return false;
            if(!verificarToken(Tokens.JOIN))
                return false;
            if(!TableRef())
                return false;
            if(!Alias_tabla())
                return false;
            if(!On_exp())
                return false;
            if(!Join_tabla())
                return false;
        }
        else if(token == Tokens.RIGHT){
            if(!verificarToken(Tokens.RIGHT))
                return false;
            if(!Outer_exp())
                return false;
            if(!verificarToken(Tokens.JOIN))
                return false;
            if(!TableRef())
                return false;
            if(!Alias_tabla())
                return false;
            if(!On_exp())
                return false;
            if(!Join_tabla())
                return false;
        }
        else if(token == Tokens.INNER){
            if(!verificarToken(Tokens.INNER))
                return false;            
            if(!verificarToken(Tokens.JOIN))
                return false;
            if(!TableRef())
                return false;
            if(!Alias_tabla())
                return false;
            if(!On_exp())
                return false;
            if(!Join_tabla())
                return false;
        }        
            
        return true;
    }
    
    boolean On_exp(){
        if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!On_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else if(token == Tokens.ON){
            if(!verificarToken(Tokens.ON))
                return false;
            if(!TableRef())
                return false;
            if(!verificarToken(Tokens.Igual))
                return false;
            if(!TableRef())
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.ON.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Outer_exp(){
        if(token == Tokens.OUTER){
            if(!verificarToken(Tokens.OUTER))
                return false;
        }
        
        return true;
    }
    
    boolean Cast_exp(){
        if(token == Tokens.AbrirParentesis || token == Tokens.Numero ||
                token == Tokens.Float || token == Tokens.Bit || token == Tokens.Identificador){
            if(!Expression())
                return false;
            if(!verificarToken(Tokens.AS))
                return false;
            if(!DataType())
                return false;
            if(!Lenght_exp())
                return false;
        }
        else if(token == Tokens.CAST){
            if(!verificarToken(Tokens.CAST))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Cast_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.AbrirParentesis.toString() + " o " +
                    Tokens.Numero.toString() + " o " +
                    Tokens.Float.toString() + " o " +
                    Tokens.Bit.toString() + " o " +
                    Tokens.Identificador.toString() + " o " +
                    Tokens.CAST.toString());
            return false;
        }
        return true;
    }
    
    boolean Lenght_exp(){
        if(token == Tokens.AbrirParentesis){
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!verificarToken(Tokens.Numero))
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        return true;
    }
    
    boolean Convert_exp(){
        if(token == Tokens.VARCHAR || token == Tokens.INT
                || token == Tokens.BIT || token == Tokens.FLOAT){
            if(!DataType())
                return false;
            if(!Lenght_exp())
                return false;
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Expression())
                return false;            
        }
        else if(token == Tokens.CONVERT){
            if(!verificarToken(Tokens.CONVERT))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Convert_exp())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.VARCHAR.toString() + " 0 " +
                    Tokens.INT.toString() + " 0 " +
                    Tokens.BIT.toString() + " 0 " +
                    Tokens.FLOAT.toString() + " 0 " +
                    Tokens.CONVERT.toString());
            return false;
        }
        
        return true;
    }
    
    //Create
    boolean Create(){
        if(token == Tokens.CREATE){
            if(!verificarToken(Tokens.CREATE))
                return false;
            if(!CreateX())
                return false;
            if(!fin_state())
                return false;
        }
        else{
            ErrorSintactico(Tokens.CREATE.toString());
            return false;
        }
        
        return true;
    }
    
    boolean CreateX(){
        if(token == Tokens.TABLE){
            if(!CreateTable())
                return false;
        }
        else if(token == Tokens.UNIQUE || token == Tokens.CLUSTERED || 
                token == Tokens.NONCLUSTERED || token == Tokens.INDEX){
            if(!CreateIndex())
                return false;
        }
        else if(token == Tokens.DATABASE){
            if(!CreateDatabase())
                return false;
        }
        else if(token == Tokens.USER){
            if(!CreateUser())
                return false;
        }
        else if(token == Tokens.OR || token == Tokens.VIEW)   {
            if(!CreateView())
                return false;
        }
        else{
            ErrorSintactico(Tokens.UNIQUE.toString() + " o " +
                    Tokens.CLUSTERED.toString() + " o " +
                    Tokens.NONCLUSTERED.toString() + " o " +
                    Tokens.INDEX.toString() + " o " +
                    Tokens.TABLE.toString() + " o " +
                    Tokens.DATABASE.toString() + " o " +
                    Tokens.USER.toString() + " o " +
                    Tokens.OR.toString() + " o " +
                    Tokens.VIEW.toString());
            return false;
        }
            
        return true;
    }
    
    boolean CreateTable(){
        if(token == Tokens.TABLE){
            if(!verificarToken(Tokens.TABLE))
                return false;
            if(!TableRef())
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Create1())
                return false;
            if(!Create3())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        else{
            ErrorSintactico(Tokens.TABLE.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Create1(){
        if(token == Tokens.Identificador){
            if(!ColumnDef())
                return false;
            if(!Create2())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Create2(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Create1())
                return false;
        }
        return true;
    }
    
    boolean Create3(){
        if(token == Tokens.CONSTRAINT){
            if(!Constraint())
                return false;
            if(!Create4())
                return false;
        }
        return true;
    }
    
    boolean Create4(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Create3())
                return false;
        }
        return true;
    }
    
    boolean CreateIndex(){
        if(token == Tokens.UNIQUE || token == Tokens.CLUSTERED || token == Tokens.NONCLUSTERED
                || token == Tokens.INDEX){
            if(!Unique())
                return false;
            if(!Clustered())
                return false;
            if(!verificarToken(Tokens.INDEX))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!verificarToken(Tokens.ON))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Index1())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
            if(!Index4())
                return false;
            if(!Index7())
                return false;
        }
        else{
            ErrorSintactico(Tokens.UNIQUE.toString() + " o " +
                    Tokens.CLUSTERED.toString() + " o " +
                    Tokens.NONCLUSTERED.toString() + " o " +
                    Tokens.INDEX.toString());
            return false;
        }
        return true;
    }
    
    boolean Unique(){
        if(token == Tokens.UNIQUE){
            if(!verificarToken(Tokens.UNIQUE))
                return false;
        }
            
        return true;
    }
    
    boolean Clustered(){
        if(token == Tokens.CLUSTERED){
            if(!verificarToken(Tokens.CLUSTERED))
                return false;
        }
        else if(token == Tokens.NONCLUSTERED){
            if(!verificarToken(Tokens.NONCLUSTERED))
                return false;
        }
        return true;
    }
    
    boolean Index1(){
        if(token == Tokens.Identificador){
            if(!ColumnRef())
                return false;
            if(!AscDesc())
                return false;
            if(!Index3())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        return true;
    }
    
    boolean Index3(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Index1())
                return false;
        }
        return true;
    }
    
    boolean Index4(){
        if(token == Tokens.INCLUDE){
            if(!verificarToken(Tokens.INCLUDE))
                return false;
            if(!verificarToken(Tokens.AbrirParentesis))
                return false;
            if(!Index5())
                return false;
            if(!verificarToken(Tokens.CerrarParentesis))
                return false;
        }
        return true;
    }
    
    boolean Index5(){
        if(token == Tokens.Identificador){
            if(!ColumnRef())
                return false;
            if(!Index6())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Index6(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Index5())
                return false;
        }
        return true;
    }
    
    boolean Index7(){
        if(token == Tokens.WHERE){
            if(!WhereExpression())
                return false;
        }
            
        return true;
    }
    
    boolean CreateDatabase(){
        if(token == Tokens.DATABASE){
            if(!verificarToken(Tokens.DATABASE))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!Database2())
                return false;
            if(!Database6())
                return false;
        }
        else{
            ErrorSintactico(Tokens.DATABASE.toString());
            return false;
        }
        return true;
    }        
    
    boolean Database2(){
        if(token == Tokens.ON){
            if(!verificarToken(Tokens.ON))
                return false;
            if(!Primary())
                return false;
            if(!Database3())
                return false;
        }
        else{
            ErrorSintactico(Tokens.ON.toString());
            return false;
        }
        return true;
    }
    
    boolean Primary(){
        if(token == Tokens.PRIMARY){
            if(!verificarToken(Tokens.PRIMARY))
                return false;
        }
            
        return true;
    }
    
    boolean Database3(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!Database4())
                return false;            
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        return true;
    }
    
    boolean Database4(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Database3())
                return false;
        }
        return true;
    }
    
    boolean Database6(){
        if(token == Tokens.COLLATE){
            if(!verificarToken(Tokens.COLLATE))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
        }
        return true;
    }
    
    boolean CreateUser(){
        if(token == Tokens.USER){
            if(!verificarToken(Tokens.USER))
                return false;
            if(!CreateUser12())
                return false;
        }
        else{
            ErrorSintactico(Tokens.USER.toString());
            return false;
        }
            
        return true;
    }
    
    boolean CreateUser12(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!User6())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        return true;
    }               
    
    boolean User6(){
        if(token == Tokens.WITH || token == Tokens.Identificador){
            if(!With())
                return false;
            if(!User7())
                return false;
        }
        return true;
    }
    
    boolean With(){
        if(token == Tokens.WITH){
            if(!verificarToken(Tokens.WITH))
                return false;
        }
        return true;
    }
    
    boolean User7(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!User8())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
            
        return true;
    }
    
    boolean User8(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!User7())
                return false;
        }
        return true;
    }
    
    boolean CreateView(){
        if(token == Tokens.OR || token == Tokens.VIEW){
            if(!OrAlter())
                return false;
            if(!verificarToken(Tokens.VIEW))
                return false;
            if(!ColumnRef())
                return false;
            if(!Col())
                return false;
            if(!View2())
                return false;
            if(!verificarToken(Tokens.AS))
                return false;
            if(!Select())
                return false;
            if(!With2())
                return false;
        }
        else{
            ErrorSintactico(Tokens.OR.toString() + " o " +
            Tokens.VIEW.toString());
            return false;
        }
        return true;
    }
    
    boolean OrAlter(){
        if(token == Tokens.OR){
            if(!verificarToken(Tokens.OR))
                return false;
            if(!verificarToken(Tokens.ALTER))
                return false;
        }
        return true;
    }        
    
    boolean Col(){
        if(token == Tokens.Identificador){
            if(!ColumnRef())
                return false;
            if(!View1())
                return false;
        }
        return true;
    }
    
    boolean View1(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!Col())
                return false;            
        }
        return true;
    }
    
    boolean View2(){
        if(token == Tokens.WITH){
            if(!verificarToken(Tokens.WITH))
                return false;
            if(!View3())
                return false;
        }
        return true;
    }
    
    boolean View3(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!View4())
                return false;            
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        return true;
    }
    
    boolean View4(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!View3())
                return false;
        }
        return true;
    }
    
    boolean With2(){
        if(token == Tokens.WITH){
            if(!verificarToken(Tokens.WITH))
                return false;
            if(!verificarToken(Tokens.CHECK))
                return false;
            if(!verificarToken(Tokens.OPTION))
                return false;
        }
        return true;
    }        
    
    //Alter
    boolean Alter(){  
        if(token == Tokens.ALTER){
            if(!verificarToken(Tokens.ALTER))
                return false;
            if(!AlterX())
                return false;
            if(!fin_state())
                return false;
        }
        else{
            ErrorSintactico(Tokens.ALTER.toString());
            return false;
        }
        
        return true;
    }
    
    boolean AlterX(){
        if(token == Tokens.TABLE){
            if(!AlterTable())
                return false;
        }
        else if(token == Tokens.DATABASE){
            if(!AlterDatabase())
                return false;
        }
        else if(token == Tokens.VIEW){
            if(!AlterView())
                return false;
        }
        else if(token == Tokens.INDEX){
            if(!AlterIndex())
                return false;
        }
        else{
            ErrorSintactico(Tokens.TABLE.toString() + " o " +
                    Tokens.DATABASE.toString() + " o " +
                    Tokens.VIEW.toString() + " o " +
                    Tokens.INDEX.toString());
            return false;
        }   
        
        return true;
    }
    
    boolean AlterTable(){
        if(token == Tokens.TABLE){
            if(!verificarToken(Tokens.TABLE))
                return false;
            if(!TableRef())
                return false;
            if(!AlterT())
                return false;
        }
        else{
            ErrorSintactico(Tokens.TABLE.toString());
            return false;
        }
        return true;
    }
    
    boolean AlterT(){
        if(token == Tokens.ALTER){
            if(!AlterTColumn())
                return false;            
        }
        else if(token == Tokens.ADD){
            if(!AlterTAdd())
                return false;
        }
        else if(token == Tokens.DROP){
            if(!AlterTDrop())
                return false;
        }
        else{
            ErrorSintactico(Tokens.ALTER.toString() + " o " +
                    Tokens.ADD.toString() + " o " +
                    Tokens.DROP.toString());
            return false;
        }
        return true;
    }
    
    boolean AlterTColumn(){
        if(token == Tokens.ALTER){
            if(!verificarToken(Tokens.ALTER))
                return false;
            if(!verificarToken(Tokens.COLUMN))
                return false;
            if(!ColumnDef())
                return false;
            if(!NulNot())
                return false;
        }
        else{
            ErrorSintactico(Tokens.ALTER.toString());
            return false;
        }
        return true;
    }
    
    boolean NulNot(){
        if(token == Tokens.NULL){
            if(!verificarToken(Tokens.NULL))
                return false;
        }
        else if(token == Tokens.NOT){
            if(!verificarToken(Tokens.NOT))
                return false;
            if(!verificarToken(Tokens.NULL))
                return false;
        }
            
        return true;
    }
    
    boolean AlterTAdd(){
        if(token == Tokens.ADD){
            if(!verificarToken(Tokens.ADD))
                return false;
            if(!AlterAdd1())
                return false;
        }
        else{
            ErrorSintactico(Tokens.ADD.toString());
            return false;
        }
        return true;
    }        
    
    boolean AlterAdd1(){
        if(token == Tokens.Identificador || token == Tokens.CONSTRAINT){
            if(!AlterTAdd2())
                return false;
            if(!RepAdd())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString() + " o " +
                    Tokens.CONSTRAINT.toString());
            return false;
            
        }
        return true;
    }
    
    boolean AlterTAdd2(){
        if(token == Tokens.Identificador){
            if(!ColumnDef())
                return false;
        }
        else if(token == Tokens.CONSTRAINT){
            if(!Constraint())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString() + " o " +
                    Tokens.CONSTRAINT.toString());
            return false;
        }
        return true;
    }
    
    boolean RepAdd(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!AlterAdd1())
                return false;
        }
        return true;
    }
    
    boolean AlterTDrop(){
        if(token == Tokens.DROP){
            if(!verificarToken(Tokens.DROP))
                return false;
            if(!AlterTDrop1())
                return false;
        }
        else{
            ErrorSintactico(Tokens.DROP.toString());
            return false;
        }
        return true;
    }
    
    boolean AlterTDrop1(){
        if(token == Tokens.CONSTRAINT || token == Tokens.COLUMN){
            if(!ConsCol())
                return false;
            if(!IfExists())
                return false;
            if(!AlterTDrop2())
                return false;
            if(!RepDrop())
                return false;
        }
        else{
            ErrorSintactico(Tokens.CONSTRAINT.toString() + " o " +
                    Tokens.COLUMN.toString());
            return false;
        }
        return true;
    }
    
    boolean ConsCol(){
        if(token == Tokens.CONSTRAINT){
            if(!verificarToken(Tokens.CONSTRAINT))
                return false;
        }
        else if(token == Tokens.COLUMN){
            if(!verificarToken(Tokens.COLUMN))
                return false;
        }
        else{
            ErrorSintactico(Tokens.CONSTRAINT.toString() +  " o " +
                    Tokens.COLUMN.toString());
            return false;
        }
        
        return true;
    }
    
    boolean AlterTDrop2(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!AlterTDrop3())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
        return true;
    }
    
    boolean AlterTDrop3(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!AlterTDrop2())
                return false;
        }
        return true;
    }
    
    boolean RepDrop(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!AlterTDrop1())
                return false;
        }
        return true;
    }
    
    boolean AlterDatabase(){
        if(token == Tokens.DATABASE){
            if(!verificarToken(Tokens.DATABASE))
                return false;
            if(!Name())
                return false;
            if(!AlterD1())
                return false;
        }
        else{
            ErrorSintactico(Tokens.DATABASE.toString());
            return false;
        }
        return true;
    }
    
    boolean Name(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
        }
        else if(token == Tokens.CURRENT){
            if(!verificarToken(Tokens.CURRENT))
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString() + " o " +
                    Tokens.CURRENT.toString());
            return false;
        }
        return true;
    }
    
    boolean AlterD1(){
        if(token == Tokens.COLLATE){
            if(!verificarToken(Tokens.COLLATE))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
        }
        else if(token == Tokens.SET){
            if(!verificarToken(Tokens.SET))
                return false;
            if(!AlterD2())
                return false;
            if(!verificarToken(Tokens.WITH))
                return false;
            if(!AlterD2())
                return false;
        }
        else{
            ErrorSintactico(Tokens.COLLATE.toString() + " o " +
                    Tokens.SET.toString());
            return false;
        }
        return true;
    }
    
    boolean AlterD2(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!AlterD3())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString());
            return false;
        }
            
        return true;
    }
    
    boolean AlterD3(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!AlterD2())
                return false;
        }
        return true;
    }
    
    boolean AlterView(){
        if(token == Tokens.VIEW){
            if(!verificarToken(Tokens.VIEW))
                return false;
            if(!ColumnRef())
                return false;
            /*if(!AlterViewCol())
                return false;*/
            if(!verificarToken(Tokens.AS))
                return false;
            if(!Select())
                return false;
            if(!AlterViewCheck())
                return false;
        }
        else{
            ErrorSintactico(Tokens.VIEW.toString());
            return false;
        }
        return true;
    }
    
    boolean AlterViewCol(){
        if(token == Tokens.Identificador){
            if(!ColumnRef())
                return false;
            if(!AlterViewCol1())
                return false;            
        }
        return true;
    }
    
    boolean AlterViewCol1(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!AlterViewCol())
                return false;            
        }
        return true;
    }
    
    boolean AlterViewCheck(){
        if(token == Tokens.WITH){
            if(!verificarToken(Tokens.WITH))
                return false;
            if(!verificarToken(Tokens.CHECK))
                return false;
            if(!verificarToken(Tokens.OPTION))
                return false;
        }
            
        return true;
    }
    
    boolean AlterIndex(){
        if(token == Tokens.INDEX){
            if(!verificarToken(Tokens.INDEX))
                return false;
            if(!IndexName())
                return false;
            if(!verificarToken(Tokens.ON))
                return false;
            if(!TableRef())
                return false;               
        }
        else{
            ErrorSintactico(Tokens.VIEW.toString());
            return false;
        }
        return true;
    }
    
    boolean IndexName(){
        if(token == Tokens.Identificador){
            if(!verificarToken(Tokens.Identificador))
                    return false;
        }
        else if(token == Tokens.ALL){
            if(!verificarToken(Tokens.ALL))
                return false;            
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString() + " o " +
                    Tokens.ALL.toString());
            return false;
        }
        return true;
    }        
                
    //Drop
    boolean Drop(){
        if(token == Tokens.DROP){
            if(!verificarToken(Tokens.DROP))
                return false;
            if(!Droppers())
                return false;
            if(!fin_state())
                return false;            
        }
        else{
            ErrorSintactico(Tokens.DROP.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Droppers(){
        if(token == Tokens.TABLE){
            if(!Drop_table())
                return false;
        }
        else if(token == Tokens.VIEW){
            if(!Drop_view())
                return false;
        }
        else if(token == Tokens.DATABASE){
            if(!Drop_database())
                return false;
        }
        else if(token == Tokens.USER){
            if(!Drop_user())
                return false;
        }
        else if(token == Tokens.INDEX){
            if(!Drop_index())
                return false;
        }
        else
        {
            ErrorSintactico(Tokens.TABLE.toString() + " o " +
                    Tokens.VIEW.toString() + " o " +
                    Tokens.DATABASE.toString() + " o " +
                    Tokens.USER.toString() + " o " +
                    Tokens.INDEX.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Drop_table(){
        if(token == Tokens.TABLE){
            if(!verificarToken(Tokens.TABLE))
                return false;
            if(!IfExists())
                return false;
            if(!TableRef())
                return false;
            if(!SigDrop_table())
                return false;
        }
        else{
            ErrorSintactico(Tokens.TABLE.toString());
            return false;
        }
        
        return true;
    }
    
    boolean SigDrop_table(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!TableRef())
                return false;
            if(!SigDrop_table())
                return false;                
        }        
        return true;
    }
    
    boolean Drop_view(){
        if(token == Tokens.VIEW){
            if(!verificarToken(Tokens.VIEW))
                return false;
            if(!IfExists())
                return false;
            if(!ColumnRef())
                return false;
            if(!SigDrop_view())
                return false;
        }
        else{
            ErrorSintactico(Tokens.VIEW.toString());
            return false;
        }
        
        return true;
    }
    
    boolean SigDrop_view(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!ColumnRef())
                return false;
            if(!SigDrop_view())
                return false;
        }
        
        return true;
    }
    
    boolean Drop_database(){
        if(token == Tokens.DATABASE){
            if(!verificarToken(Tokens.DATABASE))
                return false;
            if(!IfExists())
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!SigDrop_db_user())
                return false;
        }
        else{
            ErrorSintactico(Tokens.DATABASE.toString());
            return false;
        }
        
        return true;
    }
    
    boolean SigDrop_db_user(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!SigDrop_db_user())
                return false;
        }
        
        return true;
    }
    
    boolean Drop_user(){
        if(token == Tokens.USER){
            if(!verificarToken(Tokens.USER))
                return false;
            if(!IfExists())
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!SigDrop_db_user())
                return false;
        }
        else{
            ErrorSintactico(Tokens.USER.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Drop_index(){
        if(token == Tokens.INDEX){
            if(!verificarToken(Tokens.INDEX))
                return false;
            if(!IfExists())
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!Find_index())
                return false;
        }
        else{
            ErrorSintactico(Tokens.INDEX.toString());
            return false;
        }
        
        return true;
    }
    
    boolean Find_index(){
        if(token == Tokens.Identificador){
            if(!ColumnRef())
                return false;
            if(!SigColumn())
                return false;
        }
        else if(token == Tokens.ON){
            if(!verificarToken(Tokens.ON))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!SigId())
                return false;
        }
        else{
            ErrorSintactico(Tokens.Identificador.toString() + " o " +
            Tokens.ON.toString());
            return false;
        }
        
        return true;
    }
    
    boolean SigColumn(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!ColumnRef())
                return false;
            if(!SigColumn())
                return false;
        }
        
        return true;
    }
    
    boolean SigId(){
        if(token == Tokens.Coma){
            if(!verificarToken(Tokens.Coma))
                return false;
            if(!verificarToken(Tokens.Identificador))
                return false;
            if(!SigId())
                return false;
        }
        
        return true;
    }
    
    //Truncate
    boolean Truncate(){
        if(token == Tokens.TRUNCATE){
            if(!verificarToken(Tokens.TRUNCATE))
                return false;
            if(!verificarToken(Tokens.TABLE))
                return false;
            if(!TableRef())
                return false;
            if(!fin_state())
                return false;
        }
        else{
            ErrorSintactico(Tokens.TRUNCATE.toString());
            return false;
        }
        
        return true;
    }        
}