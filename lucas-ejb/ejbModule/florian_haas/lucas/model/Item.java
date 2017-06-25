package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.validation.*;

@Entity
@ValidItem
public class Item extends EntityBase implements ReadOnlyItem {

	private static final long serialVersionUID = -3598214275967142864L;

	@Basic(optional = false)
	@Column(nullable = false, unique = true)
	@NotBlank
	private String name;

	@Basic(optional = true)
	@Column(nullable = true)
	@NotBlankString
	private String description;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(0)
	private Integer itemsAvailable = 0;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@ValidItemPrice
	private BigDecimal fictionalPricePerItem;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@ValidItemPrice
	private BigDecimal realPricePerItem;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "item")
	@Valid
	@NotNull
	private List<@TypeNotNull PurchaseLog> purchaseLogs = new ArrayList<>();

	Item() {}

	public Item(String name, String description, BigDecimal fictionalPricePerItem, BigDecimal realPricePerItem, Integer itemsAvaible) {
		this.name = name;
		this.description = description;
		this.realPricePerItem = realPricePerItem;
		this.fictionalPricePerItem = fictionalPricePerItem;
		this.itemsAvailable = itemsAvaible;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getItemsAvailable() {
		return this.itemsAvailable;
	}

	public void setItemsAvailable(Integer itemsAvaible) {
		this.itemsAvailable = itemsAvaible;
	}

	public BigDecimal getRealPricePerItem() {
		return this.realPricePerItem;
	}

	public void setRealPricePerItem(BigDecimal realPricePerItem) {
		this.realPricePerItem = realPricePerItem;
	}

	public BigDecimal getFictionalPricePerItem() {
		return this.fictionalPricePerItem;
	}

	public void setFictionalPricePerItem(BigDecimal fictionalPricePerItem) {
		this.fictionalPricePerItem = fictionalPricePerItem;
	}

	public Boolean getHasToBeOrdered() {
		return this.itemsAvailable.equals(0);
	}

	public List<PurchaseLog> getPurchaseLogs() {
		return Collections.unmodifiableList(purchaseLogs);
	}

	public Boolean addPurchaseLog(PurchaseLog purchaseLog) {
		return this.purchaseLogs.add(purchaseLog);
	}

}
