package frames;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;

import functionality.General;


@SuppressWarnings("serial")
public abstract class MyFrame extends JFrame{

	
	protected MyPanel mainPanel;
	
	public MyFrame(){
		this.inicializarGeneral();
	}
	
	private void inicializarGeneral() {
		setPreferredSize(General.PREF);
		setMaximumSize(General.MAX);
		setMinimumSize(General.MIN);
		setLocationRelativeTo(null);
		mainPanel = new MyPanel(getWidth(), getHeight());
		mainPanel.setLayout(new BorderLayout());
		mainPanel.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent evt){
				mainPanel.changeSize(getWidth(), getHeight());
			}
		});
		add(mainPanel);
		
	}
	

	protected abstract void init();
	public abstract void update();
	
	
	
	
}