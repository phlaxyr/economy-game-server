package game.economy.recipe;

import java.util.Map;

import game.economy.items.Facility;
import game.economy.items.Item;

public interface Recipe {
	public Map<Item, Integer> getInput();

	public Map<Item, Integer> getOutput();

	public Map<Facility[], Long> getTimeRequired();

}
