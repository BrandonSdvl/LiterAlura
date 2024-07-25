# LiterAlura

Este proyecto forma parte del programa Oracle Next Education y consiste en una aplicación de consola desarrollada para la gestión de libros y autores. La aplicación ofrece funciones como la búsqueda de libros por título, la visualización de libros y autores registrados en la base de datos, y la realización de diversas operaciones relacionadas con el manejo de estos elementos.

## Características

- Buscar libros por título utilizando una API externa.
- Registrar libros y autores en la base de datos
- Listar libros registrados en la base de datos.
- Listar autores registrados en la base de datos.
- Listar autores vivos en un determinado año.
- Listar libros por idioma.

## Requisitos

- Java 17 o superior.
- Maven.
- Spring Boot.
- PostgreSQL 16 o superior.

## Estructura del Proyecto

El proyecto está organizado en las siguientes clases:

- model: Paquete que contiene las clases de modelo que representan las entidades de la base de datos.

    - Autor.java: Clase que representa la entidad Autor.
    - DatosAutor.java: Clase que representa los datos de un autor, utilizada para la deserialización de JSON.
    - Libro.java: Clase que representa la entidad Libro.
    - DatosLibro.java: Clase que representa los datos de un libro, utilizada para la deserialización de JSON.
- Principal: Paquete que contiene la clase principal que maneja la lógica del menú de la consola.
    - Principal.java: Clase que contiene el menú principal de la aplicación y maneja las interacciones del usuario.
    - EntradaUsuario.java: Maneja la entrada del usuario.
- repository: Paquete que contiene las interfaces de repositorio para acceder a la base de datos.
    - AutorRepository.java: Interfaz de repositorio para la entidad Autor.
    - LibroRepository.java: Interfaz de repositorio para la entidad Libro.
- service: Paquete que contiene las clases de servicio que manejan la lógica de negocio de la aplicación.
    - ConsumoAPI.java: Clase que se encarga de consumir la API externa para obtener datos de libros.
    - ConvierteDatos.java: Clase que se encarga de convertir los datos obtenidos de la API en objetos de tipo DatosLibro y JsonNode.
    - Buscador.java: Clase que maneja la lógica de búsqueda de libros y autores y registro de datos.

## Configuración

1. **Clonar el repositorio**:

    ```sh
    git clone https://github.com/BrandonSdvl/LiterAlura.git
    cd LiterAlura
    ```

2. **Configura las variables de entorno para la base de datos:**
    
    En linux o macOS:
    ```bash
    export DB_HOST=tu-host
    export DB_NAME=literalura
    export DB_USER=tu-usuario
    export DB_PASSWORD=tu-contraseña
    ```
    
    En windows
    ```bash
    set DB_HOST=tu-host
    set DB_NAME=literalura
    set DB_USER=tu-usuario
    set DB_PASSWORD=tu-contraseña
    ```
    
    O puedes modificar el archivo application.properties
    ```properties
    spring.datasource.url=jdbc:postgresql://tu-host/literalura
    spring.datasource.username=tu-usuario
    spring.datasource.password=tu-contraseña
    ```
    
## Uso


Al iniciar la aplicación, se mostrará un menú interactivo desde el cual podrás seleccionar la opción que desees explorar.


```text
Bienvenido/a a LiterAlura
                
1.- Buscar libro por título
2.- Listar libros registrados
3.- Listar autores registrados
4.- Listar autores vivos en un determinado año
5.- Listar libros por idioma
0.- Salir

Seleccione una opción:
```

