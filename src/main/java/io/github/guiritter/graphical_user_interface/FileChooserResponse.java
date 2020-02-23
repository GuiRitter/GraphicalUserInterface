package io.github.guiritter.graphical_user_interface;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * Data that is returned when the
 * {@link JFileChooser} dialog is closed.
 * @author Guilherme Alan Ritter
 */
public class FileChooserResponse {

	/**
	 * Either {@link JFileChooser#APPROVE_OPTION},
	 * {@link JFileChooser#CANCEL_OPTION}
	 * or {@link JFileChooser#ERROR_OPTION}.
	 */
	public final int state;

	public final File selectedFile;

	public final File selectedFiles[];

	/**
	 * Builds a new response.
	 * @param state either {@link JFileChooser#APPROVE_OPTION},
	 * {@link JFileChooser#CANCEL_OPTION}
	 * or {@link JFileChooser#ERROR_OPTION}
	 */
	public FileChooserResponse(int state, File selectedFile, File selectedFiles[]) {
		this.state = state;
		this.selectedFile = selectedFile;
		this.selectedFiles = selectedFiles;
	}
}
