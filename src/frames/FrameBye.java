package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

import functionality.General;
import main.Controller;

@SuppressWarnings("serial")
public class FrameBye extends MyFrame{

	
	
	public FrameBye(){
		super();
		init();
	}

	@Override
	protected void init() {
		
		setTitle("Goodbye!");
		
		JLabel w = new JLabel("Goodbye!");
		w.setHorizontalAlignment(JLabel.CENTER);
		w.setVerticalAlignment(JLabel.CENTER);
		w.setFont(General.TTLS);
		w.setForeground(Color.WHITE);
		mainPanel.add(w, BorderLayout.CENTER);
		
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				Controller.getUnicaInstancia().closeAll();
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

	@Override
	public void update() {
	}
	
	

}
