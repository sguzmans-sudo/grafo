package com.proyectografo;

import java.util.List;

/** Pruebas ejecutables sin dependencias externas para las operaciones del grafo. */
public class GrafoMatrizTest {
    /** Evita crear instancias de esta clase de pruebas. */
    private GrafoMatrizTest() {
    }

    /**
        * Ejecuta todas las pruebas y muestra el resultado en consola.
        *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        grafoDirigidoSoloAgregaLaAristaEnUnaDireccion();
        grafoNoDirigidoMantieneSimetriaYListaLaAristaUnaVez();
        eliminarVerticeAjustaLaMatrizYConservaLasOtrasAristas();
        System.out.println("Todas las pruebas pasaron.");
    }

    /** Comprueba que una arista dirigida no se registre en el sentido inverso. */
    private static void grafoDirigidoSoloAgregaLaAristaEnUnaDireccion() {
        GrafoMatriz grafo = new GrafoDirigido();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");

        grafo.agregarArista(0, 1);

        verificar(grafo.hayArista(0, 1), "Debe existir la arista A -> B.");
        verificar(!grafo.hayArista(1, 0), "No debe existir la arista inversa.");
        verificarIguales(List.of("A -> B"), grafo.listarAristas());
    }

    /** Comprueba la simetría y el listado único de una arista no dirigida. */
    private static void grafoNoDirigidoMantieneSimetriaYListaLaAristaUnaVez() {
        GrafoMatriz grafo = new GrafoNoDirigido();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");

        grafo.agregarArista(0, 1);

        verificar(grafo.hayArista(0, 1), "Debe existir la arista A -- B.");
        verificar(grafo.hayArista(1, 0), "La arista no dirigida debe ser simétrica.");
        verificarIguales(List.of("A -- B"), grafo.listarAristas());
    }

    /** Comprueba que eliminar un vértice reajuste la matriz y conserve otras aristas. */
    private static void eliminarVerticeAjustaLaMatrizYConservaLasOtrasAristas() {
        GrafoMatriz grafo = new GrafoDirigido();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista(0, 2);
        grafo.agregarArista(2, 1);

        grafo.eliminarVertice(1);

        verificarIguales(List.of("A", "C"), grafo.listarVertices());
        verificarIguales(List.of("A -> C"), grafo.listarAristas());
    }

    /**
     * Falla la prueba cuando una condición no se cumple.
     *
     * @param condicion condición que debe ser verdadera
     * @param mensaje descripción del fallo
     */
    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    /**
     * Compara dos objetos y falla si no son iguales.
     *
     * @param esperado valor esperado
     * @param actual valor obtenido
     */
    private static void verificarIguales(Object esperado, Object actual) {
        if (!esperado.equals(actual)) {
            throw new AssertionError("Se esperaba " + esperado + " pero se obtuvo " + actual + ".");
        }
    }
}