# 📋 Consignas:

1. **Crear un proyecto Spring Boot**
    
    - Crear un proyecto Spring Boot con Maven o Gradle.

    - Configurar la base de datos PostgreSQL.

    - Crear las entidades y repositorios de la base de datos.
    
1. **Desarrollar el backend**
    
    - Desarrollar las rutas y servicios API REST para administrar los libros de la biblioteca.
    
    - Implementar la lógica de negocio para agregar, editar, eliminar y consultar libros.
    
1. **Desarrollar el frontend**
    
    - Crear una interfaz de usuario para interactuar con las API REST del backend.
    
    - Implementar las funcionalidades para agregar, editar, eliminar y consultar libros.
    
1. **Dockerizar los módulos**
    
    - Dockerizar los módulos a fin que se pueda facilitar su despliegue.

## ⚙️ Requisitos funcionales:

- [x] **Agregar libros:** El usuario debe poder agregar nuevos libros a la biblioteca. Para ello, debe proporcionar los siguientes datos:
    - Título
    - Autor
    - Género
    - Número de páginas
    - Sinopsis

- [x] **Editar libros:** El usuario debe poder editar los datos de los libros ya existentes. Para ello, debe poder modificar los datos de título, autor, género, número de páginas y sinopsis.

- [x] **Eliminar libros:** El usuario debe poder eliminar los libros de la biblioteca.

- [x] **Consultar libros:** El usuario debe poder consultar los libros de la biblioteca por título, autor, género o fecha de publicación.

## 🛠️ ABMs básicos:

- [x] **ABM de libros:** Este ABM permitirá al usuario agregar, editar y eliminar libros de la biblioteca.

- [x] **ABM de usuarios:** Este ABM permitirá al usuario registrarse y crear una cuenta en la biblioteca.

- [x] **ABM de préstamos:** Este ABM permitirá al usuario realizar préstamos de libros de la biblioteca.
