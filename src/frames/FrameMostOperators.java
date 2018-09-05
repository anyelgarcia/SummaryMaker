package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import functionality.General;
import main.Controller;

@SuppressWarnings("serial")
public class FrameMostOperators extends MyFrame{
	
	private JComboBox<String> union;
	private JTextField scaling;
	private JTextField pruning;
	
	public FrameMostOperators(){
		super();
		init();
	}
	
	@Override
	protected void init() {
		JLabel w = new JLabel("Choose the operators:");
		w.setHorizontalAlignment(JLabel.CENTER);
		w.setVerticalAlignment(JLabel.CENTER);
		w.setFont(General.TXTxs);
		w.setForeground(Color.WHITE);
		
		JPanel panel_for_the_label = new JPanel();
		panel_for_the_label.add(w);
		panel_for_the_label.setBackground(General.BLACK);
		
		mainPanel.add(panel_for_the_label, BorderLayout.NORTH);
		
		JPanel panel_center = new JPanel();
		panel_center.setBackground(General.BLACK);
		
		JPanel first_op_panel = new JPanel(new FlowLayout());
		JPanel second_op_panel = new JPanel(new FlowLayout());
		JPanel third_op_panel = new JPanel(new FlowLayout());
		
		first_op_panel.setBackground(General.BLACK);
		second_op_panel.setBackground(General.BLACK);
		third_op_panel.setBackground(General.BLACK);
		
		JLabel first_op_label = new JLabel("Union: ");
		first_op_label.setFont(General.BTNS);
		first_op_label.setForeground(Color.WHITE);
		JLabel second_op_label = new JLabel("Scaling: ");
		second_op_label.setFont(General.BTNS);
		second_op_label.setForeground(Color.WHITE);
		JLabel third_op_label = new JLabel("Pruning: ");
		third_op_label.setFont(General.BTNS);
		third_op_label.setForeground(Color.WHITE);
		
		
		first_op_panel.add(first_op_label);
		second_op_panel.add(second_op_label);
		third_op_panel.add(third_op_label);
		
		
		String options[] = {"Union by levels", "Normal Union", "Union by type"};
		union = new JComboBox<String>(options);
		union.setSelectedIndex(1);
		union.addPopupMenuListener(new PopupMenuListener(){
			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				repaint();
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				repaint();	
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				repaint();
			}
		});
		
		scaling = new JTextField("10");
		pruning = new JTextField("0");
		
		scaling.setColumns(10);
		pruning.setColumns(10);
		
		first_op_panel.add(union);
		second_op_panel.add(scaling);
		third_op_panel.add(pruning);
		
		panel_center.add(first_op_panel);
		panel_center.add(second_op_panel);
		panel_center.add(third_op_panel);
		
		panel_center.setLayout(new BoxLayout(panel_center, BoxLayout.PAGE_AXIS));
		
		mainPanel.add(panel_center, BorderLayout.CENTER);
		
		JPanel panel_for_the_buttons = new JPanel();
		panel_for_the_buttons.setBackground(General.BLACK);
		panel_for_the_buttons.setLayout(new FlowLayout());
		
		JButton ok = new JButton("OK");
		ok.setFont(General.BTNS);
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int scale = -1, threshold=-1;
				try{
					if(!scaling.getText().isEmpty()){
						scale = Integer.valueOf(scaling.getText());
						if(scale<=0){
							JOptionPane.showMessageDialog(null, "Input error.", "Please give a positive integer as an input for the scaling option.", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					if(!pruning.getText().isEmpty()){
						threshold = Integer.valueOf(pruning.getText());
					}
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "Input error.", "Please give a positive integer as an input.", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int optionunion = union.getSelectedIndex();
				Controller.getUnicaInstancia().applyUnionOperator(optionunion);
				if(scale!=-1)
					Controller.getUnicaInstancia().applyScalingOperator(scale);
				if(threshold!=-1)
					Controller.getUnicaInstancia().applyPruningOperator(threshold);
				Controller.getUnicaInstancia().changeFrame(+1);
			}
			
		});
		
		JButton goBack = new JButton("Go back");
		goBack.setFont(General.BTNS);
		goBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getUnicaInstancia().updateCurrentSummary();
				Controller.getUnicaInstancia().changeFrame(-1);
			}
			
		});
		
		panel_for_the_buttons.add(ok);
		panel_for_the_buttons.add(goBack);
		
		mainPanel.add(panel_for_the_buttons, BorderLayout.SOUTH);
		
		pack();
		repaint();
		
	}

	@Override
	public void update() {
		String gene = Controller.getUnicaInstancia().getSummary().getGene();
		
		if(gene.contains("-"))	setTitle("Genes: " + gene);
		else	setTitle("Gene: " + gene);
		
		union.setSelectedIndex(1);
		scaling.setText("10");
		pruning.setText("0");
	}

}
