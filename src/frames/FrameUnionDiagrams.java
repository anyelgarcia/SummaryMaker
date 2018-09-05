package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import functionality.General;
import main.Controller;
import objects.Node;
import objects.Summary;

@SuppressWarnings("serial")
public class FrameUnionDiagrams extends MyFrame{

	private JLabel question;
	
	private JPanel centralPane;
	
	private ArrayList<Summary<Node>> selectedGenes; 
	@SuppressWarnings("rawtypes")
	private JList listSelected;
	private JScrollPane selectedGenesScroll;
	private JPanel panel_for_the_list_of_selected;
	private JButton r;
	
	private ArrayList<Summary<Node>> notSelectedGenes; 
	@SuppressWarnings("rawtypes")
	private JList listNotSelected;
	private JScrollPane notSelectedGenesScroll;
	private JPanel panel_for_the_list_of_not_selected;
	private JButton s; 
	
	private JPanel panel_for_the_buttons;
	
	public FrameUnionDiagrams(){
		super();
		init();
	}


	@Override
	protected void init() {
		
		question = new JLabel("Combine its diagram with others?");
		question.setHorizontalAlignment(JLabel.CENTER);
		question.setVerticalAlignment(JLabel.CENTER);
		question.setFont(General.TXT);
		question.setForeground(Color.WHITE);
		
		JButton y = new JButton("Yes");
		y.setFont(General.BTNS);
		y.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				enableSecondPart();
			}
		});
		
		JButton n = new JButton("No");
		n.setFont(General.BTNS);
		n.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				centralPane.setVisible(false);
				panel_for_the_buttons.setVisible(false);
				mainPanel.changeSize(getWidth(), getHeight());
				repaint();
				Controller.getUnicaInstancia().updateCurrentSummary();
				Controller.getUnicaInstancia().changeFrame(+1);
			}
		});
		
		JPanel panel_question_buttons = new JPanel(new FlowLayout());
		panel_question_buttons.setBackground(General.BLACK);
		
		panel_question_buttons.add(question);
		panel_question_buttons.add(y);
		panel_question_buttons.add(n);
		
		mainPanel.add(panel_question_buttons, BorderLayout.NORTH);
		
		
		centralPane = new JPanel(new FlowLayout());
		centralPane.setBackground(General.BLACK);
		centralPane.setVisible(false);

		mainPanel.add(centralPane, BorderLayout.CENTER);
		
		panel_for_the_buttons = new JPanel();
		panel_for_the_buttons.setLayout(new FlowLayout());
		panel_for_the_buttons.setBackground(General.BLACK);
		
		JButton ok = new JButton("OK");
		ok.setFont(General.BTNS);
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedGenes.size()==0){
					JOptionPane.showMessageDialog(null, "You have to select at least a gene.", "Void selection", JOptionPane.ERROR_MESSAGE);
					return;
				}
				centralPane.setVisible(false);
				panel_for_the_buttons.setVisible(false);
				mainPanel.changeSize(getWidth(), getHeight());
				repaint();
				Controller.getUnicaInstancia().applyOperatorDiagramUnion(selectedGenes);
				Controller.getUnicaInstancia().changeFrame(+1);
			}
			
		});
		
		JButton goBack = new JButton("Go back");
		goBack.setFont(General.BTNS);
		goBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				centralPane.setVisible(false);
				panel_for_the_buttons.setVisible(false);
				mainPanel.changeSize(getWidth(), getHeight());
				repaint();
				Controller.getUnicaInstancia().changeFrame(-1);
			}
			
		});
		
		panel_for_the_buttons.add(ok);
		panel_for_the_buttons.add(goBack);

		panel_for_the_buttons.setVisible(false);
		
		mainPanel.add(panel_for_the_buttons, BorderLayout.SOUTH);
		
	}
	protected void enableSecondPart() {
		centralPane.setVisible(true);
		panel_for_the_buttons.setVisible(true);

		//pack();
		//validate();
		//mainPanel.validate();
		
		mainPanel.changeSize(getWidth(), getHeight());
		repaint();
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void update() {
		setTitle("Gene: " + Controller.getUnicaInstancia().getCurrentGeneName());
		ArrayList<Summary<Node>> genes = Controller.getUnicaInstancia().getGenes();
		int currentGene = Controller.getUnicaInstancia().getCurrentGene();
		
		if(panel_for_the_list_of_selected!=null)centralPane.remove(panel_for_the_list_of_selected);
		if(panel_for_the_list_of_not_selected!=null)centralPane.remove(panel_for_the_list_of_not_selected);
		
		notSelectedGenes = new ArrayList<Summary<Node>>(genes);
		notSelectedGenes.remove(currentGene);
		
		selectedGenes = new ArrayList<Summary<Node>>(genes.subList(currentGene, currentGene+1));
		
		listNotSelected = new JList(notSelectedGenes.toArray());
		listNotSelected.setFont(General.TXTxs);
		
		listSelected = new JList(selectedGenes.toArray());
		listSelected.setFont(General.TXTxs);
		
		selectedGenesScroll = new JScrollPane(listSelected);
		notSelectedGenesScroll = new JScrollPane(listNotSelected);
		
		panel_for_the_list_of_selected = new JPanel();
		panel_for_the_list_of_selected.setBackground(General.BLACK);
		
		panel_for_the_list_of_not_selected = new JPanel();
		panel_for_the_list_of_not_selected.setBackground(General.BLACK);
		
		
		JLabel subtitle1 = new JLabel("Genes");
		subtitle1.setHorizontalAlignment(JLabel.CENTER);
		subtitle1.setVerticalAlignment(JLabel.CENTER);
		subtitle1.setFont(General.TXTxs);
		subtitle1.setForeground(Color.WHITE);
		
		
		r = new JButton("Remove");
		r.setFont(General.BTNS);
		r.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int values[] = listSelected.getSelectedIndices();
				if(values.length>0){
					for(int a : values){
						Summary<Node> aux = selectedGenes.get(a);
						notSelectedGenes.add(aux);
					}
					selectedGenes.removeAll(notSelectedGenes);
					listSelected = new JList(selectedGenes.toArray());
					listSelected.setFont(General.TXTxs);
					
					listNotSelected = new JList(notSelectedGenes.toArray());
					listNotSelected.setFont(General.TXTxs);
					
					
					panel_for_the_list_of_not_selected.remove(notSelectedGenesScroll);
					panel_for_the_list_of_not_selected.remove(s);
					notSelectedGenesScroll = new JScrollPane(listNotSelected);
					panel_for_the_list_of_not_selected.add(notSelectedGenesScroll);
					panel_for_the_list_of_not_selected.add(s);
	
					panel_for_the_list_of_selected.remove(selectedGenesScroll);
					panel_for_the_list_of_selected.remove(r);
					selectedGenesScroll = new JScrollPane(listSelected);
					panel_for_the_list_of_selected.add(selectedGenesScroll);
					panel_for_the_list_of_selected.add(r);
					pack();
					repaint();
				}
			}
		});
		
		
		panel_for_the_list_of_selected.add(subtitle1);
		panel_for_the_list_of_selected.add(selectedGenesScroll);
		panel_for_the_list_of_selected.add(r);
		panel_for_the_list_of_selected.setLayout(new BoxLayout(panel_for_the_list_of_selected, BoxLayout.PAGE_AXIS));
		
		
		JLabel subtitle2 = new JLabel("Selection");
		subtitle2.setHorizontalAlignment(JLabel.CENTER);
		subtitle2.setVerticalAlignment(JLabel.CENTER);
		subtitle2.setFont(General.TXTxs);
		subtitle2.setForeground(Color.WHITE);
		
		
		s = new JButton("Select");
		s.setFont(General.BTNS);
		s.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int values[] = listNotSelected.getSelectedIndices();
				if(values.length>0){
					for(int a : values){
						Summary<Node> aux = notSelectedGenes.get(a);
						selectedGenes.add(aux);
					}
					notSelectedGenes.removeAll(selectedGenes);
					listSelected = new JList(selectedGenes.toArray());
					listSelected.setFont(General.TXTxs);
					
					listNotSelected = new JList(notSelectedGenes.toArray());
					listNotSelected.setFont(General.TXTxs);
					
					
					panel_for_the_list_of_not_selected.remove(notSelectedGenesScroll);
					panel_for_the_list_of_not_selected.remove(s);
					notSelectedGenesScroll = new JScrollPane(listNotSelected);
					panel_for_the_list_of_not_selected.add(notSelectedGenesScroll);
					panel_for_the_list_of_not_selected.add(s);
	
					panel_for_the_list_of_selected.remove(selectedGenesScroll);
					panel_for_the_list_of_selected.remove(r);
					selectedGenesScroll = new JScrollPane(listSelected);
					panel_for_the_list_of_selected.add(selectedGenesScroll);
					panel_for_the_list_of_selected.add(r);
					pack();
					repaint();
				}
			}
		});
		
		
		panel_for_the_list_of_not_selected.add(subtitle2);
		panel_for_the_list_of_not_selected.add(notSelectedGenesScroll);
		panel_for_the_list_of_not_selected.add(s);
		panel_for_the_list_of_not_selected.setLayout(new BoxLayout(panel_for_the_list_of_not_selected, BoxLayout.PAGE_AXIS));
		
		centralPane.add(panel_for_the_list_of_not_selected);
		centralPane.add(panel_for_the_list_of_selected);
		
		
	}

}
