package org.iesalandalus.programacion.cuatroenraya.modelo;

import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

public class CuatroEnRaya {
    public static final int NUMERO_JUGADORES = 2;
    private Jugador[] jugadores;
    private Tablero tablero;

    public CuatroEnRaya(Jugador jugador1, Jugador jugador2) {
        jugadores = new Jugador[NUMERO_JUGADORES];
        jugadores[0] = jugador1;
        jugadores[1] = jugador2;

        // Crear una nueva instancia del tablero
        tablero = new Tablero();
    }

    public boolean tirar(Jugador jugador) {
        int columna = Consola.leerColumna(jugador);
        return tablero.introducirFicha(columna, jugador.colorFichas());
    }

    public void jugar() {
        int turno = 0;
        boolean ganador = false;
        while (!tablero.estaLleno() && !ganador) {
            Jugador jugadorActual = jugadores[turno % NUMERO_JUGADORES];
            System.out.println(tablero);
            System.out.printf("Es el turno de %s\n", jugadorActual.nombre());
            ganador = tirar(jugadorActual);
            if (!ganador) {
                turno++;
            }
        }
        if (ganador) {
            System.out.printf("ENHORABUENA, %s has ganado!!|\n", jugadores[turno % NUMERO_JUGADORES].nombre());
        } else {
            System.out.println("El juego ha terminado, No hay más casillas libres.");
        }
    }
}