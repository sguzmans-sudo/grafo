# Proyecto de grafos con matriz de adyacencia

Proyecto Java con Maven, compatible con Apache NetBeans. Incluye grafos dirigidos y no dirigidos, ambos implementados con una matriz de adyacencia que se ajusta al agregar o eliminar vértices.

## Abrir y ejecutar en NetBeans

1. Seleccione **File > Open Project** y abra la carpeta `ProyectoGrafo`.
2. Ejecute `com.proyectografo.Main`.
3. Elija el tipo de grafo y use el menú de consola para gestionar vértices y aristas.

Se requiere JDK 17 o superior. Los índices de vértices empiezan en `0`; liste los vértices para consultar los índices actuales.

## Operaciones

- Agregar, listar, actualizar y eliminar vértices.
- Agregar y eliminar aristas, consultar si existe una arista y listar todas.
- Los vértices tienen nombres únicos y no vacíos.
- El grafo dirigido conserva el sentido de cada arista; el no dirigido registra la conexión en ambos sentidos.

## Pruebas

La prueba no requiere JUnit. En NetBeans, ejecute `GrafoMatrizTest.java` como una aplicación Java. Desde una terminal con Maven:

```text
mvn test-compile
java -cp "target/test-classes;target/classes" com.proyectografo.GrafoMatrizTest
```