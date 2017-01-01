package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import florian_haas.lucas.model.validation.ValidItemPrice;
import florian_haas.lucas.util.validation.TypeNotNull;

@Entity
public class Item extends EntityBase {

	private static final long serialVersionUID = -3598214275967142864L;

	@Basic(optional = false)
	@Column(nullable = false, unique = true)
	@NotNull
	private String name;

	@Basic(optional = true)
	@Column(nullable = true)
	private String description;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(0)
	private Integer itemsAvaible = 0;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@ValidItemPrice
	private BigDecimal pricePerItem;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "item")
	@Valid
	@NotNull
	private List<@TypeNotNull PurchaseLog> purchaseLogs = new ArrayList<>();

	Item() {}

	public Item(String name, String description, BigDecimal pricePerItem, Integer itemsAvaible) {
		this.name = name;
		this.description = description;
		this.pricePerItem = pricePerItem;
		this.itemsAvaible = itemsAvaible;
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

	public Integer getItemsAvaible() {
		return this.itemsAvaible;
	}

	public void setItemsAvaible(Integer itemsAvaible) {
		this.itemsAvaible = itemsAvaible;
	}

	public BigDecimal getPricePerItem() {
		return this.pricePerItem;
	}

	public void setPricePerItem(BigDecimal pricePerItem) {
		this.pricePerItem = pricePerItem;
	}

	public Boolean getHasToBeOrdered() {
		return this.itemsAvaible == 0;
	}

	public List<PurchaseLog> getPurchaseLogs() {
		return purchaseLogs;
	}

	public boolean addPurchaseLog(PurchaseLog purchaseLog) {
		return this.purchaseLogs.add(purchaseLog);
	}
}
