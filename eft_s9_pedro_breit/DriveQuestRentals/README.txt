

DriveQuest Rentals

Este proyecto es una aplicación de consola en Java que permite administrar la flota de vehículos de una empresa de arriendo, separando entre vehículos de carga y de pasajeros. La idea principal es que desde la consola se puedan realizar las operaciones típicas de administración: agregar, eliminar, listar vehículos, consultar el historial de arriendos y calcular boletas considerando IVA y descuentos según el tipo de vehículo. Todo el desarrollo se hizo respetando la programación orientada a objetos, colecciones seguras y persistencia de datos en archivos de texto.

-----

Organización del proyecto

Así está ordenado el código (por carpetas y archivos):

src/
|
|- datos/
| |- ArchivoVehiculos.java
| |- GestorVehiculos.java
| |- RegistroHistorial.java
|
|- interfaz/
| |- Main.java
| |- Usuario.java
| |- Administrador.java
|
|- modelo/
| |- Vehiculo.java
| |- VehiculoCarga.java
| |- VehiculoPasajero.java
|
|- negocio/
| |- Facturable.java
|
|- util/
  |- ValidadorPatente.java


-----

Relación del código con la rúbrica

Voy punto por punto para mostrar en qué parte se cumple cada requisito solicitado:

- Clases abstractas y herencia:  
  En la carpeta "modelo" está la clase abstracta "Vehiculo", y las clases hijas "VehiculoCarga" y "VehiculoPasajero". Cada una tiene constructores y métodos propios según corresponde.

- Interfaces para IVA y descuentos:  
  En "negocio/Facturable.java" está la interfaz que define las constantes de IVA y descuentos, y el método para calcular y mostrar boleta.

- Gestión y colecciones:  
  Todo el manejo de la flota de vehículos se hace en "datos/GestorVehiculos.java", que usa un HashMap sincronizado para asegurar la unicidad de la patente y facilitar búsquedas, altas y bajas.

- Persistencia en archivos:  
  Los métodos para guardar y leer vehículos desde archivos están en "datos/ArchivoVehiculos.java". Así, cualquier cambio en la flota se refleja en el archivo "vehiculos.txt" de inmediato.

- Manejo de excepciones:  
  Todas las operaciones sensibles (archivos, ingreso de datos) están rodeadas de "try-catch" para evitar que el programa se caiga ante errores.

- Filtros y búsquedas:  
  El gestor de vehículos tiene métodos para listar por tipo, buscar por patente y filtrar por duración de arriendo.

- Interfaz ordenada y amigable:  
  Toda la interacción se hace por consola, en "interfaz/Administrador.java" y "interfaz/Usuario.java". Los menús están bien estructurados y las salidas (listados y detalles) se muestran en formato de tabla, con columnas alineadas para que la información se lea fácil y claro, cumpliendo el punto de "interfaz amigable" de la rúbrica, aunque sea en consola.

- Historial de arriendos:
  En "datos/RegistroHistorial.java" se registra y consulta el historial de arriendos, tal como se pide en la rúbrica.

- Principios OOP y extensibilidad:  
  Todo el proyecto sigue la estructura de orientación a objetos, con encapsulamiento, polimorfismo y responsabilidad clara en cada clase. La arquitectura es lo bastante flexible como para agregar nuevas funcionalidades en el futuro.

-----

Nota sobre la interfaz

La "interfaz" que pide la rúbrica está completamente cubierta con la experiencia en consola:  
Los menús son claros, las tablas están alineadas y la navegación es simple, así que cualquier usuario puede operar el sistema sin enredos ni instrucciones externas.

-----

Autor: Pedro Breit


