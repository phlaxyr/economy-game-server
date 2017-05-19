package game.economy.items;

import java.util.Map;

import game.economy.inventory.Inventory;

public interface Item {
	public String getItemName();

	public String getManufacturer();

	public int getItemId();

	public ItemQuality getItemQuality();

	public int getSize();

	public boolean canBeStoredIn(Inventory inv);
	
	public static long getSlots(Map<Item, Long> items) {
		return items.entrySet().stream().mapToLong((entry) -> entry.getKey().getSize() * entry.getValue()).sum();
	}
}
