package com.proyectografo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un grafo almacenado mediante una matriz de adyacencia.
 * Los vértices se identifican por su índice en la lista interna.
 */
public abstract class GrafoMatriz {
    /** Indica si las aristas del grafo tienen dirección. */
    private final boolean dirigido;

    /** Nombres únicos de los vértices, en el mismo orden que la matriz. */
    private final List<String> vertices = new ArrayList<>();

    /** Matriz que indica si existe una arista entre cada par de vértices. */
    private boolean[][] matriz = new boolean[0][0];

    /**
     * Crea un grafo vacío con la modalidad indicada.
     *
     * @param dirigido {@code true} para un grafo dirigido; {@code false} para uno no dirigido
     */
    protected GrafoMatriz(boolean dirigido) {
        this.dirigido = dirigido;
    }

    /**
     * Agrega un vértice y amplía la matriz de adyacencia.
     *
     * @param nombre nombre único y no vacío del vértice
     * @return índice asignado al nuevo vértice
     * @throws IllegalArgumentException si el nombre es vacío o ya está en uso
     */
    public int agregarVertice(String nombre) {
        String nombreValido = validarNombre(nombre, -1);
        int cantidadActual = vertices.size();
        boolean[][] nuevaMatriz = new boolean[cantidadActual + 1][cantidadActual + 1];

        for (int fila = 0; fila < cantidadActual; fila++) {
            System.arraycopy(matriz[fila], 0, nuevaMatriz[fila], 0, cantidadActual);
        }

        vertices.add(nombreValido);
        matriz = nuevaMatriz;
        return cantidadActual;
    }

    /**
     * Cambia el nombre de un vértice sin modificar sus aristas.
     *
     * @param indice índice del vértice que se actualizará
     * @param nuevoNombre nombre único y no vacío que se asignará
     * @throws IllegalArgumentException si el índice no existe o el nombre no es válido
     */
    public void actualizarVertice(int indice, String nuevoNombre) {
        validarIndice(indice);
        vertices.set(indice, validarNombre(nuevoNombre, indice));
    }

    /**
     * Elimina un vértice y las aristas que lo conectan con otros vértices.
     * Los índices posteriores al eliminado se desplazan una posición.
     *
     * @param indice índice del vértice que se eliminará
     * @throws IllegalArgumentException si el índice no existe
     */
    public void eliminarVertice(int indice) {
        validarIndice(indice);
        int nuevaCantidad = vertices.size() - 1;
        boolean[][] nuevaMatriz = new boolean[nuevaCantidad][nuevaCantidad];
        int nuevaFila = 0;

        for (int fila = 0; fila < matriz.length; fila++) {
            if (fila == indice) {
                continue;
            }

            int nuevaColumna = 0;
            for (int columna = 0; columna < matriz.length; columna++) {
                if (columna != indice) {
                    nuevaMatriz[nuevaFila][nuevaColumna] = matriz[fila][columna];
                    nuevaColumna++;
                }
            }
            nuevaFila++;
        }

        vertices.remove(indice);
        matriz = nuevaMatriz;
    }

    /**
     * Agrega una arista entre dos vértices.
     * En grafos no dirigidos, registra también la conexión inversa.
     *
     * @param origen índice del vértice de origen
     * @param destino índice del vértice de destino
     * @throws IllegalArgumentException si alguno de los índices no existe
     */
    public void agregarArista(int origen, int destino) {
        validarIndice(origen);
        validarIndice(destino);
        matriz[origen][destino] = true;
        if (!dirigido) {
            matriz[destino][origen] = true;
        }
    }

    /**
     * Elimina la arista entre dos vértices.
     * En grafos no dirigidos, elimina también la conexión inversa.
     *
     * @param origen índice del vértice de origen
     * @param destino índice del vértice de destino
     * @throws IllegalArgumentException si alguno de los índices no existe
     */
    public void eliminarArista(int origen, int destino) {
        validarIndice(origen);
        validarIndice(destino);
        matriz[origen][destino] = false;
        if (!dirigido) {
            matriz[destino][origen] = false;
        }
    }

    /**
     * Comprueba si existe una arista desde un vértice hasta otro.
     *
     * @param origen índice del vértice de origen
     * @param destino índice del vértice de destino
     * @return {@code true} si existe la arista; de lo contrario, {@code false}
     * @throws IllegalArgumentException si alguno de los índices no existe
     */
    public boolean hayArista(int origen, int destino) {
        validarIndice(origen);
        validarIndice(destino);
        return matriz[origen][destino];
    }

    /**
     * Devuelve los nombres de los vértices en orden de índice.
     * La lista devuelta no permite modificaciones.
     *
     * @return lista inmutable con los nombres de los vértices
     */
    public List<String> listarVertices() {
        return List.copyOf(vertices);
    }

    /**
     * Devuelve las aristas como texto, sin duplicar conexiones no dirigidas.
     * Usa {@code origen -> destino} para grafos dirigidos y
     * {@code vértice -- vértice} para grafos no dirigidos.
     *
     * @return lista inmutable con la representación de las aristas
     */
    public List<String> listarAristas() {
        List<String> aristas = new ArrayList<>();
        for (int origen = 0; origen < matriz.length; origen++) {
            int primerDestino = dirigido ? 0 : origen;
            for (int destino = primerDestino; destino < matriz.length; destino++) {
                if (matriz[origen][destino]) {
                    String separador = dirigido ? " -> " : " -- ";
                    aristas.add(vertices.get(origen) + separador + vertices.get(destino));
                }
            }
        }
        return List.copyOf(aristas);
    }

    /**
     * Obtiene la cantidad actual de vértices.
     *
     * @return número de vértices del grafo
     */
    public int getNumeroVertices() {
        return vertices.size();
    }

    /**
     * Indica si este grafo conserva la dirección de sus aristas.
     *
     * @return {@code true} si es dirigido; de lo contrario, {@code false}
     */
    public boolean esDirigido() {
        return dirigido;
    }

    /**
     * Valida y normaliza el nombre de un vértice.
     *
     * @param nombre nombre que se validará
     * @param indiceActual índice cuyo nombre se está modificando, o {@code -1} al agregar
     * @return nombre sin espacios al inicio ni al final
     * @throws IllegalArgumentException si el nombre es vacío o está duplicado
     */
    private String validarNombre(String nombre, int indiceActual) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del vértice no puede estar vacío.");
        }

        String nombreNormalizado = nombre.trim();
        for (int indice = 0; indice < vertices.size(); indice++) {
            if (indice != indiceActual && vertices.get(indice).equals(nombreNormalizado)) {
                throw new IllegalArgumentException("Ya existe un vértice con ese nombre.");
            }
        }
        return nombreNormalizado;
    }

    /**
     * Comprueba que un índice corresponda a un vértice existente.
     *
     * @param indice índice que se validará
     * @throws IllegalArgumentException si el índice está fuera de rango
     */
    private void validarIndice(int indice) {
        if (indice < 0 || indice >= vertices.size()) {
            throw new IllegalArgumentException("Índice fuera de rango: " + indice);
        }
    }
}