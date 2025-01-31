package org.iesalandalus.programacion.cuatroenraya;


import org.iesalandalus.programacion.cuatroenraya.modelo.CuatroEnRaya;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

public class MainApp {

    public static void main(String[] args) {
        System.out.println("Introduce los datos del PRIMER jugador.");
        Jugador jugador1 = new Jugador(Consola.leerNombre(), Consola.elegirColorFichas());
        System.out.println("Introduce los datos del SEGUNDO jugador.");
        Jugador jugador2 = new Jugador(Consola.leerNombre(), Consola.elegirColorFichas());
        CuatroEnRaya juego = new CuatroEnRaya(jugador1,jugador2);
        juego.jugar();
    }
}

