package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

@Entity
public class Item extends EntityBase {

	private static final long serialVersionUID = -3598214275967142864L;

	@Basic(optional = false)
	@Column(nullable = false, unique = true)
	private String name;

	@Basic(optional = true)
	@Column(nullable = true)
	private String description;

	@Basic(optional = false)
	@Column(nullable = false)
	private Integer itemsAvaible = 0;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	private BigDecimal pricePerItem;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "item")
	private List<PurchaseLog> purchaseLogs = new ArrayList<>();

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
