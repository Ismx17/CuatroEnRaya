package org.iesalandalus.programacion.cuatroenraya.modelo;

public class Casilla {

    Ficha ficha;

    public Casilla()    {
        this.ficha = null;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) throws NullPointerException, CuatroEnRayaExcepcion {
        if (ficha == null) {
            throw new NullPointerException("No se puede poner una ficha nula.");
        }
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