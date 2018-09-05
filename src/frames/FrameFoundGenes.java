package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import functionality.General;
import main.Controller;
import objects.Node;
import objects.Summary;


@SuppressWarnings("serial")
public class FrameFoundGenes extends MyFrame{
	
	private JScrollPane list;
	private JPanel panel_for_the_list;
	
	public FrameFoundGenes(){
		super();
		init();
	}
	

	@Override
	protected void init() {
		setTitle("List of found genes.");
		JLabel w = new JLabel("Found the following genes:");
		w.setHorizontalAlignment(JLabel.CENTER);
		w.setVerticalAlignment(JLabel.CENTER);
		w.setFont(General.TXTxs);
		w.setForeground(Color.WHITE);
		
		JPanel panel_for_the_label = new JPanel();
		panel_for_the_label.setBackground(General.BLACK);
		panel_for_the_label.add(w);
		
		mainPanel.add(panel_for_the_label, BorderLayout.NORTH);
		
		panel_for_the_list = new JPanel();
		panel_for_the_list.setBackground(General.BLACK);
		mainPanel.add(panel_for_the_list, BorderLayout.CENTER);
		
		list = null;
		
		JPanel panel_for_the_buttons = new JPanel();
		panel_for_the_buttons.setLayout(new FlowLayout());
		panel_for_the_buttons.setBackground(General.BLACK);
		
		JButton ok = new JButton("OK");
		ok.setFont(General.BTNS);
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getUnicaInstancia().updateCurrentSummary();
				Controller.getUnicaInstancia().changeFrame(+1);
			}
			
		});
		
		JButton goBack = new JButton("Go back");
		goBack.setFont(General.BTNS);
		goBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getUnicaInstancia().clearSummaries();
				Controller.getUnicaInstancia().changeFrame(-1);
			}
			
		});
		
		panel_for_the_buttons.add(ok);
		panel_for_the_buttons.add(goBack);
		
		mainPanel.add(panel_for_the_buttons, BorderLayout.SOUTH);
	}
	
	public void update(){
		if(list!=null)panel_for_the_list.remove(list);
		@SuppressWarnings("unchecked")
		JList<Summary<Node>> items = new JList<Summary<Node>>((Summary<Node>[]) Controller.getUnicaInstancia().getGenes().toArray(new Summary[1]));
		items.setFont(General.TXT);
		list = new JScrollPane(items);
		panel_for_the_list.add(list);
		
		validate();
		repaint();
	}

}
