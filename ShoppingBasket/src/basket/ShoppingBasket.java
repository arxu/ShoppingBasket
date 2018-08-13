package basket;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("unused")

public class ShoppingBasket {
	static ArrayList<OrderItem> basket = new ArrayList<OrderItem>(); //shopping basket
	
	private static int type;
	private static boolean dup = false;
	
	//saving to text file
	static void save() {
		PrintWriter writer;
		File file = new File("basket.txt"); 
		try {
			writer = new PrintWriter(file, "UTF-8");
			double total = 0;
			int items = 0;
			NumberFormat formatter = NumberFormat.getCurrencyInstance(); //ensures price is to 2 d.p.
			for(int i = 0; i < basket.size(); i++) { //loop through basket to add each item
				writer.println(basket.get(i).getName() + ", " + basket.get(i).getAmt() + ", " + formatter.format(basket.get(i).getPrice()));
				total += basket.get(i).getAmt() * basket.get(i).getPrice();
				items += basket.get(i).getAmt();
			}
			writer.println("TOTAL, " + items + ", " + formatter.format(total)); //write totals
			writer.close();
			Desktop.getDesktop().edit(file); //open default text editor
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	//after OK is pressed in the add/delete/edit interface
	static void okPressed(JTextField name, JSpinner amt, JTextField price, JFrame frame2) { 
		try {
			OrderItem temp = new OrderItem(); //temporary OrderItem for ease of access to name/amount/price
			temp.setName(name.getText());
			dup = false;
			if (type == 1) { //add
				temp.setAmt((int) amt.getValue());
				temp.setPrice(Double.parseDouble(price.getText()));
				try {
					for(int i = 0; i < basket.size(); i++) {
						if(basket.get(i).getName().equals(temp.getName())) {
							dup = true;
						}
					}
					if(dup == false) {
						frame2.setVisible(false);
						basket.add(temp);
					}
				} catch(Exception e1){
					System.out.println(e1);
				}
			} else if (type == 2) { //delete
				try {
					for(int i = 0; i < basket.size(); i++) {
						if(basket.get(i).getName().equals(temp.getName())) {
							frame2.setVisible(false);
							basket.remove(i);
						}
					}
				} catch(Exception e1){
					e1.printStackTrace();
				}
			} else { //edit
				frame2.setVisible(false);
				temp.setAmt((int) amt.getValue());
				temp.setPrice(Double.parseDouble(price.getText()));
				for(int i = 0; i < basket.size(); i++) { //looking for a match
					if(basket.get(i).getName().equals(temp.getName())) {
						basket.get(i).setAmt(temp.getAmt());
						basket.get(i).setPrice(temp.getPrice());
					}
				}
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

	//redraw the shopping basket after interaction
	private static void redrawBasket(JTextArea displayN, JTextArea displayA, JTextArea displayP, JTextField name, JSpinner amt, JTextField price, JTextField totalPrice, JTextField totalItems) {
		double total = 0;
		int items = 0;
		displayN.setText("Name\n");
		displayA.setText("Amount\n");
		displayP.setText("Price\n");
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
		for(int i = 0; i < basket.size(); i++) { //inserting every item
			displayN.setText(displayN.getText() + (basket.get(i).getName() + "\n" ));
			displayA.setText(displayA.getText() + (basket.get(i).getAmt() + "\n" ));
			displayP.setText(displayP.getText() + (formatter.format(basket.get(i).getPrice()) + "\n" ));
			total += basket.get(i).getAmt() * basket.get(i).getPrice(); //adding the item to the totals
			items += basket.get(i).getAmt();
			
			name.setText(""); //emptying the fields
			amt.setValue(0);
			price.setText("");
		}
		totalPrice.setText(formatter.format(total)); //set the totals in the top right
		totalItems.setText(Integer.toString(items));
	}
	
	//display add/delete/edit interface
	static void showPrompt(String inp, int t, JFrame frame2, JSpinner amt, JTextField price, JLabel amtLabel, JLabel priceLabel) {
        frame2.setTitle(inp);
		frame2.setVisible(true);
		type = t;
		if(inp.equals("Delete Item")) {
			//delete does not need amount or price
	        amt.setVisible(false);
	        price.setVisible(false);
	        amtLabel.setVisible(false);
	        priceLabel.setVisible(false);
		} else {
			//add/edit needs amount and price
	        amt.setVisible(true);
	        price.setVisible(true);
	        amtLabel.setVisible(true);
	        priceLabel.setVisible(true);
		}
	}
	
	//draws the main interface
	private static void drawInterface() {
		//shopping basket main interface
		JFrame frame = new JFrame("Basket"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,4));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		c.insets = new Insets(2, 2, 2, 2);
		
		//name display
		JTextArea displayN = new JTextArea("Name\n");
		displayN.setEditable(false);
		displayN.setLineWrap(true);
		displayN.setWrapStyleWord(true);
		panel.add(displayN);
		
		//amount display
		JTextArea displayA = new JTextArea("Amount\n");
		displayA.setEditable(false);
		displayA.setLineWrap(true);
		displayA.setWrapStyleWord(true);
		panel.add(displayA);
		
		//price display
		JTextArea displayP = new JTextArea("Price\n");
		displayP.setEditable(false);
		displayP.setLineWrap(true);
		displayP.setWrapStyleWord(true);
		panel.add(displayP);
		
		//button panel
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new GridLayout(7,1));
		
		//totals in the top right
		JPanel totals = new JPanel();
		totals.setLayout(new GridLayout(2,2));
		totals.add(new JLabel("TOTAL PRICE:"));
		totals.add(new JLabel("TOTAL ITEMS:"));
		JTextField totalPrice = new JTextField("’0.00");
		totalPrice.setEditable(false);
		JTextField totalItems = new JTextField("0");
		totalItems.setEditable(false);
		totals.add(totalPrice);
		totals.add(totalItems);
		bPanel.add(totals);
		
		//add button
		JButton addItem = new JButton("Add");
		addItem.setActionCommand("startAdd");
		addItem.setToolTipText("Add an item to the shopping basket");
		bPanel.add(addItem);
		
		//delete button
		JButton delItem = new JButton("Delete");
		delItem.setActionCommand("delete");
		delItem.setToolTipText("Delete an item from the shopping basket");
		bPanel.add(delItem);
		
		//edit button
		JButton ediItem = new JButton("Edit");
		ediItem.setActionCommand("edit");
		ediItem.setToolTipText("Edit an item in the shopping basket");
		bPanel.add(ediItem);
		//clear button
		JButton clrList = new JButton("Clear");
		clrList.setActionCommand("clear");
		clrList.setToolTipText("Clear the shopping basket");
		bPanel.add(clrList);
		
		//save button
		JButton savList = new JButton("Save");
		savList.setActionCommand("save");
		savList.setToolTipText("Save the shopping basket contents to a text document");
		bPanel.add(savList);
		
		//exit button
		JButton exitBtn = new JButton("Exit");
		exitBtn.setActionCommand("exit");
		exitBtn.setToolTipText("Exit the shopping basket application");
		bPanel.add(exitBtn);
		panel.add(bPanel);
		
        frame.add(panel);
        frame.pack();
        frame.setMinimumSize(new Dimension(700,400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //add/delete/edit interface
		JFrame frame2 = new JFrame("Add Item");
        frame2.setLayout(new BorderLayout());
        JPanel panel2 = new JPanel();
        
        panel2.setLayout(new GridBagLayout());
		c.gridx = 0; c.gridy = 0;
		c.insets = new Insets(2, 2, 2, 2);
		
		//labelling the fields
		panel2.add(new JLabel("Product Name"), c);
		c.gridx++;
		JLabel amtLabel = new JLabel("Amount");
		panel2.add(amtLabel, c);
		c.gridx++;
		JLabel priceLabel = new JLabel("Price");
		panel2.add(priceLabel, c);
		
		//name field
		c.gridx = 0; c.gridy++;
		JTextField name = new JTextField(10);
		panel2.add(name, c);
		
		//amount field
		c.gridx++;
		JSpinner amt = new JSpinner(new SpinnerNumberModel(0,0,999,1));
		panel2.add(amt, c);
		
		//price field
		c.gridx++;
		JTextField price = new JTextField(6);
		panel2.add(price, c);
		
		//OK button
		c.gridx = 0; c.gridy++; c.gridwidth = 5;
		JButton addBtn = new JButton("OK");
		addBtn.setActionCommand("add");
		panel2.add(addBtn, c);
		
		//button actions
		class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((e.getActionCommand()) == "add") { //pressed OK button in add/delete/edit interface
					okPressed(name, amt, price, frame2);
					redrawBasket(displayN, displayA, displayP, name, amt, price, totalPrice, totalItems);
				} else if((e.getActionCommand()) == "startAdd") { //add
			        showPrompt("Add Item", 1, frame2, amt, price, amtLabel, priceLabel);
				} else if((e.getActionCommand()) == "delete") {
					showPrompt("Delete Item", 2, frame2, amt, price, amtLabel, priceLabel);
				} else if((e.getActionCommand()) == "edit") {
					showPrompt("Edit Item", 3, frame2, amt, price, amtLabel, priceLabel);
				} else if((e.getActionCommand()) == "clear") {
					basket.removeAll(basket);
					redrawBasket(displayN, displayA, displayP, name, amt, price, totalPrice, totalItems);
				} else if((e.getActionCommand()) == "save") {
					save();
				} else {
					frame.dispose();
					
				}
			}
		}
		
		ButtonListener bl = new ButtonListener();
		addBtn.addActionListener(bl); //add listener to buttons
		addItem.addActionListener(bl);
		delItem.addActionListener(bl);
		ediItem.addActionListener(bl);
		clrList.addActionListener(bl);
		savList.addActionListener(bl);
		exitBtn.addActionListener(bl);
		
        frame2.add(panel2);
        frame2.pack();
        frame2.setLocationRelativeTo(null);
		frame2.setResizable(false);
	}
	
	public static void main(String[] args) {
		drawInterface();
		
	}
}
