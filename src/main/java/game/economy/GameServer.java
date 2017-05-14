package game.economy;

import java.util.Collection;

import game.economy.items.Item;
import game.economy.recipe.Recipe;
import game.economy.userdb.DataDB;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Builder
public class GameServer {
	public static final transient int DEFAULT_PORT = 1942;
	
	@Singular
	@Getter
	private Collection<Recipe> recipes;
	
	@Singular
	@Getter
	private Collection<Item> items;
	
	@Getter
	private DataDB database;
	
	public void start() {
		
	}
}
