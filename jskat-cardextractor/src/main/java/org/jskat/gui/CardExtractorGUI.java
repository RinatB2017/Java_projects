package org.jskat.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.transcoder.TranscoderException;
import org.jskat.extract.CardConverter;
import org.jskat.extract.ExtractConfiguration;

public class CardExtractorGUI extends JFrame {

	// Starts the extraction
	private JButton extractButton = new JButton("Extract cards...");

	private JComboBox<ExtractConfiguration> cardSets = new JComboBox<>();

	// The status label.
	private JLabel label = new JLabel();

	private JProgressBar progressBar = new JProgressBar();

	// The SVG canvas.
	private JSVGCanvas svgCanvas = new JSVGCanvas();

	public CardExtractorGUI() {
		setTitle("Card Extractor");
		setMinimumSize(new Dimension(600, 400));

		setContentPane(createComponents());

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		loadSelectedCardSet();
	}

	public JComponent createComponents() {
		// Create a panel and add the button, status label and the SVG canvas.
		final JPanel panel = new JPanel(new BorderLayout());

		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

		for (ExtractConfiguration conf : ExtractConfiguration.values()) {
			cardSets.addItem(conf);
		}
		cardSets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadSelectedCardSet();
			}
		});

		p.add(cardSets);
		p.add(extractButton);
		p.add(label);
		p.add(progressBar);

		panel.add("North", p);
		panel.add("Center", svgCanvas);

		final CardExtractorGUI gui = this;

		extractButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				extractButton.setEnabled(false);
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						CardConverter saveAsSeparateCards = new CardConverter();
						try {
							saveAsSeparateCards.convertAllCards(gui,
									(ExtractConfiguration) cardSets
											.getSelectedItem());
						} catch (TranscoderException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						return null;
					}

					@Override
					public void done() {
					}
				};
				worker.execute();
			}
		});

		// Set the JSVGCanvas listeners.
		setJSVGCanvasListeners();

		return panel;
	}

	private void setJSVGCanvasListeners() {
		svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
			@Override
			public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
				label.setText("Document Loading...");
			}

			@Override
			public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
				label.setText("Document Loaded.");
			}
		});

		svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
			@Override
			public void gvtBuildStarted(GVTTreeBuilderEvent e) {
				label.setText("Build Started...");
			}

			@Override
			public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
				label.setText("Build Done.");
				pack();
			}
		});

		svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
			@Override
			public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
				label.setText("Rendering Started...");
			}

			@Override
			public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
				label.setText("");
			}
		});
	}

	private void loadSelectedCardSet() {
		ExtractConfiguration conf = (ExtractConfiguration) cardSets
				.getSelectedItem();
		File f = new File(conf.location);
		try {
			svgCanvas.setURI(f.toURL().toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void setTasks(int count) {
		progressBar.setMinimum(0);
		progressBar.setMaximum(count);
		progressBar.setValue(0);
	}

	public void taskCompleted() {
		progressBar.setValue(progressBar.getValue() + 1);

		if (progressBar.getValue() == progressBar.getMaximum()) {
			extractButton.setEnabled(true);
		}
	}
}
