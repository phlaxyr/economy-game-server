package game.economy.websocket;

import game.economy.GameServer;

public interface RequestType {
	public String getType();

	public String[] getData();
	
	public void runTask(GameServer gs);
}
