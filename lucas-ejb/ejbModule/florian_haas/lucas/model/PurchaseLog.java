package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;

import florian_haas.lucas.validation.ValidItemPrice;

@Entity
public class PurchaseLog extends EntityBase implements ReadOnlyPurchaseLog {

	private static final long serialVersionUID = -3291832482461768966L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	@NotNull
	private Company company;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	@NotNull
	private Item item;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private LocalDateTime dateTime;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private EnumPayType payType;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(1)
	private Integer count;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@ValidItemPrice
	private BigDecimal currentFictionalPrice;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@ValidItemPrice
	private BigDecimal currentRealPrice;

	PurchaseLog() {}

	public PurchaseLog(Company company, LocalDateTime dateTime, Item item, EnumPayType payType, Integer count, BigDecimal currentFictionalPrice,
			BigDecimal currentRealPrice) {
		this.company = company;
		this.dateTime = dateTime;
		this.item = item;
		this.payType = payType;
		this.count = count;
		this.currentFictionalPrice = currentFictionalPrice;
		this.currentRealPrice = currentRealPrice;
	}

	public Company getCompany() {
		return this.company;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public ReadOnlyItem getItem() {
		return this.item;
	}

	public EnumPayType getPayType() {
		return payType;
	}

	public Integer getCount() {
		return this.count;
	}

	@Override
	public BigDecimal getCurrentFictionalPrice() {
		return currentFictionalPrice;
	}

	@Override
	public BigDecimal getCurrentRealPrice() {
		return currentRealPrice;
	}

	public BigDecimal getTotalFictionalPrice() {
		return currentFictionalPrice.multiply(new BigDecimal(count));
	}

	public BigDecimal getTotalRealPrice() {
		return currentRealPrice.multiply(new BigDecimal(count));
	}
}
