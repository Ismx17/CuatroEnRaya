package org.iesalandalus.programacion.cuatroenraya;

import org.iesalandalus.programacion.cuatroenraya.modelo.CuatroEnRaya;
import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

public class MainApp {
    public static void main(String[] args) {
        // Crear el primer jugador
        Jugador jugador1 = new Jugador(Consola.leerNombre(), Consola.elegirColorFichas());
        System.out.println(jugador1);

        // Crear el segundo jugador con el color de ficha opuesto al primero
        Jugador jugador2 = Consola.leerJugador(Ficha.VERDE);

        // Crear una instancia del juego con ambos jugadores
        CuatroEnRaya juego = new CuatroEnRaya(jugador1, jugador2);

        // Iniciar el juego
        juego.jugar();
    }
}

