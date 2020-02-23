package io.github.guiritter.graphical_user_interface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.Arrays;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Wraps a Swing {@link javax.swing.JComponent}
 * in a {@link javax.swing.JPanel} with a {@link javax.swing.JLabel}
 * using the {@link javax.swing.BoxLayout}.
 * @author Guilherme Alan Ritter
 */
public final class LabelledComponentFactory {

	/**
	 * Builds a {@link javax.swing.JPanel}
	 * containing a {@link javax.swing.JButton} with the specified text
	 * that opens a file chooser
	 * and a non editable {@link javax.swing.JTextField}
	 * that shows the selected file.
	 * @param title the text to be displayed by the {@link javax.swing.JLabel}
	 * @param componentAlignment X alignment from {@link javax.swing.SwingConstants}
	 * @param panelAlignment Y alignment from {@link javax.swing.SwingConstants}
	 * @param labelComponentSpace the space between the label and the component
	 * @param fileSelectionMode either {@link javax.swing.JFileChooser#FILES_ONLY}
	 * or {@link javax.swing.JFileChooser#DIRECTORIES_ONLY}
	 * @param response to be called when a file is selected
	 * @return the finished {@link javax.swing.JPanel}
	 * with a {@link javax.swing.BoxLayout}.
	 */
	public static JPanel buildFileChooser(
			String title,
			float componentAlignment,
			float panelAlignment,
			int labelComponentSpace,
			int fileSelectionMode,
			Consumer<FileChooserResponse> response) {
		JButton button = new JButton(title);
		JTextField textField = new JTextField();
		JPanel panel = buildLabelledComponent(
				title,
				button,
				textField,
				componentAlignment,
				panelAlignment,
				labelComponentSpace
		);
		textField.setEditable(false);
		JFileChooser chooser = new JFileChooser();
		panel.addPropertyChangeListener((PropertyChangeEvent evt) -> {
			button.setEnabled(panel.isEnabled());
			textField.setEnabled(panel.isEnabled());
		});
		chooser.setFileSelectionMode(fileSelectionMode);
		button.addActionListener((ActionEvent event) -> {
			int state = chooser.showOpenDialog(panel);
			File selectedFile = chooser.getSelectedFile();
			File selectedFiles[] = chooser.getSelectedFiles();
			if (JFileChooser.APPROVE_OPTION == state) {
				if ((selectedFiles != null) && (selectedFiles.length > 0)) {
					textField.setText(Arrays.toString(selectedFiles));
				} else {
					textField.setText(selectedFile.toString());
				}
			} else {
				textField.setText("");
			}
			response.accept(new FileChooserResponse(
					state,
					selectedFile,
					selectedFiles
			));
		});
		return panel;
	}

	private static JPanel buildLabelledComponent(
			String title,
			JComponent label,
			JComponent component,
			float componentAlignment,
			float panelAlignment,
			int labelComponentSpace) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		label.setAlignmentX(componentAlignment);
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(
		 labelComponentSpace, labelComponentSpace)));
		component.setAlignmentX(componentAlignment);
		panel.add(component);
		panel.setAlignmentY(panelAlignment);
		return panel;
	}

	/**
	 * Builds a {@link javax.swing.JPanel}
	 * containing a {@link javax.swing.JLabel} with the specified text
	 * and the specified {@link javax.swing.JComponent}.
	 * @param title the text to be displayed by the {@link javax.swing.JLabel}
	 * @param component to be displayed below the {@link javax.swing.JLabel}
	 * @param componentAlignment X alignment from {@link javax.swing.SwingConstants}
	 * @param panelAlignment Y alignment from {@link javax.swing.SwingConstants}
	 * @param labelComponentSpace the space between the label and the component
	 * @return the finished {@link javax.swing.JPanel}
	 * with a {@link javax.swing.BoxLayout}.
	 */
	public static JPanel buildLabelledComponent(
			String title,
			JComponent component,
			float componentAlignment,
			float panelAlignment,
			int labelComponentSpace) {
		JLabel label = new JLabel(title);
		JPanel panel = buildLabelledComponent(
				title,
				label,
				component,
				componentAlignment,
				panelAlignment,
				labelComponentSpace
		);
		component.addPropertyChangeListener((PropertyChangeEvent evt) ->
			label.setEnabled(component.isEnabled())
		);
		return panel;
	}
}
