package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import functionality.General;
import main.Controller;

@SuppressWarnings("serial")
public class FrameRepeat extends MyFrame{

	double integrity;
	public FrameRepeat(){
		super();
		init();
	}
	@Override
	protected void init() {
		setTitle("Saved without problems.");
		JLabel statement = new JLabel("There are still genes unused");
		statement.setHorizontalAlignment(JLabel.CENTER);
		statement.setVerticalAlignment(JLabel.CENTER);
		statement.setFont(General.TXT);
		statement.setForeground(Color.WHITE);
		
		mainPanel.add(statement, BorderLayout.NORTH);
		
		JLabel question = new JLabel("Do you want to process the leftover genes?");
		question.setHorizontalAlignment(JLabel.CENTER);
		question.setVerticalAlignment(JLabel.CENTER);
		question.setFont(General.TXT);
		question.setForeground(Color.WHITE);
		
		JPanel aux = new JPanel(new FlowLayout());
		aux.setBackground(General.BLACK);
		
		
		JButton y = new JButton("Yes");
		y.setFont(General.BTNS);
		y.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getUnicaInstancia().changeFrame(-3);
			}
		});
		
		JButton n = new JButton("No");
		n.setFont(General.BTNS);
		n.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getUnicaInstancia().changeFrame(+1);
			}
		});
		
		JPanel panel_question_buttons = new JPanel(new FlowLayout());
		panel_question_buttons.setBackground(General.BLACK);
		
		panel_question_buttons.add(question);
		aux.add(y);
		aux.add(n);
		
		panel_question_buttons.add(aux);
		
		mainPanel.add(panel_question_buttons, BorderLayout.CENTER);
		
	}

	@Override
	public void update() {
		saveFile();
		
	}
	
	protected void saveFile() {
		
		JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Text file", "txt");
		fc.setDialogTitle("Please, select where to save the diagram");
		
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(ff);
		fc.setMultiSelectionEnabled(false);
		
		integrity = Controller.getUnicaInstancia().getIntegrity();
		JOptionPane.showMessageDialog(null, "The integrity of the diagram is: " + integrity, "Integrity", JOptionPane.INFORMATION_MESSAGE);
		
		int returnValue = fc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			
			File file = fc.getSelectedFile();
			
			if(!Controller.getUnicaInstancia().getSummary().isLeveled()){
				Controller.getUnicaInstancia().writeFile2(file);
			}else{
				Controller.getUnicaInstancia().writeFile(file);
			}
			
			Controller.getUnicaInstancia().updateGenesSaved();
			if(Controller.getUnicaInstancia().getCurrentGene()==-1)
				Controller.getUnicaInstancia().changeFrame(+1);
			
		}else{
			Controller.getUnicaInstancia().changeFrame(-1);
			return;
		}
	}

}
