package org.iesalandalus.programacion.cuatroenraya;

import org.iesalandalus.programacion.cuatroenraya.modelo.CuatroEnRayaExcepcion;
import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Tablero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import static org.iesalandalus.programacion.cuatroenraya.modelo.Ficha.AZUL;
import static org.iesalandalus.programacion.cuatroenraya.modelo.Ficha.VERDE;
import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

	private Tablero tablero;

	@BeforeEach
	void init() {
		tablero = new Tablero();
	}

	@Test
	void constructorCreaTableroVacio() {
		assertTrue(tablero.estaVacio());
	}

	@Test
	void estaLlenoCompruebaCorrectamente() {
		assertFalse(tablero.estaLleno());
		llenarTablero(tablero);
		assertTrue(tablero.estaLleno());
	}

	private void llenarTablero(Tablero tablero) {
		for (int columna = 0; columna < Tablero.COLUMNAS; columna++) {
			llenarColumna(tablero, columna);
		}
	}

	private void llenarColumna(Tablero tablero, int columna) {
		for (int veces = 0; veces < Tablero.FILAS; veces++) {
			assertDoesNotThrow(() -> tablero.introducirFicha(columna, AZUL));
		}
	}

	@ParameterizedTest(name = "Cuando introducimos ficha en la columna {0} lanza la excepción correspondiente.")
	@ValueSource(ints = {-1, Tablero.COLUMNAS})
	void introducirFichaColumnaNoValidaLanzaExcepcion(int columna) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> tablero.introducirFicha(columna, AZUL));
		assertEquals("Columna incorrecta.", iae.getMessage());
	}

	@ParameterizedTest(name = "Cuando introducimos la ficha {0} en una columna llena lanza la excepción correspondiente.")
	@EnumSource(Ficha.class)
	void introducirFichaColumnaLlenaLanzaExcepcion(Ficha ficha) {
		llenarColumna(tablero, 4);
		CuatroEnRayaExcepcion cere = assertThrows(CuatroEnRayaExcepcion.class, () -> tablero.introducirFicha(4, ficha));
		assertEquals("Columna llena.", cere.getMessage());
	}

	@Test
	void introducirFichaNulaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> tablero.introducirFicha(4,null));
		assertEquals("La ficha no puede ser nula.", npe.getMessage());
	}

	@ParameterizedTest(name = "Cuando realizamos las tiradas: {0}, detecta correctamente el objetivo horizontal alcanzado.")
	@MethodSource
	void introducirFichaFilaValidaFichaValidaDetectaObjetivoHorizontal(Tirada[] tiradas, Boolean[] resultados) {
		Iterator<Tirada> iteradorTiradas = Arrays.stream(tiradas).iterator();
		Iterator<Boolean> iteradorResultados = Arrays.stream(resultados).iterator();
		while (iteradorTiradas.hasNext() && iteradorResultados.hasNext()) {
			Tirada tirada = iteradorTiradas.next();
			assertDoesNotThrow(() -> {
				boolean resultado = tablero.introducirFicha(tirada.fila(), tirada.ficha());
				assertEquals(iteradorResultados.next(), resultado);
			});
		}
	}

	private record Tirada(int fila, Ficha ficha) {}

	private static Stream<Arguments> introducirFichaFilaValidaFichaValidaDetectaObjetivoHorizontal() {
		return Stream.of(
			Arguments.of(
				new Tirada[]{new Tirada(4, AZUL), new Tirada(3, AZUL), new Tirada(2, AZUL), new Tirada(6, AZUL), new Tirada(5, AZUL)},
				new Boolean[]{false, false, false, false, true}),
			Arguments.of(
				new Tirada[]{new Tirada(4, AZUL), new Tirada(3, AZUL), new Tirada(2, AZUL), new Tirada(6, AZUL), new Tirada(5, VERDE)},
				new Boolean[]{false, false, false, false, false})
		);
	}

	@ParameterizedTest(name = "Cuando realizamos las tiradas: {0}, detecta correctamente el objetivo vertical alcanzado.")
	@MethodSource
	void introducirFichaFilaValidaFichaValidaDetectaObjetivoVertical(Tirada[] tiradas, Boolean[] resultados) {
		Iterator<Tirada> iteradorTiradas = Arrays.stream(tiradas).iterator();
		Iterator<Boolean> iteradorResultados = Arrays.stream(resultados).iterator();
		while (iteradorTiradas.hasNext() && iteradorResultados.hasNext()) {
			Tirada tirada = iteradorTiradas.next();
			assertDoesNotThrow(() -> {
				boolean resultado = tablero.introducirFicha(tirada.fila(), tirada.ficha());
				assertEquals(iteradorResultados.next(), resultado);
			});
		}
	}

	private static Stream<Arguments> introducirFichaFilaValidaFichaValidaDetectaObjetivoVertical() {
		return Stream.of(
			Arguments.of(
				new Tirada[] {new Tirada(4, AZUL), new Tirada(4, AZUL), new Tirada(4, AZUL), new Tirada(4, AZUL)},
				new Boolean[]{false, false, false, true}),
			Arguments.of(
				new Tirada[]{new Tirada(4, AZUL), new Tirada(4, VERDE), new Tirada(4, AZUL), new Tirada(4, AZUL), new Tirada(4, AZUL), new Tirada(4, AZUL)},
				new Boolean[]{false, false, false, false, false, true})
		);
	}

	@ParameterizedTest(name = "Cuando realizamos las tiradas: {0}, detecta correctamente el objetivo diagonal NE alcanzado.")
	@MethodSource
	void introducirFichaFilaValidaFichaValidaDetectaObjetivoDiagonaNE(Tirada[] tiradas, Boolean[] resultados) {
		Iterator<Tirada> iteradorTiradas = Arrays.stream(tiradas).iterator();
		Iterator<Boolean> iteradorResultados = Arrays.stream(resultados).iterator();
		while (iteradorTiradas.hasNext() && iteradorResultados.hasNext()) {
			Tirada tirada = iteradorTiradas.next();
			assertDoesNotThrow(() -> {
				boolean resultado = tablero.introducirFicha(tirada.fila(), tirada.ficha());
				assertEquals(iteradorResultados.next(), resultado);
			});
		}
	}

	private static Stream<Arguments> introducirFichaFilaValidaFichaValidaDetectaObjetivoDiagonaNE() {
		return Stream.of(
			Arguments.of(
				new Tirada[] {new Tirada(3, AZUL), new Tirada(4, VERDE), new Tirada(4, AZUL), new Tirada(5, VERDE), new Tirada(5, VERDE),
					new Tirada(5, AZUL), new Tirada(6, VERDE), new Tirada(6, VERDE), new Tirada(6, VERDE), new Tirada(6, AZUL)},
				new Boolean[]{false, false, false, false, false, false, false, false, false, true}),
			Arguments.of(
				new Tirada[]{new Tirada(0, AZUL), new Tirada(1, AZUL), new Tirada(1, VERDE), new Tirada(2, VERDE), new Tirada(2, VERDE),
					new Tirada(2, AZUL), new Tirada(3, VERDE), new Tirada(3, AZUL), new Tirada(3, VERDE), new Tirada(3, AZUL),
					new Tirada(4, VERDE), new Tirada(4, AZUL), new Tirada(4, AZUL), new Tirada(4, VERDE), new Tirada(4, AZUL),
					new Tirada(5, AZUL), new Tirada(5, AZUL), new Tirada(5, VERDE), new Tirada(5, VERDE), new Tirada(5, AZUL), new Tirada(5, AZUL)},
				new Boolean[]{false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false, true})
		);
	}

	@ParameterizedTest(name = "Cuando realizamos las tiradas: {0}, detecta correctamente el objetivo diagonal NO alcanzado.")
	@MethodSource
	void introducirFichaFilaValidaFichaValidaDetectaObjetivoDiagonaNO(Tirada[] tiradas, Boolean[] resultados) {
		Iterator<Tirada> iteradorTiradas = Arrays.stream(tiradas).iterator();
		Iterator<Boolean> iteradorResultados = Arrays.stream(resultados).iterator();
		while (iteradorTiradas.hasNext() && iteradorResultados.hasNext()) {
			Tirada tirada = iteradorTiradas.next();
			assertDoesNotThrow(() -> {
				boolean resultado = tablero.introducirFicha(tirada.fila(), tirada.ficha());
				assertEquals(iteradorResultados.next(), resultado);
			});
		}
	}

	private static Stream<Arguments> introducirFichaFilaValidaFichaValidaDetectaObjetivoDiagonaNO() {
		return Stream.of(
			Arguments.of(
				new Tirada[] {new Tirada(3, AZUL), new Tirada(2, VERDE), new Tirada(2, AZUL), new Tirada(1, VERDE), new Tirada(1, VERDE),
					new Tirada(1, AZUL), new Tirada(0, VERDE), new Tirada(0, VERDE), new Tirada(0, VERDE), new Tirada(0, AZUL)},
				new Boolean[]{false, false, false, false, false, false, false, false, false, true}),
			Arguments.of(
				new Tirada[]{new Tirada(5, AZUL), new Tirada(4, AZUL), new Tirada(4, VERDE), new Tirada(3, VERDE), new Tirada(3, VERDE),
					new Tirada(3, AZUL), new Tirada(2, VERDE), new Tirada(2, AZUL), new Tirada(2, VERDE), new Tirada(2, AZUL),
					new Tirada(1, VERDE), new Tirada(1, AZUL), new Tirada(1, AZUL), new Tirada(1, VERDE), new Tirada(1, AZUL),
					new Tirada(0, AZUL), new Tirada(0, AZUL), new Tirada(0, VERDE), new Tirada(0, VERDE), new Tirada(0, AZUL),
					new Tirada(0, AZUL)},
				new Boolean[]{false, false, false, false, false, false, false, false, false, false,
						false, false, false, false, false, false, false, false, false, false, true})
		);
	}

	@Test
	void toStringTableroVacioRepresentaCorrectamenteTablero() {
		String tableroVacio = "|       |\n|       |\n|       |\n|       |\n|       |\n|       |\n -------\n";
		assertEquals(tableroVacio, tablero.toString());
	}

	@Test
	void toStringTableroLlenoRepresentaCorrectamenteTablero() {
		String tableroLleno = "|AAAAAAA|\n|AAAAAAA|\n|AAAAAAA|\n|AAAAAAA|\n|AAAAAAA|\n|AAAAAAA|\n -------\n";
		llenarTablero(tablero);
		assertEquals(tableroLleno, tablero.toString());
	}

	@Test
	void toStringConAlgunaTiradaRerpresentaCorrectamenteTablero() {
		String tableroAlgunaTirada = "|       |\n|       |\n|       |\n|       |\n|      A|\n|     AV|\n -------\n";
		assertDoesNotThrow(() -> tablero.introducirFicha(5, AZUL));
		assertDoesNotThrow(() -> tablero.introducirFicha(6, VERDE));
		assertDoesNotThrow(() -> tablero.introducirFicha(6, AZUL));
		assertEquals(tableroAlgunaTirada, tablero.toString());
	}
	
}