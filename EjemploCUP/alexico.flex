
/* --------------------------Codigo de Usuario----------------------- */
package ejemplocup;

import java_cup.runtime.*;
import java.io.Reader;
      
%% //inicio de opciones
   
/* ------ Seccion de opciones y declaraciones de JFlex -------------- */  
   
/* 
    Cambiamos el nombre de la clase del analizador a Lexer
*/
%class AnalizadorLexico

/*
    Activar el contador de lineas, variable yyline
    Activar el contador de columna, variable yycolumn
*/
%line
%column
    
/* 
   Activamos la compatibilidad con Java CUP para analizadores
   sintacticos(parser)
*/
%cup
   
/*
    Declaraciones

    El codigo entre %{  y %} sera copiado integramente en el 
    analizador generado.
*/
LM=[A-Z_]
Lm=[a-z]
D=[0-9]
coma = ","
espacio=[ \t\r\n\f]
FinLinea = \r|\n|\r\n
caracter = [^\r\n]
caracterString = [^~"'"~\r~\n]
char = [.\r\n]
comentarioMulti = ("/*"("*")* [^*] ("*")* ~"*/") | ("/*" ("*")+ "/")


cadena = "'"{caracterString}*"'"

comentario = "--" {caracter}* {FinLinea}?
%{
    /*  Generamos un java_cup.Symbol para guardar el tipo de token 
        encontrado */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Generamos un Symbol para el tipo de token encontrado 
       junto con su valor */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
	/*public String lexeme;   
    public int linea;
    public int columna;
    public int columnaInicial;  */
%}
   

/*
    Macro declaraciones
  
    Declaramos expresiones regulares que despues usaremos en las
    reglas lexicas.
*/
   
/*  Un salto de linea es un \n, \r o \r\n dependiendo del SO   */
//Salto = \r|\n|\r\n
   
/* Espacio es un espacio en blanco, tabulador \t, salto de linea 
    o avance de pagina \f, normalmente son ignorados */
//Espacio     = {Salto} | [ \t\f]
   
/* Una literal entera es un numero 0 oSystem.out.println("\n*** Generado " + archNombre + "***\n"); un digito del 1 al 9 
    seguido de 0 o mas digitos del 0 al 9 */
//Entero = 0 | [1-9][0-9]*


%% //fin de opciones
/* -------------------- Seccion de reglas lexicas ------------------ */
   
/*
   Esta seccion contiene expresiones regulares y acciones. 
   Las acciones son código en Java que se ejecutara cuando se
   encuentre una entrada valida para la expresion regular correspondiente */
   
   /* YYINITIAL es el estado inicial del analizador lexico al escanear.
      Las expresiones regulares solo serán comparadas si se encuentra
      en ese estado inicial. Es decir, cada vez que se encuentra una 
      coincidencia el scanner vuelve al estado inicial. Por lo cual se ignoran
      estados intermedios.*/  
   
    /* Regresa que el token SEMI declarado en la clase sym fue encontrado. */
    //";"                { return symbol(sym.SEMI); }
    /* Regresa que el token OP_SUMA declarado en la clase sym fue encontrado. */
    //"+"                {  System.out.print(" + ");
    //                      return symbol(sym.OP_SUMA); }
    /* Regresa que el token OP_SUMA declarado en la clase sym fue encontrado. */
    //"-"                {  System.out.print(" - ");
    //                      return symbol(sym.OP_RESTA); }
    /* Regresa que el token OP_SUMA declarado en la clase sym fue encontrado. */
    //"*"                {  System.out.print(" * ");
    //                      return symbol(sym.OP_MULT); }
    /* Regresa que el token PARENIZQ declarado en la clase sym fue encontrado. */
    //"("                {  System.out.print(" ( ");
    //                      return symbol(sym.PARENIZQ); }
                          /* Regresa que el token PARENIZQ declarado en la clase sym fue encontrado. */
    //")"                {  System.out.print(" ) ");
    //                      return symbol(sym.PARENDER); }
   
    /* Si se encuentra un entero, se imprime, se regresa un token numero
        que representa un entero y el valor que se obtuvo de la cadena yytext
        al convertirla a entero. yytext es el token encontrado. */
    //{Entero}      {   System.out.print(yytext()); 
    //                  return symbol(sym.ENTERO, new Integer(yytext())); }

    /* No hace nada si encuentra el espacio en blanco */
    //{Espacio}       { /* ignora el espacio */ } 
	
