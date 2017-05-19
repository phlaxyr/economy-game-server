package game.economy.userdb;

import java.util.Collection;
import java.util.Map;

import game.economy.items.Item;

public interface DataDB {
	// login details
	public boolean registerUser(String username, String password);

	public boolean login(String username, String password);

	// inventory
	public Map<Item, Integer> getInventory(String username);

	public boolean hasItems(Map<Item, Integer> items);

	public boolean addItem(String user, Item item, long count);

	public boolean removeItem(String user, Item item, long count);

	default public boolean transferItem(String from, String to, Item item, long count) {
		if (removeItem(from, item, count))
			if (addItem(to, item, count))
				return true;
			else if (addItem(from, item, count))
				throw new RuntimeException("Removed item (" + count + "* " + item + ") from " + from + " to send to "
						+ to + " but could not send or recover item to " + from);

		return false;
	}

	public long itemCount(Item item);

	// money
	public long getBalance(String user);

	public boolean changeBalance(String user, long delta);

	default public boolean transferMoney(String from, String to, long amount) {
		if (changeBalance(from, -amount))
			if (changeBalance(to, amount))
				return true;
			else if (changeBalance(from, amount))
				throw new RuntimeException("Removed balance (" + amount + ") from " + from + " to send to " + to
						+ " but could not send or recover item to " + from);

		return false;
	}
	
	// db stuff
	public void commit();
}
