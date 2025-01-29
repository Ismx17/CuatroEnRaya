package org.iesalandalus.programacion.cuatroenraya.vista;

import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
    private Consola() {
    }

    public static String leerNombre() {
        String nombre;
        do {
            System.out.print("Introduce el nombre del jugador: ");
            nombre = Entrada.cadena();
            if (nombre.isEmpty()) {
                System.out.println("Error: El nombre no puede estar vacío.");
            }
        } while (nombre.isEmpty());
        return nombre;
    }

    public static Ficha elegirColorFichas() {
        Ficha ficha = null;
        while (ficha == null) {
            System.out.print("Introduce el color de la ficha (AZUL/VERDE): ");
            String color = Entrada.cadena();
            if (color.equals("AZUL")) {
                ficha = Ficha.AZUL;
            } else if (color.equals("VERDE")) {
                ficha = Ficha.VERDE;
            } else {
                System.out.println("Error: Ficha no válida. Debe ser 'AZUL' o 'VERDE'.");
            }
        }
        return ficha;
    }

    public static Jugador leerJugador() {
        String nombre = leerNombre();
        Ficha colorFichas = elegirColorFichas();
        return new Jugador(nombre, colorFichas);
    }

    public static Jugador leerJugador(Ficha ficha) {
        String nombre = leerNombre();
        return new Jugador(nombre, ficha);
    }

    public static int leerColumna(Jugador jugador) {
        int columna;
        do {
            System.out.printf("%s, Introduce la columna en la que deseas introducir  la ficha (0 - 6): ", jugador.nombre());
            columna = Entrada.entero();
        } while (columna < 0 || columna > 6);
        return columna;
    }
}