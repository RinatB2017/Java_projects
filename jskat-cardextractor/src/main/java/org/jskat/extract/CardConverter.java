package org.jskat.extract;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.batik.transcoder.TranscoderException;
import org.jskat.gui.CardExtractorGUI;

public class CardConverter {

	public static void convertAllCards(CardExtractorGUI gui,
			ExtractConfiguration conf) throws TranscoderException, IOException {

		ExecutorService threadPool = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors() * 2);

		int columns = conf.columnSymbols.length();
		int rows = conf.rowSymbols.length();
		int cardWidth = conf.width / columns;
		int cardHeight = conf.height / rows;

		gui.setTasks(columns * rows);

		for (int yCard = 0; yCard < columns; yCard++) {
			for (int xCard = 0; xCard < rows; xCard++) {

				String cardName = String.valueOf(conf.rowSymbols.charAt(xCard))
						+ "-"
						+ String.valueOf(conf.columnSymbols.charAt(yCard))
						+ ".png";

				Rectangle areaOfInterest = new Rectangle(cardWidth * yCard,
						cardHeight * xCard, cardWidth - conf.gap, cardHeight
								- conf.gap);

				threadPool.execute(new CardExtractor(gui, conf.location,
						cardName, areaOfInterest, getScale(conf.targetHeight,
								conf.height, rows)));
			}
		}

		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}
		System.out.println("Finished all threads");
	}

	private static float getScale(int targetHeight, int height, int rows) {
		return (float) targetHeight / (height / rows);
	}
}