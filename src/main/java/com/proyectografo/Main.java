package com.proyectografo;

import java.util.List;
import java.util.Scanner;

/** Punto de entrada y menús de interacción por consola para los grafos. */
public class Main {
    /** Lector compartido para las entradas de teclado. */
    private static final Scanner entrada = new Scanner(System.in);

    /** Evita crear instancias de esta clase de entrada. */
    private Main() {
    }

    /**
     * Muestra el menú principal y permite elegir el tipo de grafo o un ejemplo.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== Proyecto de grafos con matriz de adyacencia ===");
            System.out.println("1. Crear grafo dirigido");
            System.out.println("2. Crear grafo no dirigido");
            System.out.println("3. Ejemplo");
            System.out.println("0. Salir");

            switch (leerEntero("Seleccione una opción: ")) {
                case 1 -> ejecutarMenu(new GrafoDirigido());
                case 2 -> ejecutarMenu(new GrafoNoDirigido());
                case 3 -> menuEjemplos();
                case 0 -> continuar = false;
                default -> System.out.println("Opción no válida.");
            }
        }
        System.out.println("Programa finalizado.");
    }

    /** Muestra el submenú de ejemplos de grafos dirigidos y no dirigidos. */
    private static void menuEjemplos() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== Ejemplo ===");
            System.out.println("1. Ejemplo de grafo dirigido");
            System.out.println("2. Ejemplo de grafo no dirigido");
            System.out.println("0. Volver al menú principal");

            switch (leerEntero("Seleccione un ejemplo: ")) {
                case 1 -> ejecutarEjemplo(true);
                case 2 -> ejecutarEjemplo(false);
                case 0 -> continuar = false;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Crea un grafo de muestra, presenta sus datos y abre el menú de operaciones.
     *
     * @param dirigido indica si se debe crear un ejemplo dirigido
     */
    private static void ejecutarEjemplo(boolean dirigido) {
        GrafoMatriz grafo = dirigido ? new GrafoDirigido() : new GrafoNoDirigido();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);

        System.out.println("Ejemplo " + (dirigido ? "dirigido" : "no dirigido") + " cargado:");
        listarVertices(grafo);
        listarAristas(grafo);
        ejecutarMenu(grafo);
    }

    /**
     * Ejecuta repetidamente las operaciones disponibles para un grafo.
     *
     * @param grafo grafo sobre el que se realizarán las operaciones
     */
    private static void ejecutarMenu(GrafoMatriz grafo) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nGrafo " + (grafo.esDirigido() ? "dirigido" : "no dirigido"));
            System.out.println("1. Agregar vértice");
            System.out.println("2. Listar vértices");
            System.out.println("3. Actualizar vértice");
            System.out.println("4. Eliminar vértice");
            System.out.println("5. Agregar arista");
            System.out.println("6. Eliminar arista");
            System.out.println("7. Listar aristas");
            System.out.println("8. Consultar arista");
            System.out.println("0. Volver al menú principal");

            int opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> agregarVertice(grafo);
                    case 2 -> listarVertices(grafo);
                    case 3 -> actualizarVertice(grafo);
                    case 4 -> eliminarVertice(grafo);
                    case 5 -> agregarArista(grafo);
                    case 6 -> eliminarArista(grafo);
                    case 7 -> listarAristas(grafo);
                    case 8 -> consultarArista(grafo);
                    case 0 -> continuar = false;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException error) {
                System.out.println("No se pudo completar la operación: " + error.getMessage());
            }
        }
    }

    /**
     * Solicita un nombre y agrega el vértice al grafo.
     *
     * @param grafo grafo al que se agregará el vértice
     */
    private static void agregarVertice(GrafoMatriz grafo) {
        int indice = grafo.agregarVertice(leerTexto("Nombre del vértice: "));
        System.out.println("Vértice agregado con índice " + indice + ".");
    }

    /**
     * Muestra los índices y nombres de todos los vértices del grafo.
     *
     * @param grafo grafo cuyos vértices se mostrarán
     */
    private static void listarVertices(GrafoMatriz grafo) {
        List<String> vertices = grafo.listarVertices();
        if (vertices.isEmpty()) {
            System.out.println("No hay vértices.");
            return;
        }
        for (int indice = 0; indice < vertices.size(); indice++) {
            System.out.println(indice + ": " + vertices.get(indice));
        }
    }

    /**
     * Solicita un índice y un nombre nuevo para actualizar un vértice.
     *
     * @param grafo grafo cuyo vértice se actualizará
     */
    private static void actualizarVertice(GrafoMatriz grafo) {
        listarVertices(grafo);
        int indice = leerEntero("Índice a actualizar: ");
        grafo.actualizarVertice(indice, leerTexto("Nuevo nombre: "));
        System.out.println("Vértice actualizado.");
    }

    /**
     * Solicita el índice de un vértice y lo elimina junto con sus aristas.
     *
     * @param grafo grafo del que se eliminará el vértice
     */
    private static void eliminarVertice(GrafoMatriz grafo) {
        listarVertices(grafo);
        grafo.eliminarVertice(leerEntero("Índice a eliminar: "));
        System.out.println("Vértice y sus aristas eliminados.");
    }

    /**
     * Solicita origen y destino, y agrega una arista al grafo.
     *
     * @param grafo grafo al que se agregará la arista
     */
    private static void agregarArista(GrafoMatriz grafo) {
        listarVertices(grafo);
        int origen = leerEntero("Índice de origen: ");
        int destino = leerEntero("Índice de destino: ");
        grafo.agregarArista(origen, destino);
        System.out.println("Arista agregada.");
    }

    /**
     * Solicita origen y destino, y elimina la arista correspondiente.
     *
     * @param grafo grafo del que se eliminará la arista
     */
    private static void eliminarArista(GrafoMatriz grafo) {
        listarVertices(grafo);
        int origen = leerEntero("Índice de origen: ");
        int destino = leerEntero("Índice de destino: ");
        grafo.eliminarArista(origen, destino);
        System.out.println("Arista eliminada.");
    }

    /**
     * Muestra todas las aristas del grafo o informa si no hay ninguna.
     *
     * @param grafo grafo cuyas aristas se mostrarán
     */
    private static void listarAristas(GrafoMatriz grafo) {
        List<String> aristas = grafo.listarAristas();
        if (aristas.isEmpty()) {
            System.out.println("No hay aristas.");
            return;
        }
        aristas.forEach(System.out::println);
    }

    /**
     * Solicita dos índices e informa si existe una arista entre ellos.
     *
     * @param grafo grafo en el que se consultará la arista
     */
    private static void consultarArista(GrafoMatriz grafo) {
        listarVertices(grafo);
        int origen = leerEntero("Índice de origen: ");
        int destino = leerEntero("Índice de destino: ");
        System.out.println(grafo.hayArista(origen, destino) ? "La arista existe." : "La arista no existe.");
    }

    /**
     * Lee una línea hasta obtener un número entero válido.
     *
     * @param mensaje indicación que se muestra antes de leer
     * @return número entero ingresado
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException error) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    /**
     * Muestra una indicación y lee una línea de texto.
     *
     * @param mensaje indicación que se muestra antes de leer
     * @return texto ingresado por el usuario
     */
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine();
    }
}