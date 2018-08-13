package basket;

import static org.junit.Assert.*;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.junit.Before;
import org.junit.Test;

public class ShoppingBasketTest {

	private JFrame frame2;
	private JPanel panel2;
	private JSpinner amt;
	private JTextField price;
	private JLabel priceLabel;
	private JLabel amtLabel;

	@Before
	public void setUp() throws Exception {
		frame2 = new JFrame();
		panel2 = new JPanel();
		amtLabel = new JLabel("Amount");
		panel2.add(amtLabel);
		priceLabel = new JLabel("Price");
		panel2.add(priceLabel);
		amt = new JSpinner(new SpinnerNumberModel(0,0,999,1));
		panel2.add(amt);
		price = new JTextField(6);
		panel2.add(price);
	}

	@Test
	public void showPromptTest() {
		//add interface test
		ShoppingBasket.showPrompt("Add Item", 1, frame2, amt, price, amtLabel, priceLabel);
		assertEquals(amt.isVisible(), true);
		assertEquals(price.isVisible(), true);
		assertEquals(amtLabel.isVisible(), true);
		assertEquals(priceLabel.isVisible(), true);
		
		//delete interface test
		ShoppingBasket.showPrompt("Delete Item", 2, frame2, amt, price, amtLabel, priceLabel);
		assertEquals(amt.isVisible(), false);
		assertEquals(price.isVisible(), false);
		assertEquals(amtLabel.isVisible(), false);
		assertEquals(priceLabel.isVisible(), false);
		
		//edit interface test
		ShoppingBasket.showPrompt("Edit Item", 3, frame2, amt, price, amtLabel, priceLabel);
		assertEquals(amt.isVisible(), true);
		assertEquals(price.isVisible(), true);
		assertEquals(amtLabel.isVisible(), true);
		assertEquals(priceLabel.isVisible(), true);
	}

	@Test
	public void okPressedTest() {
		//add test
		ShoppingBasket.showPrompt("Add Item", 1, frame2, amt, price, amtLabel, priceLabel);
		JTextField n = new JTextField();
		n.setText("a");
		ShoppingBasket.okPressed(n, amt, price, frame2);
		assertEquals(ShoppingBasket.basket.get(0).getName() == "a", true);
		
		//edit test
		ShoppingBasket.showPrompt("Edit Item", 3, frame2, amt, price, amtLabel, priceLabel);
		JSpinner a = new JSpinner();
		a.setValue(2);
		JTextField p = new JTextField();
		p.setText("3");
		ShoppingBasket.okPressed(n, a, p, frame2);
		assertEquals(ShoppingBasket.basket.get(0).getAmt() == 2, true);
		assertEquals(ShoppingBasket.basket.get(0).getPrice() == 3, true);
		
		//delete test
		ShoppingBasket.showPrompt("Delete Item", 2, frame2, amt, price, amtLabel, priceLabel);
		ShoppingBasket.okPressed(n, amt, price, frame2);
		assertTrue(ShoppingBasket.basket.isEmpty());
	}
	
	@Test
	public void saveTest() {
		// save to file test
		File f = new File("basket.txt");
		if(f.exists()) f.delete();
		OrderItem i = new OrderItem();
		i.setName("a");
		i.setAmt(1);
		i.setPrice(1.00);
		ShoppingBasket.basket.add(i);
		ShoppingBasket.save();
		assertTrue(f.exists());
	}
}
