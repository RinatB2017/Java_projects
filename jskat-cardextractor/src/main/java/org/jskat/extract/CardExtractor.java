package org.jskat.extract;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.jskat.gui.CardExtractorGUI;

public class CardExtractor implements Runnable {

	final String svgFileLocation;
	final String cardName;
	final Rectangle areaOfInterest;
	final Float scale;
	final CardExtractorGUI gui;

	public CardExtractor(CardExtractorGUI gui, String svgFileLocation,
			String cardName, Rectangle aoi, Float scale)
			throws MalformedURLException, FileNotFoundException {

		this.gui = gui;
		this.svgFileLocation = svgFileLocation;
		this.cardName = cardName;
		this.areaOfInterest = aoi;
		this.scale = scale;
	}

	@Override
	public void run() {
		try {
			tile();
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			gui.taskCompleted();
		}
	}

	public void tile() throws TranscoderException, IOException {

		OutputStream outputStream = createOutputStream();
		PNGTranscoder trans = createTrancoder();

		trans.transcode(createInput(), new TranscoderOutput(outputStream));

		// Flush and close the output.
		outputStream.flush();
		outputStream.close();

		System.out.println(cardName + " created");
	}

	private PNGTranscoder createTrancoder() {
		PNGTranscoder trans = new PNGTranscoder();

		// Set hints to indicate the dimensions of the output image
		// and the input area of interest.
		trans.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, new Float(
				areaOfInterest.width) * scale);
		trans.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, new Float(
				areaOfInterest.height) * scale);
		trans.addTranscodingHint(SVGAbstractTranscoder.KEY_AOI, areaOfInterest);

		return trans;
	}

	private TranscoderInput createInput() throws MalformedURLException {
		String svgURI = new File(svgFileLocation).toURL().toString();
		return new TranscoderInput(svgURI);
	}

	private OutputStream createOutputStream() throws FileNotFoundException {
		return new FileOutputStream(System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator") + cardName);
	}
}
