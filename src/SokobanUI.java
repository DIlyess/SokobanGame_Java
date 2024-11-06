
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SokobanUI extends SimpleInterface {

	private boolean first = true;

	public SokobanUI() {
		super("sokoban", 1200, 700);
	}

	public void show(Configuration config) {
		setGrid(config.getNiveau().getX(), config.getNiveau().getY());
		if (first) {
			setSize(65 * config.getNiveau().getX(), 65 * config.getNiveau().getY());
			first = false;
		}

		for (int i = 0; i < config.getNiveau().getX(); i++) {
			for (int j = 0; j < config.getNiveau().getY(); j++) {

				try {
					drawCell(i, j, ImageIO.read(new File("img/Vide.jpg")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				boolean cible = config.estCible(new Position(i, j));
				Element ele = config.get(new Position(i, j));

				if (ele instanceof Joueur) {
					try {
						drawCell(i, j, ImageIO.read(new File("img/Joueur.jpg")));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (ele instanceof Mur) {
					try {
						drawCell(i, j, ImageIO.read(new File("img/Mur.jpg")));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (ele instanceof Caisse) {
					if (cible) {
						try {
							drawCell(i, j, ImageIO.read(new File("img/CaisseRangee.jpg")));
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						try {
							drawCell(i, j, ImageIO.read(new File("img/Caisse.jpg")));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else if (cible) {
					try {
						drawCell(i, j, ImageIO.read(new File("img/Rangement.jpg")));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		repaint();
	}

	public void endGame(Configuration config) {
		String msg = "Perdu !";
		int[] color = RED;
		if (config.victoire()) {
			color = GREEN;
			msg = "Victoire !";
		}
		for (int i = 0; i < 10; i++) {
			show(config);
			pause(50);
			drawString(msg, 0.45, 0.45, 52, color);
			refresh();
			pause(100);
		}
	}

}
