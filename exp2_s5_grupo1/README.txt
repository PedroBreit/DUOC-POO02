=========================================================
Explicación de Cumplimiento de Requisitos S4 y S5
Proyecto: Biblioteca DUOC UC
=========================================================

CLAVE DE ADMINISTRACIÓN
---------------------------------------------------------
El sistema implementa un menú de administración al que solo puede accederse ingresando la clave:
ADMIN

Para acceder a la administración, selecciona la opción 3 del menú principal e ingresa la clave cuando el sistema lo solicite.

---------------------------------------------------------
REQUISITOS S4 - Semana 4
---------------------------------------------------------

1. Uso de ArrayList y HashMap:
   - Se utiliza un ArrayList para almacenar objetos de la clase 'Libro' en la clase 'Biblioteca'.
   - Se utiliza un HashMap para almacenar objetos de la clase 'Usuario' en la clase 'RegistroUsuario', usando el RUT como clave única.

2. Integración de archivos externos:
   - Se emplean archivos CSV y TXT para persistir la información de libros y usuarios.
   - Clases utilizadas: 'BufferedReader' para leer archivos, 'FileWriter' y 'PrintWriter' para escribir archivos.
   - Ejemplo: los métodos cargarLibros() y guardarLibros() en 'Biblioteca', y cargarUsuarios() y guardarUsuarios() en 'RegistroUsuario'.

3. Excepciones personalizadas y manejo de errores:
   a) Búsqueda de un libro inexistente
      - Se implementa la excepción personalizada 'LibroNoEncontradoException', que es lanzada cuando no se encuentra un libro en el método buscarLibroPorIsbn().
      - Se usa un bloque try-catch en los lugares donde se busca un libro y se informa adecuadamente al usuario.
   b) Préstamo de un libro ya prestado
      - Se implementa la excepción personalizada 'LibroYaPrestadoException', lanzada cuando se intenta prestar un libro ya arrendado.
   c) Introducción de datos incorrectos
      - Se usa 'InputMismatchException' para capturar errores de formato en la entrada por consola, especialmente en la selección de opciones del menú.
   d) Bloques try-catch específicos
      - Cada excepción es capturada en su propio bloque catch, mostrando mensajes útiles usando la variable de excepción.
   e) No se capturan excepciones genéricas inmanejables
      - No se utiliza 'catch(Exception e)' de forma genérica.
   f) Relanzamiento de excepciones
      - Si alguna excepción no puede ser manejada localmente, se relanza usando 'throw'.
   g) Uso de la variable de excepción
      - En cada catch, se muestra información relevante al usuario sobre el error detectado.

4. Estructura del proyecto:
   - Clases principales: Libro, Usuario, Biblioteca, RegistroUsuario, ServiciosBiblioteca, Main.
   - Cada clase implementa los atributos y métodos necesarios para gestionar la biblioteca y los préstamos.

---------------------------------------------------------
REQUISITOS S5 - Semana 5
---------------------------------------------------------

1. Uso avanzado de colecciones:
   - 'ArrayList' para el catálogo secuencial de libros en la clase 'Biblioteca'.
   - 'HashMap' para la gestión eficiente de usuarios en la clase 'RegistroUsuario'.
   - 'HashSet' para garantizar unicidad de libros en la clase 'Biblioteca'.
   - 'TreeSet' para obtener listados ordenados de libros y usuarios (por título o nombre).

2. Importación de clases de colección:
   - Al inicio de cada archivo Java donde se usan, se importan las clases necesarias (ArrayList, HashMap, HashSet, TreeSet, etc.).

3. Menú robusto y operaciones eficientes:
   - El menú principal y los submenús permiten gestionar préstamos, devoluciones, búsqueda y registro de usuarios y libros de manera eficiente, incluso con un gran volumen de datos.

4. Persistencia eficiente:
   - Todas las operaciones que modifican el estado del sistema (registro, préstamo, devolución, alta/baja de libros) actualizan inmediatamente los archivos externos.
   - Al inicio del programa se carga toda la información desde archivos para trabajar en memoria con las colecciones adecuadas.

---------------------------------------------------------
UBICACIÓN EN EL CÓDIGO
---------------------------------------------------------

- El uso de ArrayList, HashMap, HashSet y TreeSet puede encontrarse en las declaraciones de variables de las clases 'Biblioteca' y 'RegistroUsuario', así como en los métodos que retornan catálogos ordenados.
- El manejo de excepciones personalizadas está en los métodos de búsqueda y préstamo de libros.
- El manejo de errores de entrada y robustez del menú se encuentra en la clase 'Main'.
- El control de acceso al menú de administración por clave está en el método 'administrar()' en 'Main.java'.

=========================================================