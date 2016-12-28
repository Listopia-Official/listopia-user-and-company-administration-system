package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class PurchaseLog extends EntityBase {

	private static final long serialVersionUID = -3291832482461768966L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Company company;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Item item;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDateTime dateTime;

	@Basic(optional = false)
	@Column(nullable = false)
	private EnumPayType payType;

	@Basic(optional = false)
	@Column(nullable = false)
	private Integer count;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	private BigDecimal currentPrice;

	PurchaseLog() {}

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
