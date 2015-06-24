package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HojaSprites {

	private final int ancho;
	private final int alto;
	public final int[] pixeles;

	// coleccion de hojas de sprites
	public static HojaSprites general = new HojaSprites(
			"/texturas/general.png", 150, 30);

	// fin de la coleccion

	public HojaSprites(final String ruta, final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		pixeles = new int[ancho * alto];

		BufferedImage imagen;

		try {
			imagen = ImageIO.read(HojaSprites.class.getResource(ruta));
			imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}
