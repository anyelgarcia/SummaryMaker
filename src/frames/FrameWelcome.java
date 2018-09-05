package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import functionality.General;
import main.Controller;

@SuppressWarnings("serial")
public class FrameWelcome extends MyFrame{

	
	private boolean selecting;
	
	public FrameWelcome(){
		super();
		init();
	}

	@Override
	protected void init() {
		
		setTitle("Welcome!");
		selecting = false;
		
		JLabel w = new JLabel("Welcome!");
		w.setHorizontalAlignment(JLabel.CENTER);
		w.setVerticalAlignment(JLabel.CENTER);
		w.setFont(General.TTLS);
		w.setForeground(Color.WHITE);
		mainPanel.add(w, BorderLayout.CENTER);
		
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				selectFile();
			}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	
	protected void selectFile() {
		selecting = true;
		
		JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter ff = new FileNameExtensionFilter("CSV file", "csv");
		fc.setDialogTitle("Rules file selection");
		
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(ff);
		fc.setMultiSelectionEnabled(true);
		
		int returnValue = fc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			
			File[] files = fc.getSelectedFiles();
			Optional<Boolean> a = Arrays.asList(files).stream().map(new Function<File, Boolean>(){
				public Boolean apply(File x){
					return Controller.getUnicaInstancia().readRuleFile(x.getPath())!=null;
				}
			}).filter(b->!b).findAny();
			
			if(a.isPresent()){
				selecting = false;
				return;
			}
			selecting = false;
			Controller.getUnicaInstancia().changeFrame(+1);
		}
		if (returnValue ==JFileChooser.CANCEL_OPTION){
			Controller.getUnicaInstancia().closeAll();
		}
		selecting = false;
		
	}
	
	public boolean isSelecting() {
		return selecting;
	}
	
	public void setSelecting(boolean selecting) {
		this.selecting = selecting;
	}

	@Override
	public void update() {
		if(!selecting)selectFile();
	}
	
	

}
