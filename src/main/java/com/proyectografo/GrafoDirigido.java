package com.proyectografo;

/** Grafo cuyas aristas tienen un sentido definido desde el origen al destino. */
public class GrafoDirigido extends GrafoMatriz {
    /** Crea un grafo dirigido vacío. */
    public GrafoDirigido() {
        super(true);
    }
}