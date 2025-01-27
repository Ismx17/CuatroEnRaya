package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    private Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    public boolean estaVacio() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (casillas[i][j].estaOcupada()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean columnaVacia(int columna) {
        if (columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException("La columna no es válida");
        }
        // Comprobar si la primera fila está vacía en la columna
        return casillas[0][columna].getFicha() == null;
    }

    public boolean estaLleno() {
        for (int i = 0; i < COLUMNAS; i++) {
            if (columnaVacia(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean columnaLlena(int columna) {
        // Comprobar si la columna está llena mirando la primera fila
        return casillas[0][columna].estaOcupada();
    }

    private void comprobarFicha(Ficha ficha) {
        Objects.requireNonNull(ficha, "La ficha no puede ser nula");
    }

    private void comprobarColumna(int columna) {
        if (columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException("La columna está fuera de rango.");
        }
    }

    public int getPrimeraFilaVacia(int columna) {
        comprobarColumna(columna);
        for (int i = FILAS - 1; i >= 0; i--) {
            if (!casillas[i][columna].estaOcupada()) {
                return i;
            }
        }
        throw new IllegalArgumentException("La columna está llena.");
    }

    public boolean comprobarTirada(int fila, int columna) {
        Ficha ficha = casillas[fila][columna].getFicha();
        return comprobarHorizontal(fila, ficha) ||
                comprobarVertical(columna, ficha) ||
                comprobarDiagonalNE(fila, columna, ficha) ||
                comprobarDiagonalNO(fila, columna, ficha);
    }

    public boolean objetivoAlcanzado(int fichasConsecutivas) {
        return fichasConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    public boolean comprobarHorizontal(int fila, Ficha ficha) {
        int contador = 1;
        // Comprobamos hacia la derecha
        for (int j = 1; j < COLUMNAS && fila < FILAS && casillas[fila][j].getFicha() == ficha; j++) {
            contador++;
        }
        // Comprobamos hacia la izquierda
        for (int j = COLUMNAS - 2; j >= 0 && casillas[fila][j].getFicha() == ficha; j--) {
            contador++;
        }
        return objetivoAlcanzado(contador);
    }

    public boolean comprobarVertical(int columna, Ficha ficha) {
        int contador = 1;
        // Comprobamos hacia abajo
        for (int i = FILAS - 2; i >= 0 && casillas[i][columna].getFicha() == ficha; i--) {
            contador++;
        }
        return objetivoAlcanzado(contador);
    }

    public boolean comprobarDiagonalNE(int fila, int columna, Ficha ficha) {
        int contador = 1;
        int desplazamiento = menor(fila, columna);
        int f = fila - desplazamiento;
        int c = columna - desplazamiento;
        // Comprobamos hacia arriba y a la derecha
        while (f < FILAS && c < COLUMNAS && casillas[f][c].getFicha() == ficha) {
            contador++;
            f++;
            c++;
        }
        return objetivoAlcanzado(contador);
    }

    public boolean comprobarDiagonalNO(int fila, int columna, Ficha ficha) {
        int contador = 1;
        int desplazamiento = menor(fila, COLUMNAS - 1 - columna);
        int f = fila - desplazamiento;
        int c = columna + desplazamiento;
        // Comprobamos hacia arriba y a la izquierda
        while (f < FILAS && c >= 0 && casillas[f][c].getFicha() == ficha) {
            contador++;
            f++;
            c--;
        }
        return objetivoAlcanzado(contador);
    }

    private int menor(int a, int b) {
        return a < b ? a : b;
    }

    public boolean introducirFicha(int columna, Ficha ficha) {
        comprobarFicha(ficha);
        comprobarColumna(columna);
        if (columnaLlena(columna)) {
            throw new CuatroEnRayaExcepcion("Columna llena.");
        }
        if (columna < 0 || columna >= COLUMNAS) {
            throw new CuatroEnRayaExcepcion("La columna esta fuera de rango");
        }
        if (columna == -1)   {
            throw new CuatroEnRayaExcepcion("La columna esta fuera de rango");
        }
        int fila = getPrimeraFilaVacia(columna);
        casillas[fila][columna].setFicha(ficha);
        return comprobarTirada(fila, columna);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                sb.append(casillas[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}