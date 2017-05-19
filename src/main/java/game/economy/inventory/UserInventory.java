package game.economy.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import game.economy.items.Item;

public class UserInventory implements Inventory {

	public static final int USER_INVENTORY_SLOTS = 100;

	private Map<Item, Long> items = Collections.synchronizedMap(new HashMap<>());

	@Override
	public long getSlots() {
		return USER_INVENTORY_SLOTS;
	}

	@Override
	public Map<Item, Long> getItems() {
		return items;
	}

	@Override
	public boolean putItems(Map<Item, Long> toput) {
		long size = Item.getSlots(toput);

		if (!this.canFit(size))
			return false;

		toput.forEach((item, count) -> {
			items.put(item, count);
		});
		
		return true;
	}

}
