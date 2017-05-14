package game.economy.items;

public interface Facility {

	public int getTier();

	public long maxProductionPerDay(Item i);
}
