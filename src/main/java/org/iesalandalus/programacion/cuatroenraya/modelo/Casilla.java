package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Casilla {

    Ficha ficha;

    public Casilla()    {
        this.ficha = null;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) throws NullPointerException, CuatroEnRayaExcepcion {
        Objects.requireNonNull(ficha, "La ficha no puede ser nula.");
        if (this.ficha != null) {
            throw new CuatroEnRayaExcepcion("La casilla ya contiene una ficha.");
        }
        this.ficha = ficha;
    }

    public boolean estaOcupada() {
        if (ficha != null) {
            return true;
        }
            return false;
    }

    @Override
    public String toString() {
        return String.format("%s", (this.estaOcupada() ? ficha.toString() : " "));
    }
}