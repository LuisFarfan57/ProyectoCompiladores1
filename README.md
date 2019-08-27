# ProyectoCompiladores1

Lo primero que debe realizarse en el proyecto es cargar el archivo .flex que generará la clase .java con el analizador léxico (El archivo se encuentra dentro del proyecto, en la carpeta src). Luego se debe seleccionar el archivo .txt que se quiere analizar. Después de analizar dicho archivo la aplicación preguntará la ubicación donde se quiere guardar el archivo .out con los resultados del analisis.

El analizador maneja cada error de forma independiente, se creó una expresión regular para cada error posible dentro de lo solicitado, lo cual incluye errores en los float, enteros, identificadores, comentarios, cadenas, etc. Es robusto porque fue probado exaustivamente con varios tipos de archivos y todos los errores descritos en el enunciado del proyecto son reconocidos y mostrados correctamente con la linea y columna correspondiente al token.
