package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseLog extends EntityBase {

	private static final long serialVersionUID = -3291832482461768966L;

	private Company company;
	private LocalDateTime dateTime;
	private Item item;
	private EnumPayType payType;
	private Integer count;
	private BigDecimal currentPrice;

	public PurchaseLog(Company company, LocalDateTime dateTime, Item item, EnumPayType payType, Integer count,
			BigDecimal currentPrice) {
		this.company = company;
		this.dateTime = dateTime;
		this.item = item;
		this.payType = payType;
		this.count = count;
		this.currentPrice = currentPrice;
	}

	public Company getCompany() {
		return this.company;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public Item getItem() {
		return this.item;
	}

	public EnumPayType getPayType() {
		return payType;
	}

	public Integer getCount() {
		return this.count;
	}

	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}

	public BigDecimal getTotalPrice() {
		return currentPrice.multiply(new BigDecimal(count));
	}
}
