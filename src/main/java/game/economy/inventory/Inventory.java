package game.economy.inventory;

import java.util.Map;

import game.economy.items.Item;

public interface Inventory {

	public long getSlots();

	public Map<Item, Long> getItems();

	public boolean putItems(Map<Item, Long> toput);

	default public long getSlotsFilled() {
		return Item.getSlots(getItems());
	}

	default public boolean canFit(long numOfItems) {
		return getSlots() - getSlotsFilled() >= numOfItems;
	}
}
