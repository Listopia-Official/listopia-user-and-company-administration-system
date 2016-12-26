package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseLog extends EntityBase {

	private static final long serialVersionUID = -3291832482461768966L;

	private Company company;
	private LocalDateTime dateTime;
	private Item item;
	private Integer count;
	private BigDecimal currentPrice;

	public PurchaseLog(Company company, LocalDateTime dateTime, Item item, Integer count, BigDecimal currentPrice) {
		this.company = company;
		this.dateTime = dateTime;
		this.item = item;
		this.count = count;
		this.currentPrice = currentPrice;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getTotalPrice() {
		return currentPrice.multiply(new BigDecimal(count));
	}

}