{coma} {return symbol(sym.Coma);}
{espacio} {/*Ignore*/}
{comentarioMulti}  | {comentario} {/*Ignore*/}

ADD {return symbol(sym.ADD);}
EXTERNAL {return symbol(sym.EXTERNAL);}
PROCEDURE {return symbol(sym.PROCEDURE);}
ALL {return symbol(sym.ALL);}
FETCH {return symbol(sym.FETCH);}
PUBLIC {return symbol(sym.PUBLIC);}
ALTER {return symbol(sym.ALTER);}
FILE {return symbol(sym.FILE);}
RAISERROR {return symbol(sym.RAISERROR);}
AND {return symbol(sym.AND);}
FILLFACTOR {return symbol(sym.FILLFACTOR);}
READ {return symbol(sym.READ);}
ANY {return symbol(sym.ANY);}
FOR {return symbol(sym.FOR);}
READTEXT {return symbol(sym.READTEXT);}
AS {return symbol(sym.AS);}
FOREIGN {return symbol(sym.FOREIGN);}
RECONFIGURE {return symbol(sym.RECONFIGURE);}
ASC {return symbol(sym.ASC);}
FREETEXT {return symbol(sym.FREETEXT);}
REFERENCES {return symbol(sym.REFERENCES);}
AUTHORIZATION {return symbol(sym.AUTHORIZATION);}
FREETEXTTABLE {return symbol(sym.FREETEXTTABLE);}
REPLICATION {return symbol(sym.REPLICATION);}
BACKUP {return symbol(sym.BACKUP);}
FROM {return symbol(sym.FROM);}
RESTORE {return symbol(sym.RESTORE);}
BEGIN {return symbol(sym.BEGIN);}
FULL {return symbol(sym.FULL);}
RESTRICT {return symbol(sym.RESTRICT);}
BETWEEN {return symbol(sym.BETWEEN);}
FUNCTION {return symbol(sym.FUNCTION);}
RETURN {return symbol(sym.RETURN);}
BREAK {return symbol(sym.BREAK);}
GOTO {return symbol(sym.GOTO);}
REVERT {return symbol(sym.REVERT);}
BROWSE {return symbol(sym.BROWSE);}
GRANT {return symbol(sym.GRANT);}
REVOKE {return symbol(sym.REVOKE);}
BULK {return symbol(sym.BULK);}
GROUP {return symbol(sym.GROUP);}
RIGHT {return symbol(sym.RIGHT);}
BY {return symbol(sym.BY);}
HAVING {return symbol(sym.HAVING);}
ROLLBACK {return symbol(sym.ROLLBACK);}
CASCADE {return symbol(sym.CASCADE);}
HOLDLOCK {return symbol(sym.HOLDLOCK);}
ROWCOUNT {return symbol(sym.ROWCOUNT);}
CASE {return symbol(sym.CASE);}
IDENTITY {return symbol(sym.IDENTITY);}
ROWGUIDCOL {return symbol(sym.ROWGUIDCOL);}
CHECK {return symbol(sym.CHECK);}
IDENTITY_INSERT {return symbol(sym.IDENTITY_INSERT);}
RULE {return symbol(sym.RULE);}
CHECKPOINT {return symbol(sym.CHECKPOINT);}
IDENTITYCOL {return symbol(sym.IDENTITYCOL);}
SAVE {return symbol(sym.SAVE);}
CLOSE {return symbol(sym.CLOSE);}
IF {return symbol(sym.IF);}
SCHEMA {return symbol(sym.SCHEMA);}
CLUSTERED {return symbol(sym.CLUSTERED);}
IN {return symbol(sym.IN);}
SECURITYAUDIT {return symbol(sym.SECURITYAUDIT);}
COALESCE {return symbol(sym.COALESCE);}
INDEX {return symbol(sym.INDEX);}
SELECT {return symbol(sym.SELECT);}
COLLATE {return symbol(sym.COLLATE);}
INNER {return symbol(sym.INNER);}
SEMANTICKEYPHRASETABLE {return symbol(sym.SEMANTICKEYPHRASETABLE);}
COLUMN {return symbol(sym.COLUMN);}
INSERT {return symbol(sym.INSERT);}
SEMANTICSIMILARITYDETAILSTABLE {return symbol(sym.SEMANTICSIMILARITYDETAILSTABLE);}
COMMIT {return symbol(sym.COMMIT);}
INTERSECT {return symbol(sym.INTERSECT);}
SEMANTICSIMILARITYTABLE {return symbol(sym.SEMANTICSIMILARITYTABLE);}
COMPUTE {return symbol(sym.COMPUTE);}
INTO {return symbol(sym.INTO);}
SESSION_USER {return symbol(sym.SESSION_USER);}
CONSTRAINT {return symbol(sym.CONSTRAINT);}
IS {return symbol(sym.IS);}
SET {return symbol(sym.SET);}
CONTAINS {return symbol(sym.CONTAINS);}
JOIN {return symbol(sym.JOIN);}
SETUSER {return symbol(sym.SETUSER);}
CONTAINSTABLE {return symbol(sym.CONTAINSTABLE);}
KEY {return symbol(sym.KEY);}
SHUTDOWN {return symbol(sym.SHUTDOWN);}
CONTINUE {return symbol(sym.CONTINUE);}
KILL {return symbol(sym.KILL);}
SOME {return symbol(sym.SOME);}
CONVERT {return symbol(sym.CONVERT);}
LEFT {return symbol(sym.LEFT);}
STATISTICS {return symbol(sym.STATISTICS);}
CREATE {return symbol(sym.CREATE);}
LIKE {return symbol(sym.LIKE);}
SYSTEM_USER {return symbol(sym.SYSTEM_USER);}
CROSS {return symbol(sym.CROSS);}
LINENO {return symbol(sym.LINENO);}
CURRENT {return symbol(sym.CURRENT);}
LOAD {return symbol(sym.LOAD);}
TABLESAMPLE {return symbol(sym.TABLESAMPLE);}
CURRENT_DATE {return symbol(sym.CURRENT_DATE);}
MERGE {return symbol(sym.MERGE);}
TEXTSIZE {return symbol(sym.TEXTSIZE);}
CURRENT_TIME {return symbol(sym.CURRENT_TIME);}
NATIONAL {return symbol(sym.NATIONAL);}
THEN {return symbol(sym.THEN);}
CURRENT_TIMESTAMP {return symbol(sym.CURRENT_TIMESTAMP);}
NOCHECK {return symbol(sym.NOCHECK);}
TO {return symbol(sym.TO);}
CURRENT_USER {return symbol(sym.CURRENT_USER);}
NONCLUSTERED {return symbol(sym.NONCLUSTERED);}
TOP {return symbol(sym.TOP);}
CURSOR {return symbol(sym.CURSOR);}
NOT {return symbol(sym.NOT);}
TRAN {return symbol(sym.TRAN);}
DATABASE {return symbol(sym.DATABASE);}
NULL {return symbol(sym.NULL);}
TRANSACTION {return symbol(sym.TRANSACTION);}
DBCC {return symbol(sym.DBCC);}
NULLIF {return symbol(sym.NULLIF);}
TRIGGER {return symbol(sym.TRIGGER);}
DEALLOCATE {return symbol(sym.DEALLOCATE);}
OF {return symbol(sym.OF);}
TRUNCATE {return symbol(sym.TRUNCATE);}
DECLARE {return symbol(sym.DECLARE);}
OFF {return symbol(sym.OFF);}
TRY_CONVERT {return symbol(sym.TRY_CONVERT);}
DEFAULT {return symbol(sym.DEFAULT);}
OFFSETS {return symbol(sym.OFFSETS);}
TSEQUAL {return symbol(sym.TSEQUAL);}
DELETE {return symbol(sym.DELETE);}
ON {return symbol(sym.ON);}
UNION {return symbol(sym.UNION);}
DENY {return symbol(sym.DENY);}
OPEN {return symbol(sym.OPEN);}
UNIQUE {return symbol(sym.UNIQUE);}
DESC {return symbol(sym.DESC);}
OPENDATASOURCE {return symbol(sym.OPENDATASOURCE);}
UNPIVOT {return symbol(sym.UNPIVOT);}
DISK {return symbol(sym.DISK);}
OPENQUERY {return symbol(sym.OPENQUERY);}
UPDATE {return symbol(sym.UPDATE);}
DISTINCT {return symbol(sym.DISTINCT);}
OPENROWSET {return symbol(sym.OPENROWSET);}
UPDATETEXT {return symbol(sym.UPDATETEXT);}
DISTRIBUTED {return symbol(sym.DISTRIBUTED);}
OPENXML {return symbol(sym.OPENXML);}
USE {return symbol(sym.USE);}
DOUBLE {return symbol(sym.DOUBLE);}
OPTION {return symbol(sym.OPTION);}
USER {return symbol(sym.USER);}
DROP {return symbol(sym.DROP);}
OR {return symbol(sym.OR);}
VALUES {return symbol(sym.VALUES);}
DUMP {return symbol(sym.DUMP);}
ORDER {return symbol(sym.ORDER);}
VARYING {return symbol(sym.VARYING);}
ELSE {return symbol(sym.ELSE);}
OUTER {return symbol(sym.OUTER);}
VIEW {return symbol(sym.VIEW);}
END {return symbol(sym.END);}
OVER {return symbol(sym.OVER);}
WAITFOR {return symbol(sym.WAITFOR);}
ERRLVL {return symbol(sym.ERRLVL);}
PERCENT {return symbol(sym.PERCENT);}
WHEN {return symbol(sym.WHEN);}
ESCAPE {return symbol(sym.ESCAPE);}
PIVOT {return symbol(sym.PIVOT);}
WHERE {return symbol(sym.WHERE);}
EXCEPT {return symbol(sym.EXCEPT);}
PLAN {return symbol(sym.PLAN);}
WHILE {return symbol(sym.WHILE);}
EXEC {return symbol(sym.EXEC);}
PRECISION {return symbol(sym.PRECISION);}
WITH {return symbol(sym.WITH);}
EXECUTE {return symbol(sym.EXECUTE);}
PRIMARY {return symbol(sym.PRIMARY);}
WITHINGROUP {return symbol(sym.WITHINGROUP);}
EXISTS {return symbol(sym.EXISTS);}
PRINT {return symbol(sym.PRINT);}
WRITETEXT {return symbol(sym.WRITETEXT);}
EXIT {return symbol(sym.EXIT);}
PROC {return symbol(sym.PROC);}
ABSOLUTE {return symbol(sym.ABSOLUTE);}
EXEC {return symbol(sym.EXEC);}
OVERLAPS {return symbol(sym.OVERLAPS);}
ACTION {return symbol(sym.ACTION);}
EXECUTE {return symbol(sym.EXECUTE);}
PAD {return symbol(sym.PAD);}
ADA {return symbol(sym.ADA);}
EXISTS {return symbol(sym.EXISTS);}
PARTIAL {return symbol(sym.PARTIAL);}
ADD {return symbol(sym.ADD);}
EXTERNAL {return symbol(sym.EXTERNAL);}
PASCAL {return symbol(sym.PASCAL);}
ALL {return symbol(sym.ALL);}
EXTRACT {return symbol(sym.EXTRACT);}
POSITION {return symbol(sym.POSITION);}
ALLOCATE {return symbol(sym.ALLOCATE);}
FALSE {return symbol(sym.FALSE);}
PRECISION {return symbol(sym.PRECISION);}
ALTER {return symbol(sym.ALTER);}
FETCH {return symbol(sym.FETCH);}
PREPARE {return symbol(sym.PREPARE);}
AND {return symbol(sym.AND);}
FIRST {return symbol(sym.FIRST);}
PRESERVE {return symbol(sym.PRESERVE);}
ANY {return symbol(sym.ANY);}
FLOAT {return symbol(sym.FLOAT);}
PRIMARY {return symbol(sym.PRIMARY);}
ARE {return symbol(sym.ARE);}
FOR {return symbol(sym.FOR);}
PRIOR {return symbol(sym.PRIOR);}
AS {return symbol(sym.AS);}
FOREIGN {return symbol(sym.FOREIGN);}
PRIVILEGES {return symbol(sym.PRIVILEGES);}
ASC {return symbol(sym.ASC);}
FORTRAN {return symbol(sym.FORTRAN);}
PROCEDURE {return symbol(sym.PROCEDURE);}
ASSERTION {return symbol(sym.ASSERTION);}
FOUND {return symbol(sym.FOUND);}
PUBLIC {return symbol(sym.PUBLIC);}
AT {return symbol(sym.AT);}
FROM {return symbol(sym.FROM);}
READ {return symbol(sym.READ);}
AUTHORIZATION {return symbol(sym.AUTHORIZATION);}
FULL {return symbol(sym.FULL);}
REAL {return symbol(sym.REAL);}
AVG {return symbol(sym.AVG);}
GET {return symbol(sym.GET);}
REFERENCES {return symbol(sym.REFERENCES);}
BEGIN {return symbol(sym.BEGIN);}
GLOBAL {return symbol(sym.GLOBAL);}
RELATIVE {return symbol(sym.RELATIVE);}
BETWEEN {return symbol(sym.BETWEEN);}
GO {return symbol(sym.GO);}
RESTRICT {return symbol(sym.RESTRICT);}
BIT {return symbol(sym.BIT);}
GOTO {return symbol(sym.GOTO);}
REVOKE {return symbol(sym.REVOKE);}
BIT_LENGTH {return symbol(sym.BIT_LENGTH);}
GRANT {return symbol(sym.GRANT);}
RIGHT {return symbol(sym.RIGHT);}
BOTH {return symbol(sym.BOTH);}
GROUP {return symbol(sym.GROUP);}
ROLLBACK {return symbol(sym.ROLLBACK);}
BY {return symbol(sym.BY);}
HAVING {return symbol(sym.HAVING);}
ROWS {return symbol(sym.ROWS);}
CASCADE {return symbol(sym.CASCADE);}
HOUR {return symbol(sym.HOUR);}
SCHEMA {return symbol(sym.SCHEMA);}
CASCADED {return symbol(sym.CASCADED);}
IDENTITY {return symbol(sym.IDENTITY);}
SCROLL {return symbol(sym.SCROLL);}
CASE {return symbol(sym.CASE);}
IMMEDIATE {return symbol(sym.IMMEDIATE);}
SECOND {return symbol(sym.SECOND);}
CAST {return symbol(sym.CAST);}
IN {return symbol(sym.IN);}
SECTION {return symbol(sym.SECTION);}
CATALOG {return symbol(sym.CATALOG);}
INCLUDE {return symbol(sym.INCLUDE);}
SELECT {return symbol(sym.SELECT);}
CHAR {return symbol(sym.CHAR);}
INDEX {return symbol(sym.INDEX);}
SESSION {return symbol(sym.SESSION);}
CHAR_LENGTH {return symbol(sym.CHAR_LENGTH);}
INDICATOR {return symbol(sym.INDICATOR);}
SESSION_USER {return symbol(sym.SESSION_USER);}
CHARACTER {return symbol(sym.CHARACTER);}
INITIALLY {return symbol(sym.INITIALLY);}
SET {return symbol(sym.SET);}
CHARACTER_LENGTH {return symbol(sym.CHARACTER_LENGTH);}
INNER {return symbol(sym.INNER);}
SIZE {return symbol(sym.SIZE);}
CHECK {return symbol(sym.CHECK);}
INPUT {return symbol(sym.INPUT);}
SMALLINT {return symbol(sym.SMALLINT);}
CLOSE {return symbol(sym.CLOSE);}
INSENSITIVE {return symbol(sym.INSENSITIVE);}
SOME {return symbol(sym.SOME);}
COALESCE {return symbol(sym.COALESCE);}
INSERT {return symbol(sym.INSERT);}
SPACE {return symbol(sym.SPACE);}
COLLATE {return symbol(sym.COLLATE);}
INT {return symbol(sym.INT);}
SQL {return symbol(sym.SQL);}
COLLATION {return symbol(sym.COLLATION);}
INTEGER {return symbol(sym.INTEGER);}
SQLCA {return symbol(sym.SQLCA);}
COLUMN {return symbol(sym.COLUMN);}
INTERSECT {return symbol(sym.INTERSECT);}
SQLCODE {return symbol(sym.SQLCODE);}
COMMIT {return symbol(sym.COMMIT);}
INTERVAL {return symbol(sym.INTERVAL);}
SQLERROR {return symbol(sym.SQLERROR);}
CONNECT {return symbol(sym.CONNECT);}
INTO {return symbol(sym.INTO);}
SQLSTATE {return symbol(sym.SQLSTATE);}
CONNECTION {return symbol(sym.CONNECTION);}
IS {return symbol(sym.IS);}
SQLWARNING {return symbol(sym.SQLWARNING);}
CONSTRAINT {return symbol(sym.CONSTRAINT);}
ISOLATION {return symbol(sym.ISOLATION);}
SUBSTRING {return symbol(sym.SUBSTRING);}
CONSTRAINTS {return symbol(sym.CONSTRAINTS);}
JOIN {return symbol(sym.JOIN);}
SUM {return symbol(sym.SUM);}
CONTINUE {return symbol(sym.CONTINUE);}
KEY {return symbol(sym.KEY);}
SYSTEM_USER {return symbol(sym.SYSTEM_USER);}
CONVERT {return symbol(sym.CONVERT);}
LANGUAGE {return symbol(sym.LANGUAGE);}
TABLE {return symbol(sym.TABLE);}
CORRESPONDING {return symbol(sym.CORRESPONDING);}
LAST {return symbol(sym.LAST);}
TEMPORARY {return symbol(sym.TEMPORARY);}
COUNT {return symbol(sym.COUNT);}
LEADING {return symbol(sym.LEADING);}
THEN {return symbol(sym.THEN);}
CREATE {return symbol(sym.CREATE);}
LEFT {return symbol(sym.LEFT);}
TIME {return symbol(sym.TIME);}
CROSS {return symbol(sym.CROSS);}
LEVEL {return symbol(sym.LEVEL);}
TIMESTAMP {return symbol(sym.TIMESTAMP);}
CURRENT {return symbol(sym.CURRENT);}
LIKE {return symbol(sym.LIKE);}
TIMEZONE_HOUR {return symbol(sym.TIMEZONE_HOUR);}
CURRENT_DATE {return symbol(sym.CURRENT_DATE);}
LOCAL {return symbol(sym.LOCAL);}
TIMEZONE_MINUTE {return symbol(sym.TIMEZONE_MINUTE);}
CURRENT_TIME {return symbol(sym.CURRENT_TIME);}
LOWER {return symbol(sym.LOWER);}
TO {return symbol(sym.TO);}
CURRENT_TIMESTAMP {return symbol(sym.CURRENT_TIMESTAMP);}
MATCH {return symbol(sym.MATCH);}
TRAILING {return symbol(sym.TRAILING);}
CURRENT_USER {return symbol(sym.CURRENT_USER);}
MAX {return symbol(sym.MAX);}
TRANSACTION {return symbol(sym.TRANSACTION);}
CURSOR {return symbol(sym.CURSOR);}
MIN {return symbol(sym.MIN);}
TRANSLATE {return symbol(sym.TRANSLATE);}
DATE {return symbol(sym.DATE);}
MINUTE {return symbol(sym.MINUTE);}
TRANSLATION {return symbol(sym.TRANSLATION);}
DAY {return symbol(sym.DAY);}
MODULE {return symbol(sym.MODULE);}
TRIM {return symbol(sym.TRIM);}
DEALLOCATE {return symbol(sym.DEALLOCATE);}
MONTH {return symbol(sym.MONTH);}
TRUE {return symbol(sym.TRUE);}
DEC {return symbol(sym.DEC);}
NAMES {return symbol(sym.NAMES);}
UNION {return symbol(sym.UNION);}
DECIMAL {return symbol(sym.DECIMAL);}
NATIONAL {return symbol(sym.NATIONAL);}
UNIQUE {return symbol(sym.UNIQUE);}
DECLARE {return symbol(sym.DECLARE);}
NATURAL {return symbol(sym.NATURAL);}
UNKNOWN {return symbol(sym.UNKNOWN);}
DEFAULT {return symbol(sym.DEFAULT);}
NCHAR {return symbol(sym.NCHAR);}
UPDATE {return symbol(sym.UPDATE);}
DEFERRABLE {return symbol(sym.DEFERRABLE);}
NEXT {return symbol(sym.NEXT);}
UPPER {return symbol(sym.UPPER);}
DEFERRED {return symbol(sym.DEFERRED);}
NO {return symbol(sym.NO);}
USAGE {return symbol(sym.USAGE);}
DELETE {return symbol(sym.DELETE);}
NONE {return symbol(sym.NONE);}
USER {return symbol(sym.USER);}
DESC {return symbol(sym.DESC);}
NOT {return symbol(sym.NOT);}
USING {return symbol(sym.USING);}
DESCRIBE {return symbol(sym.DESCRIBE);}
NULL {return symbol(sym.NULL);}
VALUE {return symbol(sym.VALUE);}
DESCRIPTOR {return symbol(sym.DESCRIPTOR);}
NULLIF {return symbol(sym.NULLIF);}
VALUES {return symbol(sym.VALUES);}
DIAGNOSTICS {return symbol(sym.DIAGNOSTICS);}
NUMERIC {return symbol(sym.NUMERIC);}
VARCHAR {return symbol(sym.VARCHAR);}
DISCONNECT {return symbol(sym.DISCONNECT);}
OCTET_LENGTH {return symbol(sym.OCTET_LENGTH);}
VARYING {return symbol(sym.VARYING);}
DISTINCT {return symbol(sym.DISTINCT);}
OF {return symbol(sym.OF);}
VIEW {return symbol(sym.VIEW);}
DOMAIN {return symbol(sym.DOMAIN);}
ON {return symbol(sym.ON);}
WHEN {return symbol(sym.WHEN);}
DOUBLE {return symbol(sym.DOUBLE);}
ONLY {return symbol(sym.ONLY);}
WHENEVER {return symbol(sym.WHENEVER);}
DROP {return symbol(sym.DROP);}
OPEN {return symbol(sym.OPEN);}
WHERE {return symbol(sym.WHERE);}
ELSE {return symbol(sym.ELSE);}
OPTION {return symbol(sym.OPTION);}
WITH {return symbol(sym.WITH);}
END {return symbol(sym.END);}
OR {return symbol(sym.OR);}
WORK {return symbol(sym.WORK);}
ORDER {return symbol(sym.ORDER);}
WRITE {return symbol(sym.WRITE);}
ESCAPE {return symbol(sym.ESCAPE);}
OUTER {return symbol(sym.OUTER);}
YEAR {return symbol(sym.YEAR);}
EXCEPT {return symbol(sym.EXCEPT);}
OUTPUT {return symbol(sym.OUTPUT);}
ZONE {return symbol(sym.ZONE);}
EXCEPTION {return symbol(sym.EXCEPTION);}

