package basket;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OrderItemTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetAndSetName() {
		OrderItem item = new OrderItem();
		item.setName("1");
		assertEquals(item.getName() == "1", true);
		item.setName("%");
		assertEquals(item.getName() == "%", true);
		item.setName("a");
		assertEquals(item.getName() == "a", true);
	}

	@Test
	public void testGetAndSetPrice() {
		OrderItem item = new OrderItem();
		item.setPrice(0.01);
		assertEquals(item.getPrice() == 0.01, true);
		item.setPrice(999.99);
		assertEquals(item.getPrice() == 999.99, true);
		item.setPrice(0.01);
		assertEquals(item.getPrice() == 999.99, false);
	}

	@Test
	public void testGetAndSetAmt() {
		OrderItem item = new OrderItem();
		item.setAmt(1);
		assertEquals(item.getAmt() == 1, true);
		item.setAmt(999);
		assertEquals(item.getAmt() == 999, true);
		item.setAmt(999);
		assertEquals(item.getAmt() == 1, false);
	}
}
