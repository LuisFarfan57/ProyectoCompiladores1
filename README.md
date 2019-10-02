# ProyectoCompiladores1

Primero se debe seleccionar el archivo sql que se desea analizar sintácticamente. Automáticamente el programa identificará los errores que tenga el archivo y los mostrará en un cuadro de texto.

Para la realización del proyecto primero se hicieron las gramáticas del lenguaje MiniSQL. Una vez con la gramática realizada se procedió a la programación. Se realizó una función por cada producción en la gramática en donde se regresa un valor booleano indicando si el análisis es correcto o no. Este método se usó exactamente igual para todas las funciones. El código va moviendose recursivamente entre cada producción y al momento de detectar un error se sale de todas las funciones anteriores para ir a buscar el fin del Statement delimitado por un GO o ";".

El proyecto consistía en un analizador sintáctico tipo LR(0) por lo que en algunas ocasiones el uso de un LookAhead (LR(1)) habria simplificado la realización de las gramáticas.

