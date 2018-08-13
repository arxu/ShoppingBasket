package basket;

//item class
public class OrderItem {
	private String name = "foobar";
	private double price = 0;
	private int amt = 0;
	
	//receive name
	public String getName() {
		return name;
	}

	//set name
	public void setName(String name) {
		this.name = name;
	}

	//receive price
	public double getPrice() {
		return price;
	}

	//set price
	public void setPrice(double price) {
		this.price = price;
	}

	//receive amount
	public int getAmt() {
		return amt;
	}
	
	//set amount
	public void setAmt(int amt) {
		this.amt = amt;
	}
}
