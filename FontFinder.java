package font_finder;

import java.io.File;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FontFinder extends Application {

	private List<File> fonts = new ArrayList<File>();;
	private long computationTime;
	private long visitedFiles;
	private long consideredFiles;
	private long skippedFiles;

	public static void main(final String... args) {
		Application.launch(FontFinder.class, args);
	}// main()

	@Override
	public void start(final Stage primaryStage) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose start-directory");
		final File directory = directoryChooser.showDialog(primaryStage);
		showResult(directory);
	}// start()

	private void showResult(final File startDir) {
		final long startTime = System.currentTimeMillis();
		// ==================================================
		getFontFiles(startDir);
		// ==================================================
		final long endTime = System.currentTimeMillis();
		computationTime = endTime - startTime;
		printResult();
	}// method()

	private void getFontFiles(final File file) {
		visitedFiles++;
		if (hasToBeConsidered(file)) { // proves that path is valid
			if (file.isFile()) { //wenn datei
				if (matches(file)) { // wenn Font (wird geprüft durch methode matches)
					fonts.add(file); // font wird gespeichert
					consideredFiles++; // font zähler wird addiert
				} else {
					skippedFiles++; // kein font dann skippedFiles hochzählen
				} // else
			} else { // if file is directory
				final File[] files = file.listFiles(); // wird die unterodner liste vom verzeichnis im Array files gespeichert
				if (files != null) { // wenn es unterordner gibt
					for (final File f : files) { // dann für jeden ordner getFontFiles rekursiv aufrufen
						getFontFiles(f);
					} // for
				} // if
			} // else
		} else {
			skippedFiles++; // wenn shortcut etc. dann skippedFiles hochzählen
		} // else
	}// method()

	private boolean matches(final File file) {
		final String path = file.getAbsolutePath();//speichert den Pfad einer datei vom ausgewählten ordner in path
		return path.matches(".*\\.([Tt][Tt][Cc]|[Tt][Tt][Ff]|[Oo][Tt][Ff])"); // ob path dem RegEx zutrifft und gibt dann boolean wert zurück
	}// method()

	private void printResult() {
		for (final File file : fonts) {// läuft den arraylist mit den fonts durch
			System.out.println(file); // alle fonts werden ausgegeben
		} // for
		System.out.println();
		
		System.out.println("Computation time: " + computationTime + " milliseconds");
		System.out.println("Visited files: " + visitedFiles);
		System.out.println("Considered files: " + consideredFiles);
		System.out.println("Skipped files: " + skippedFiles);
	}// method()

	private boolean hasToBeConsidered(final File file) {
		final Pattern p1 = Pattern.compile(".*\\.lnk$");
		final Matcher m1 = p1.matcher(file.getName());
		final boolean matched1 = m1.find();
		//
		final boolean notConsideredAsActualFile = Files.isSymbolicLink(file.toPath()) || matched1;
		return !notConsideredAsActualFile;
	}// method()

}// class