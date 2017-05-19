package game.economy.websocket;

import org.java_websocket.WebSocket;

import game.economy.GameServer;

public interface RequestType {
	public String getType();

	public String[] getData();
	
	public void runTask(Request req, WebSocket ws, GameServer gs);
}
