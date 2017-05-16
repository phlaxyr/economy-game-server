package game.economy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GlobalGSON {
	private GlobalGSON() {}
	
	private static final Gson gson = new GsonBuilder().create();
	
	public static final Gson instance() {
		return gson;
	}
}