{D}+("."){D}*(("E"|"e")({D}+)) {return symbol(sym.ErrorFloatMas);}
({D}+)(".")({D}*)(("E"|"e")("+"|"-")({D}+))? {return symbol(sym.Float);}
("."){D}+(("E"|"e")("+"|"-")({D}+))? {return symbol(sym.ErrorFloatPunto);}
{D}+("."){D}*(("+"|"-")({D}+)) {return symbol(sym.ErrorFloatE);}

({LM}|{Lm})({LM}|{Lm}|{D})* {if(yylength()>31){return symbol(sym.ErrorCadenaLarga);}else{return symbol(sym.Identificador);}}
("-"{D}+)|{D}+ {return symbol(sym.Numero);}
("0") | ("1") | ("NULL") {return symbol(sym.Bit);}

{cadena} {return symbol(sym.Cadena);}

"," {return symbol(sym.Coma);}
"=" {return symbol(sym.Igual);}
"+" {return symbol(sym.Suma);}
"-" {return symbol(sym.Resta);}
"*" {return symbol(sym.Multiplicacion);}
"/" {return symbol(sym.Division);}
"%" {return symbol(sym.Porcentaje);}
"<" {return symbol(sym.MenorQue);}
">" {return symbol(sym.MayorQue);}
"<=" {return symbol(sym.MenorIgual);}
">=" {return symbol(sym.MayorIgual);}
"==" {return symbol(sym.IgualA);}
"!=" {return symbol(sym.DiferenteDe);}
"&&" {return symbol(sym.And);}
"||" {return symbol(sym.Or);}
"!" {return symbol(sym.SignoAdmiracionInverso);}
";" {return symbol(sym.PuntoComa);}
";GO" | "GO;" {return symbol(sym.Ambos);}
"." {return symbol(sym.Punto);}
//"[" {return symbol(sym.AbrirCorchete);}
//"]" {return symbol(sym.CerrarCorchete);}
"(" | "[" {return symbol(sym.AbrirParentesis);}
")" | "]" {return symbol(sym.CerrarParentesis);}
"{" {return symbol(sym.AbrirLlave);}
"}" {return symbol(sym.CerrarLlave);}
"[]" {return symbol(sym.DobleCorchete);}
"()" {return symbol(sym.DobleParentesis);}
"{}" {return symbol(sym.DobleLlave);}
"@" {return symbol(sym.Arroba);}
"#" {return symbol(sym.Numeral);}
"##" {return symbol(sym.DobleNumeral);}


/* Si el token contenido en la entrada no coincide con ninguna regla
    entonces se marca un token ilegal */
//[^]                    { throw new Error("Caracter ilegal <"+yytext()+">"); }
. {return symbol(sym.ERROR);}
