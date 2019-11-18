package AnalisiSemanticoSQL;
import java_cup.runtime.Symbol;
%%

%public
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%char
%line
%column
%unicode

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]+

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment}

TraditionalComment = "/*" ~"*/" 
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*
EndOfLineComment = "--" {InputCharacter}* {LineTerminator}?

/* identifiers */
Identifier = [:jletter:](([:jletter:]|[:jletterdigit:]|"_")*)

ErrorIdentifier = ([:jletterdigit:]|"_")(([:jletter:]|[:jletterdigit:]|"_")*)

Boolean = (1)|(0)| NULL

DecIntegerLiteral = [0-9][0-9]*

/* floating point literals */
FloatLiteral  = ({FLit1}) {Exponent}?

FLit1    = [0-9]+\.[0-9]*
Exponent = [eE] [+-]? [0-9]+

ErrorString = "'" [^\r\n\u0027]* {LineTerminator}?

String = "'" [^\r\n\u0027]* "'"

%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}

%%

    "ADD" { return new Symbol(sym.ADD, yychar, yyline, yytext() );}
    "EXTERNAL" { return new Symbol(sym.EXTERNAL, yychar, yyline, yytext() );}
    "PROCEDURE" { return new Symbol(sym.PROCEDURE, yychar, yyline, yytext() );}
    "ALL" { return new Symbol(sym.ALL, yychar, yyline, yytext() );}
    "OUT" { return new Symbol(sym.OUT, yychar, yyline, yytext() );}
    "READONLY" { return new Symbol(sym.READONLY, yychar, yyline, yytext() );}
    "ENCRYPTION" { return new Symbol(sym.ENCRYPTION, yychar, yyline, yytext() );}
    "RETURNS" { return new Symbol(sym.RETURNS, yychar, yyline, yytext() );}
    "SCHEMABINDING" { return new Symbol(sym.SCHEMABINDING, yychar, yyline, yytext() );}
    "CALLED" { return new Symbol(sym.CALLED, yychar, yyline, yytext() );}
    "INLINE" { return new Symbol(sym.INLINE, yychar, yyline, yytext() );}
    "CALLER" { return new Symbol(sym.CALLER, yychar, yyline, yytext() );}
    "SELF" { return new Symbol(sym.SELF, yychar, yyline, yytext() );}
    "OWNER" { return new Symbol(sym.OWNER, yychar, yyline, yytext() );}
    "RECOMPILE" { return new Symbol(sym.RECOMPILE, yychar, yyline, yytext() );}
    "MARK" { return new Symbol(sym.MARK, yychar, yyline, yytext() );}
    "FORWARD_ONLY" { return new Symbol(sym.FORWARDONLY, yychar, yyline, yytext() );}
    "STATIC" { return new Symbol(sym.STATIC, yychar, yyline, yytext() );}
    "KEYSET" { return new Symbol(sym.KEYSET, yychar, yyline, yytext() );}
    "DYNAMIC" { return new Symbol(sym.DYNAMIC, yychar, yyline, yytext() );}
    "FAST_FORWARD" { return new Symbol(sym.FASTFORWARD, yychar, yyline, yytext() );}
    "READ_ONLY" { return new Symbol(sym.READONLY, yychar, yyline, yytext() );}
    "SCROLL_LOCKS" { return new Symbol(sym.SCROLLLOCKS, yychar, yyline, yytext() );}
    "OPTIMISTIC" { return new Symbol(sym.OPTIMISTIC, yychar, yyline, yytext() );}
    "TYPE_WARNING" { return new Symbol(sym.TYPEWARNING, yychar, yyline, yytext() );}
    "FETCH" { return new Symbol(sym.FETCH, yychar, yyline, yytext() );}
    "PUBLIC" { return new Symbol(sym.PUBLIC, yychar, yyline, yytext() );}
    "ALTER" { return new Symbol(sym.ALTER, yychar, yyline, yytext() );}
    "FILE" { return new Symbol(sym.FILE, yychar, yyline, yytext() );}
    "RAISERROR" { return new Symbol(sym.RAISERROR, yychar, yyline, yytext() );}
    "AND" { return new Symbol(sym.AND, yychar, yyline, yytext() );}
    "FILLFACTOR" { return new Symbol(sym.FILLFACTOR, yychar, yyline, yytext() );}
    "READ" { return new Symbol(sym.READ, yychar, yyline, yytext() );}
    "ANY" { return new Symbol(sym.ANY, yychar, yyline, yytext() );}
    "FOR" { return new Symbol(sym.FOR, yychar, yyline, yytext() );}
    "READTEXT" { return new Symbol(sym.READTEXT, yychar, yyline, yytext() );}
    "AS" { return new Symbol(sym.AS, yychar, yyline, yytext() );}
    "FOREIGN" { return new Symbol(sym.FOREIGN, yychar, yyline, yytext() );}
    "RECONFIGURE" { return new Symbol(sym.RECONFIGURE, yychar, yyline, yytext() );}
    "ASC" { return new Symbol(sym.ASC, yychar, yyline, yytext() );}
    "FREETEXT" { return new Symbol(sym.FREETEXT, yychar, yyline, yytext() );}
    "REFERENCES" { return new Symbol(sym.REFERENCES, yychar, yyline, yytext() );}
    "AUTHORIZATION" { return new Symbol(sym.AUTHORIZATION, yychar, yyline, yytext() );}
    "FREETEXTTABLE" { return new Symbol(sym.FREETEXTTABLE, yychar, yyline, yytext() );}
    "REPLICATION" { return new Symbol(sym.REPLICATION, yychar, yyline, yytext() );}
    "BACKUP" { return new Symbol(sym.BACKUP, yychar, yyline, yytext() );}
    "FROM" { return new Symbol(sym.FROM, yychar, yyline, yytext() );}
    "RESTORE" { return new Symbol(sym.RESTORE, yychar, yyline, yytext() );}
    "BEGIN" { return new Symbol(sym.BEGIN, yychar, yyline, yytext() );}
    "FULL" { return new Symbol(sym.FULL, yychar, yyline, yytext() );}
    "RESTRICT" { return new Symbol(sym.RESTRICT, yychar, yyline, yytext() );}
    "BETWEEN" { return new Symbol(sym.BETWEEN, yychar, yyline, yytext() );}
    "FUNCTION" { return new Symbol(sym.FUNCTION, yychar, yyline, yytext() );}
    "RETURN" { return new Symbol(sym.RETURN, yychar, yyline, yytext() );}
    "BREAK" { return new Symbol(sym.BREAK, yychar, yyline, yytext() );}
    "GOTO" { return new Symbol(sym.GOTO, yychar, yyline, yytext() );}
    "REVERT" { return new Symbol(sym.REVERT, yychar, yyline, yytext() );}
    "BROWSE" { return new Symbol(sym.BROWSE, yychar, yyline, yytext() );}
    "GRANT" { return new Symbol(sym.GRANT, yychar, yyline, yytext() );}
    "REVOKE" { return new Symbol(sym.REVOKE, yychar, yyline, yytext() );}
    "BULK" { return new Symbol(sym.BULK, yychar, yyline, yytext() );}
    "GROUP" { return new Symbol(sym.GROUP, yychar, yyline, yytext() );}
    "RIGHT" { return new Symbol(sym.RIGHT, yychar, yyline, yytext() );}
    "BY" { return new Symbol(sym.BY, yychar, yyline, yytext() );}
    "HAVING" { return new Symbol(sym.HAVING, yychar, yyline, yytext() );}
    "ROLLBACK" { return new Symbol(sym.ROLLBACK, yychar, yyline, yytext() );}
    "CASCADE" { return new Symbol(sym.CASCADE, yychar, yyline, yytext() );}
    "HOLDLOCK" { return new Symbol(sym.HOLDLOCK, yychar, yyline, yytext() );}
    "ROWCOUNT" { return new Symbol(sym.ROWCOUNT, yychar, yyline, yytext() );}
    "CASE" { return new Symbol(sym.CASE, yychar, yyline, yytext() );}
    "IDENTITY" { return new Symbol(sym.IDENTITY, yychar, yyline, yytext() );}
    "ROWGUIDCOL" { return new Symbol(sym.ROWGUIDCOL, yychar, yyline, yytext() );}
    "CHECK" { return new Symbol(sym.CHECK, yychar, yyline, yytext() );}
    "IDENTITY_INSERT" { return new Symbol(sym.IDENTITY_INSERT, yychar, yyline, yytext() );}
    "RULE" { return new Symbol(sym.RULE, yychar, yyline, yytext() );}
    "CHECKPOINT" { return new Symbol(sym.CHECKPOINT, yychar, yyline, yytext() );}
    "IDENTITYCOL" { return new Symbol(sym.IDENTITYCOL, yychar, yyline, yytext() );}
    "SAVE" { return new Symbol(sym.SAVE, yychar, yyline, yytext() );}
    "CLOSE" { return new Symbol(sym.CLOSE, yychar, yyline, yytext() );}
    "IF" { return new Symbol(sym.IF, yychar, yyline, yytext() );}
    "SCHEMA" { return new Symbol(sym.SCHEMA, yychar, yyline, yytext() );}
    "CLUSTERED" { return new Symbol(sym.CLUSTERED, yychar, yyline, yytext() );}
    "IN" { return new Symbol(sym.IN, yychar, yyline, yytext() );}
    "SECURITYAUDIT" { return new Symbol(sym.SECURITYAUDIT, yychar, yyline, yytext() );}
    "COALESCE" { return new Symbol(sym.COALESCE, yychar, yyline, yytext() );}
    "INDEX" { return new Symbol(sym.INDEX, yychar, yyline, yytext() );}
    "SELECT" { return new Symbol(sym.SELECT, yychar, yyline, yytext() );}
    "COLLATE" { return new Symbol(sym.COLLATE, yychar, yyline, yytext() );}
    "INNER" { return new Symbol(sym.INNER, yychar, yyline, yytext() );}
    "SEMANTICKEYPHRASETABLE" { return new Symbol(sym.SEMANTICKEYPHRASETABLE, yychar, yyline, yytext() );}
    "COLUMN" { return new Symbol(sym.COLUMN, yychar, yyline, yytext() );}
    "INSERT" { return new Symbol(sym.INSERT, yychar, yyline, yytext() );}
    "SEMANTICSIMILARITYDETAILSTABLE" { return new Symbol(sym.SEMANTICSIMILARITYDETAILSTABLE, yychar, yyline, yytext() );}
    "COMMIT" { return new Symbol(sym.COMMIT, yychar, yyline, yytext() );}
    "INTERSECT" { return new Symbol(sym.INTERSECT, yychar, yyline, yytext() );}
    "SEMANTICSIMILARITYTABLE" { return new Symbol(sym.SEMANTICSIMILARITYTABLE, yychar, yyline, yytext() );}
    "COMPUTE" { return new Symbol(sym.COMPUTE, yychar, yyline, yytext() );}
    "INTO" { return new Symbol(sym.INTO, yychar, yyline, yytext() );}
    "SESSION_USER" { return new Symbol(sym.SESSION_USER, yychar, yyline, yytext() );}
    "CONSTRAINT" { return new Symbol(sym.CONSTRAINT, yychar, yyline, yytext() );}
    "IS" { return new Symbol(sym.IS, yychar, yyline, yytext() );}
    "SET" { return new Symbol(sym.SET, yychar, yyline, yytext() );}
    "CONTAINS" { return new Symbol(sym.CONTAINS, yychar, yyline, yytext() );}
    "JOIN" { return new Symbol(sym.JOIN, yychar, yyline, yytext() );}
    "SETUSER" { return new Symbol(sym.SETUSER, yychar, yyline, yytext() );}
    "CONTAINSTABLE" { return new Symbol(sym.CONTAINSTABLE, yychar, yyline, yytext() );}
    "KEY" { return new Symbol(sym.KEY, yychar, yyline, yytext() );}
    "SHUTDOWN" { return new Symbol(sym.SHUTDOWN, yychar, yyline, yytext() );}
    "CONTINUE" { return new Symbol(sym.CONTINUE, yychar, yyline, yytext() );}
    "KILL" { return new Symbol(sym.KILL, yychar, yyline, yytext() );}
    "SOME" { return new Symbol(sym.SOME, yychar, yyline, yytext() );}
    "CONVERT" { return new Symbol(sym.CONVERT, yychar, yyline, yytext() );}
    "LEFT" { return new Symbol(sym.LEFT, yychar, yyline, yytext() );}
    "STATISTICS" { return new Symbol(sym.STATISTICS, yychar, yyline, yytext() );}
    "CREATE" { return new Symbol(sym.CREATE, yychar, yyline, yytext() );}
    "LIKE" { return new Symbol(sym.LIKE, yychar, yyline, yytext() );}
    "SYSTEM_USER" { return new Symbol(sym.SYSTEM_USER, yychar, yyline, yytext() );}
    "CROSS" { return new Symbol(sym.CROSS, yychar, yyline, yytext() );}
    "LINENO" { return new Symbol(sym.LINENO, yychar, yyline, yytext() );}
    "TABLE" { return new Symbol(sym.TABLE, yychar, yyline, yytext() );}
    "CURRENT" { return new Symbol(sym.CURRENT, yychar, yyline, yytext() );}
    "LOAD" { return new Symbol(sym.LOAD, yychar, yyline, yytext() );}
    "TABLESAMPLE" { return new Symbol(sym.TABLESAMPLE, yychar, yyline, yytext() );}
    "CURRENT_DATE" { return new Symbol(sym.CURRENT_DATE, yychar, yyline, yytext() );}
    "MERGE" { return new Symbol(sym.MERGE, yychar, yyline, yytext() );}
    "TEXTSIZE" { return new Symbol(sym.TEXTSIZE, yychar, yyline, yytext() );}
    "CURRENT_TIME" { return new Symbol(sym.CURRENT_TIME, yychar, yyline, yytext() );}
    "NATIONAL" { return new Symbol(sym.NATIONAL, yychar, yyline, yytext() );}
    "THEN" { return new Symbol(sym.THEN, yychar, yyline, yytext() );}
    "CURRENT_TIMESTAMP" { return new Symbol(sym.CURRENT_TIMESTAMP, yychar, yyline, yytext() );}
    "NOCHECK" { return new Symbol(sym.NOCHECK, yychar, yyline, yytext() );}
    "TO" { return new Symbol(sym.TO, yychar, yyline, yytext() );}
    "CURRENT_USER" { return new Symbol(sym.CURRENT_USER, yychar, yyline, yytext() );}
    "NONCLUSTERED" { return new Symbol(sym.NONCLUSTERED, yychar, yyline, yytext() );}
    "TOP" { return new Symbol(sym.TOP, yychar, yyline, yytext() );}
    "CURSOR" { return new Symbol(sym.CURSOR, yychar, yyline, yytext() );}
    "NOT" { return new Symbol(sym.NOT, yychar, yyline, yytext() );}
    "TRAN" { return new Symbol(sym.TRAN, yychar, yyline, yytext() );}
    "DATABASE" { return new Symbol(sym.DATABASE, yychar, yyline, yytext() );}
    "NULL" { return new Symbol(sym.NULL, yychar, yyline, yytext() );}
    "TRANSACTION" { return new Symbol(sym.TRANSACTION, yychar, yyline, yytext() );}
    "DBCC" { return new Symbol(sym.DBCC, yychar, yyline, yytext() );}
    "NULLIF" { return new Symbol(sym.NULLIF, yychar, yyline, yytext() );}
    "TRIGGER" { return new Symbol(sym.TRIGGER, yychar, yyline, yytext() );}
    "DEALLOCATE" { return new Symbol(sym.DEALLOCATE, yychar, yyline, yytext() );}
    "OF" { return new Symbol(sym.OF, yychar, yyline, yytext() );}
    "TRUNCATE" { return new Symbol(sym.TRUNCATE, yychar, yyline, yytext() );}
    "DECLARE" { return new Symbol(sym.DECLARE, yychar, yyline, yytext() );}
    "OFF" { return new Symbol(sym.OFF, yychar, yyline, yytext() );}
    "TRY_CONVERT" { return new Symbol(sym.TRY_CONVERT, yychar, yyline, yytext() );}
    "DEFAULT" { return new Symbol(sym.DEFAULT, yychar, yyline, yytext() );}
    "OFFSETS" { return new Symbol(sym.OFFSETS, yychar, yyline, yytext() );}
    "TSEQUAL" { return new Symbol(sym.TSEQUAL, yychar, yyline, yytext() );}
    "DELETE" { return new Symbol(sym.DELETE, yychar, yyline, yytext() );}
    "ON" { return new Symbol(sym.ON, yychar, yyline, yytext() );}
    "UNION" { return new Symbol(sym.UNION, yychar, yyline, yytext() );}
    "DENY" { return new Symbol(sym.DENY, yychar, yyline, yytext() );}
    "OPEN" { return new Symbol(sym.OPEN, yychar, yyline, yytext() );}
    "UNIQUE" { return new Symbol(sym.UNIQUE, yychar, yyline, yytext() );}
    "DESC" { return new Symbol(sym.DESC, yychar, yyline, yytext() );}
    "OPENDATASOURCE" { return new Symbol(sym.OPENDATASOURCE, yychar, yyline, yytext() );}
    "UNPIVOT" { return new Symbol(sym.UNPIVOT, yychar, yyline, yytext() );}
    "DISK" { return new Symbol(sym.DISK, yychar, yyline, yytext() );}
    "OPENQUERY" { return new Symbol(sym.OPENQUERY, yychar, yyline, yytext() );}
    "UPDATE" { return new Symbol(sym.UPDATE, yychar, yyline, yytext() );}
    "DISTINCT" { return new Symbol(sym.DISTINCT, yychar, yyline, yytext() );}
    "OPENROWSET" { return new Symbol(sym.OPENROWSET, yychar, yyline, yytext() );}
    "UPDATETEXT" { return new Symbol(sym.UPDATETEXT, yychar, yyline, yytext() );}
    "DISTRIBUTED" { return new Symbol(sym.DISTRIBUTED, yychar, yyline, yytext() );}
    "OPENXML" { return new Symbol(sym.OPENXML, yychar, yyline, yytext() );}
    "USE" { return new Symbol(sym.USE, yychar, yyline, yytext() );}
    "DOUBLE" { return new Symbol(sym.DOUBLE, yychar, yyline, yytext() );}
    "OPTION" { return new Symbol(sym.OPTION, yychar, yyline, yytext() );}
    "USER" { return new Symbol(sym.USER, yychar, yyline, yytext() );}
    "DROP" { return new Symbol(sym.DROP, yychar, yyline, yytext() );}
    "OR" { return new Symbol(sym.OR, yychar, yyline, yytext() );}
    "VALUES" { return new Symbol(sym.VALUES, yychar, yyline, yytext() );}
    "DUMP" { return new Symbol(sym.DUMP, yychar, yyline, yytext() );}
    "ORDER" { return new Symbol(sym.ORDER, yychar, yyline, yytext() );}
    "VARYING" { return new Symbol(sym.VARYING, yychar, yyline, yytext() );}
    "ELSE" { return new Symbol(sym.ELSE, yychar, yyline, yytext() );}
    "OUTER" { return new Symbol(sym.OUTER, yychar, yyline, yytext() );}
    "VIEW" { return new Symbol(sym.VIEW, yychar, yyline, yytext() );}
    "END" { return new Symbol(sym.END, yychar, yyline, yytext() );}
    "OVER" { return new Symbol(sym.OVER, yychar, yyline, yytext() );}
    "WAITFOR" { return new Symbol(sym.WAITFOR, yychar, yyline, yytext() );}
    "ERRLVL" { return new Symbol(sym.ERRLVL, yychar, yyline, yytext() );}
    "PERCENT" { return new Symbol(sym.PERCENT, yychar, yyline, yytext() );}
    "WHEN" { return new Symbol(sym.WHEN, yychar, yyline, yytext() );}
    "ESCAPE" { return new Symbol(sym.ESCAPE, yychar, yyline, yytext() );}
    "PIVOT" { return new Symbol(sym.PIVOT, yychar, yyline, yytext() );}
    "WHERE" { return new Symbol(sym.WHERE, yychar, yyline, yytext() );}
    "EXCEPT" { return new Symbol(sym.EXCEPT, yychar, yyline, yytext() );}
    "PLAN" { return new Symbol(sym.PLAN, yychar, yyline, yytext() );}
    "WHILE" { return new Symbol(sym.WHILE, yychar, yyline, yytext() );}
    "EXEC" { return new Symbol(sym.EXEC, yychar, yyline, yytext() );}
    "PRECISION" { return new Symbol(sym.PRECISION, yychar, yyline, yytext() );}
    "WITH" { return new Symbol(sym.WITH, yychar, yyline, yytext() );}
    "EXECUTE" { return new Symbol(sym.EXECUTE, yychar, yyline, yytext() );}
    "PRIMARY" { return new Symbol(sym.PRIMARY, yychar, yyline, yytext() );}
    "WITHIN GROUP" { return new Symbol(sym.WITHINGROUP, yychar, yyline, yytext() );}
    "EXISTS" { return new Symbol(sym.EXISTS, yychar, yyline, yytext() );}
    "PRINT" { return new Symbol(sym.PRINT, yychar, yyline, yytext() );}
    "WRITETEXT" { return new Symbol(sym.WRITETEXT, yychar, yyline, yytext() );}
    "EXIT" { return new Symbol(sym.EXIT, yychar, yyline, yytext() );}
    "PROC" { return new Symbol(sym.PROC, yychar, yyline, yytext() );}
    "INCLUDE" { return new Symbol(sym.INCLUDE, yychar, yyline, yytext() );}
    "IMMEDIATE" { return new Symbol(sym.IMMEDIATE, yychar, yyline, yytext() );}
    "GO" { return new Symbol(sym.GO, yychar, yyline, yytext() );}
    "SUM" { return new Symbol(sym.SUM, yychar, yyline, yytext() );}
    "AVG" { return new Symbol(sym.AVG, yychar, yyline, yytext() );}
    "COUNT" { return new Symbol(sym.COUNT, yychar, yyline, yytext() );}
    "MAX" { return new Symbol(sym.MAX, yychar, yyline, yytext() );}
    "MIN" { return new Symbol(sym.MIN, yychar, yyline, yytext() );}
    "KB" { return new Symbol(sym.KB, yychar, yyline, yytext() );}
    "GB" { return new Symbol(sym.GB, yychar, yyline, yytext() );}
    "TB" { return new Symbol(sym.TB, yychar, yyline, yytext() );}
    "MB" { return new Symbol(sym.MB, yychar, yyline, yytext() );}
    "BIGINT" { return new Symbol(sym.BIGINT, yychar, yyline, yytext() );}
    "INT" { return new Symbol(sym.INT, yychar, yyline, yytext() );}
    "SMALLINT" { return new Symbol(sym.SMALLINT, yychar, yyline, yytext() );}
    "TINYINT" { return new Symbol(sym.TINYINT, yychar, yyline, yytext() );}
    "BIT" { return new Symbol(sym.BIT, yychar, yyline, yytext() );}
    "DECIMAL" { return new Symbol(sym.DECIMAL, yychar, yyline, yytext() );}
    "MONEY" { return new Symbol(sym.MONEY, yychar, yyline, yytext() );}
    "NUMERIC" { return new Symbol(sym.NUMERIC, yychar, yyline, yytext() );}
    "SMALLMONEY" { return new Symbol(sym.SMALLMONEY, yychar, yyline, yytext() );}
    "FLOAT" { return new Symbol(sym.FLOAT, yychar, yyline, yytext() );}
    "REAL" { return new Symbol(sym.REAL, yychar, yyline, yytext() );}
    "DATE" { return new Symbol(sym.DATE, yychar, yyline, yytext() );}
    "DATETIME" { return new Symbol(sym.DATETIME, yychar, yyline, yytext() );}
    "DATETIME2" { return new Symbol(sym.DATETIME2, yychar, yyline, yytext() );}
    "DATETIMEOFFSET" { return new Symbol(sym.DATETIMEOFFSET, yychar, yyline, yytext() );}
    "SMALLDATETIME" { return new Symbol(sym.SMALLDATETIME, yychar, yyline, yytext() );}
    "TIME" { return new Symbol(sym.TIME, yychar, yyline, yytext() );}
    "CHAR" { return new Symbol(sym.CHAR, yychar, yyline, yytext() );}
    "TEXT" { return new Symbol(sym.TEXT, yychar, yyline, yytext() );}
    "VARCHAR" { return new Symbol(sym.VARCHAR, yychar, yyline, yytext() );}
    "NCHAR" { return new Symbol(sym.NCHAR, yychar, yyline, yytext() );}
    "NTEXT" { return new Symbol(sym.NTEXT, yychar, yyline, yytext() );}
    "NVARCHAR" { return new Symbol(sym.NVARCHAR, yychar, yyline, yytext() );}
    "BINARY" { return new Symbol(sym.BINARY, yychar, yyline, yytext() );}
    "IMAGE" { return new Symbol(sym.IMAGE, yychar, yyline, yytext() );}
    "VARBINARY" { return new Symbol(sym.VARBINARY, yychar, yyline, yytext() );}
    "FILENAME" { return new Symbol(sym.FILENAME, yychar, yyline, yytext() ); }
    "NAME" { return new Symbol(sym.NAME, yychar, yyline, yytext() ); }
    "NAMES" { return new Symbol(sym.NAMES, yychar, yyline, yytext() ); }
    "SIZE" { return new Symbol(sym.SIZE, yychar, yyline, yytext() ); }
    "MAXSIZE" { return new Symbol(sym.MAXSIZE, yychar, yyline, yytext() ); }
    "FILEGROWTH" { return new Symbol(sym.FILEGROWTH, yychar, yyline, yytext() ); }
    "UNLIMITED" { return new Symbol(sym.UNLIMITED, yychar, yyline, yytext() ); }
    "OUTPUT" { return new Symbol(sym.OUTPUT, yychar, yyline, yytext() ); }
    "INPUT" { return new Symbol(sym.INPUT, yychar, yyline, yytext() ); }
    "LOCAL" { return new Symbol(sym.LOCAL, yychar, yyline, yytext() ); }
    "GLOBAL" { return new Symbol(sym.GLOBAL, yychar, yyline, yytext() ); }
    "SCROLL" { return new Symbol(sym.SCROLL, yychar, yyline, yytext() ); }

    {Boolean} { return new Symbol(sym.Bit, yychar, yyline, yytext() );}

    "+" { return new Symbol(sym.Suma, yychar, yyline, yytext() );}
    "-" { return new Symbol(sym.Resta, yychar, yyline, yytext() );}
    "*" { return new Symbol(sym.Mul, yychar, yyline, yytext() );}
    "/" { return new Symbol(sym.Div, yychar, yyline, yytext() );}
    "%" { return new Symbol(sym.Porce, yychar, yyline, yytext() );}
    ";" { return new Symbol(sym.PuntoYComa, yychar, yyline, yytext() );}
    "," { return new Symbol(sym.Coma, yychar, yyline, yytext() );}
    "." { return new Symbol(sym.Punto, yychar, yyline, yytext() );}
    "[" { return new Symbol(sym.Acorchete, yychar, yyline, yytext() );}
    "]" { return new Symbol(sym.Ccorchete, yychar, yyline, yytext() );}
    "(" { return new Symbol(sym.Aparentesis, yychar, yyline, yytext() );}
    ")" { return new Symbol(sym.Cparentesis, yychar, yyline, yytext() );}
    "{" { return new Symbol(sym.Allave, yychar, yyline, yytext() );}
    "}" { return new Symbol(sym.Cllave, yychar, yyline, yytext() );}
    "[]" { return new Symbol(sym.Dcorchete, yychar, yyline, yytext() );}
    "()" { return new Symbol(sym.Dparentesis, yychar, yyline, yytext() );}
    "{}" { return new Symbol(sym.Dllaves, yychar, yyline, yytext() );}
    "@" { return new Symbol(sym.Arroba, yychar, yyline, yytext() );}
    "#" { return new Symbol(sym.Hashtag, yychar, yyline, yytext() );}
    "##" { return new Symbol(sym.Dhashtag, yychar, yyline, yytext() );}
    "<" { return new Symbol(sym.Menor, yychar, yyline, yytext() );}
    "<=" { return new Symbol(sym.MenorIgual, yychar, yyline, yytext() );}
    ">" { return new Symbol(sym.Mayor, yychar, yyline, yytext() );}
    ">=" { return new Symbol(sym.MayorIgual, yychar, yyline, yytext() );}
    "==" { return new Symbol(sym.Digual, yychar, yyline, yytext() );}
    "!=" { return new Symbol(sym.Diferente, yychar, yyline, yytext() );}
    "&&" { return new Symbol(sym.Dampersand, yychar, yyline, yytext() );}
    "||" { return new Symbol(sym.Dbarra, yychar, yyline, yytext() );}
    "!" { return new Symbol(sym.Exclamacion, yychar, yyline, yytext() );}
    "=" { return new Symbol(sym.Igual, yychar, yyline, yytext() );}

    {DecIntegerLiteral} { return new Symbol(sym.Integer, yychar, yyline, yytext() );}

    {FloatLiteral} { return new Symbol(sym.Float, yychar, yyline, yytext() );}

    {ErrorString} { return new Symbol(sym.VarcharError, yychar, yyline, yytext() );}

    {String} { return new Symbol(sym.Varchar, yychar, yyline, yytext() );}

    {Comment} {/*Ignore*/}

    {WhiteSpace}+ { /* skip */ }

    {Identifier} { return new Symbol(sym.Identificador, yychar, yyline, yytext() );}

    {ErrorIdentifier} { return new Symbol(sym.IdentificadorError, yychar, yyline, yytext() ); }

/* error */
    . {return new Symbol(sym.ERROR, yychar, yyline, yytext() );}
