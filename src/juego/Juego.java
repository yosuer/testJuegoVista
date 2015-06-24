package juego;

import graficos.Pantalla;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import control.Teclado;

public class Juego extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 214687697799453950L;

	private static final int ANCHO = 800;
	private static final int ALTO = 600;

	private static volatile boolean enFuncionamiento = false;

	private static final String NOMBRE = "Juego";

	private static int aps = 0;
	private static int fps = 0;

	private static int x = 0;
	private static int y = 0;

	private static Thread thread;
	private static Teclado teclado;
	private static JFrame ventana;
	private static Pantalla pantalla;

	private static BufferedImage imagen = new BufferedImage(ANCHO, ALTO,
			BufferedImage.TYPE_INT_BGR);

	private static int[] pixeles;

	private Juego() {
		setPreferredSize(new Dimension(ANCHO, ALTO));

		teclado = new Teclado();
		addKeyListener(teclado);

		ventana = new JFrame(NOMBRE);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.setLayout(new BorderLayout());
		ventana.add(this, BorderLayout.CENTER);
		ventana.pack();
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	public static void main(String[] args) {
		Juego juego = new Juego();
		juego.iniciar();
	}

	private synchronized void iniciar() {
		enFuncionamiento = true;
		thread = new Thread(this, "Graficos");
		thread.start();
	}

	private synchronized void detener() {
		enFuncionamiento = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void actualizar() {
		teclado.actualizar();

		if (teclado.arriba)
			System.out.println("arriba");
		if (teclado.abajo)
			System.out.println("abajo");
		if (teclado.derecha)
			System.out.println("derecha");
		if (teclado.izquierda)
			System.out.println("izquierda");
		aps++;
	}

	private void mostrar() {
		fps++;
	}

	@Override
	public void run() {
		final int NS_POR_SEGUNDO = 1000000000;
		final byte APS_OBJETIVO = 60;
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		double delta = 0;

		requestFocus();

		while (enFuncionamiento) {
			final long inicioBucle = System.nanoTime();

			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;

			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

			while (delta >= 1) {
				actualizar();
				delta--;
			}

			if ((System.nanoTime() - referenciaContador) > NS_POR_SEGUNDO) {
				ventana.setTitle(NOMBRE + " || act por segundo " + aps
						+ " || fps: " + fps);
				aps = 0;
				fps = 0;
				referenciaContador = System.nanoTime();
			}
			mostrar();
		}
	}

}
