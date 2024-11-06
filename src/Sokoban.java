
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class Sokoban {

	// PAUSE entre chaque coup du replay
	public static int PAUSEREPLAY = 250;
	// PAUSE entre chaque coup du solveur
	public static int PAUSESOLVER = 10;
	public static String PATH = "./";
	public static String EASY = PATH + "microban.txt";
	public static String MEDIUM = PATH + "sokoriginal.txt";
	public static String HARD = PATH + "Novoban.txt";

	public static void main(String[] args) {

		Configuration sokoban = loadSokoban(EASY, 6);
		replay(sokoban, jouer(sokoban));
		// SolverInterface solver = new Solver();
		// replay(sokoban,resoudre(sokoban,solver));
	}

	public static ArrayList<Direction> jouer(Configuration config) {
		Configuration sokoban = new Configuration(config);
		Configuration previous = new Configuration(config);
		SokobanUI sokoUI = new SokobanUI();
		sokoUI.show(sokoban);
		int lastkey = 0;
		while (!sokoban.victoire()) {
			Direction d = null;
			// get key
			int key = sokoUI.waitKey();
			if (key == KeyEvent.VK_ESCAPE) {
				sokoUI.endGame(sokoban);
				sokoUI.dispose();
				return sokoban.getJoueur().getHisto();
			} else if (key == KeyEvent.VK_Z) {
				d = new Direction(0, -1);
			} else if (key == KeyEvent.VK_S) {
				d = new Direction(0, 1);
			} else if (key == KeyEvent.VK_Q) {
				d = new Direction(-1, 0);
			} else if (key == KeyEvent.VK_D) {
				d = new Direction(1, 0);
			} else if (key == KeyEvent.VK_R) {
				sokoban = new Configuration(config);
			} else if (key == KeyEvent.VK_P) {
				if (key != lastkey) {
					ArrayList<Direction> current_histo = sokoban.getJoueur().getHisto();
					sokoban = new Configuration(previous);
					current_histo.remove(current_histo.size() - 1);
					sokoban.getJoueur().setHisto(current_histo);
				} else {
					// // display the message for 2 second
					// Timer timer = new Timer(10, e -> sokoUI.drawString("1 retour possible", 0.10,
					// 0.1, 64, SokobanUI.RED));
					// timer.setRepeats(false);
					// timer.start();
				}
			}

			previous = new Configuration(sokoban);
			lastkey = key;

			if (d != null) {
				sokoban.bougerJoueurVers(d);
			}

			sokoUI.show(sokoban);
			// sokoUI.drawString("zqsd : move, r : reset, p : undo, esc : quit",
			// 0.25, 0.1, 32,
			// SokobanUI.BLACK);
		}
		sokoUI.endGame(sokoban);
		sokoUI.dispose();
		return sokoban.getJoueur().getHisto();

	}

	public static ArrayList<Direction> resoudre(Configuration sokoban, SolverInterface solver) {
		SokobanUI sokoUI = new SokobanUI();
		solver.set(sokoban);
		while (!solver.stop()) {
			solver.step();
			sokoUI.show(solver.getConfiguration());
			sokoUI.drawString("testées: " + solver.getTotalSteps(), 0.05, 0.05, 32, SokobanUI.RED);
			sokoUI.drawString("Déplacements: " + solver.getConfiguration().getJoueur().getHisto().size(), 0.05, 0.08,
					32, SokobanUI.RED);
			sokoUI.drawString("Coups: " + solver.getConfiguration().getJoueur().getNbCoups(), 0.05, 0.11, 32,
					SokobanUI.RED);
			// sokoUI.drawString("Info: "+solver.getInfo(),0.05,0.15,32,SokobanUI.RED);
			sokoUI.refresh();
			if (sokoUI.popKey() == 27) {
				sokoUI.endGame(solver.getConfiguration());
				return solver.getConfiguration().getJoueur().getHisto();
			}
			SokobanUI.pause(PAUSESOLVER);
		}
		return solver.getConfiguration().getJoueur().getHisto();
	}

	public static void replay(Configuration sokoban, ArrayList<Direction> histo) {
		SokobanUI sokoUI = new SokobanUI();
		while (true) {
			Configuration jeu = new Configuration(sokoban);
			sokoUI.show(jeu);
			sokoUI.drawString("REPLAY", 0.45, 0.05, 30, SokobanUI.BLUE);
			sokoUI.refresh();
			for (Direction d : histo) {
				SimpleInterface.pause(PAUSEREPLAY);
				jeu.bougerJoueurVers(d);
				sokoUI.show(jeu);
				sokoUI.drawString("REPLAY", 0.45, 0.05, 30, SokobanUI.BLUE);
				sokoUI.refresh();
				if (sokoUI.popKey() == 27) {
					sokoUI.dispose();
					return;
				}
			}
			if (sokoUI.popKey() == 27) {
				sokoUI.dispose();
				return;
			}
			sokoUI.endGame(jeu);
		}
	}

	public static Configuration loadSokoban(String levelString) {
		Niveau niveau = null;
		Configuration config = null;
		ArrayList<Position> murs = new ArrayList<Position>();
		ArrayList<Position> cibles = new ArrayList<Position>();
		ArrayList<Position> caisses = new ArrayList<Position>();
		Position posjoueur = null;
		int j = 0;
		int x = 0;
		for (String line : levelString.split("\n")) {
			for (int i = 0; i < line.length(); i++) {
				switch (line.charAt(i)) {
					case '#':
						murs.add(new Position(i, j));
						break;
					case '@':
						posjoueur = new Position(i, j);
						break;
					case '.':
						cibles.add(new Position(i, j));
						break;
					case '$':
						caisses.add(new Position(i, j));
						break;
					case '*':
						caisses.add(new Position(i, j));
						cibles.add(new Position(i, j));
						break;
					default:
						break;
				}
			}
			if (x < line.length())
				x = line.length();
			j++;
		}
		niveau = new Niveau(x, j);
		config = new Configuration(niveau, posjoueur);
		for (Position position : murs)
			if (!niveau.addMur(position)) {
				System.err.println("Erreur : mur " + position + " impossible à poser");
				return null;
			}
		for (Position position : caisses)
			if (!config.addCaisse(position)) {
				System.err.println("Erreur : caisse " + position + " impossible à poser");
				return null;
			}
		for (Position position : cibles)
			if (!niveau.addCible(position)) {
				System.err.println("Erreur : cible " + position + " impossible à poser");
				return null;
			}
		return config;
	}

	public static Configuration loadSokoban(String fn, int level) {
		Configuration config = null;
		try {
			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fn)))) {
				String line = "";
				String levelString = "";
				int nblev = 0;
				while ((nblev < level && (line = buffer.readLine()) != null))
					if (line.contains(";"))
						nblev++;
				while (((line = buffer.readLine()) != null) && (!line.contains(";"))) {
					levelString += line + "\n";

				}
				config = loadSokoban(levelString);
			}
		} catch (Exception e) {
			e.printStackTrace();
			;
		}
		return config;
	}

}
