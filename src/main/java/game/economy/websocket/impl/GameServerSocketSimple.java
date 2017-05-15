package game.economy.websocket.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.java_websocket.drafts.Draft_17;

import game.economy.GameServer;
import game.economy.websocket.GameServerSocket;
import game.economy.websocket.RequestType;

public class GameServerSocketSimple implements GameServerSocket {

	Map<String, RequestType> requestTypes = new HashMap<>();

	GameSocketHandlerSimple handler;
	
	@Override
	public void startServer(GameServer gs, int port) {
		handler = new GameSocketHandlerSimple(gs, requestTypes, port, new Draft_17());
	}

	@Override
	public void registerRequest(RequestType r) {
		requestTypes.put(r.getType(), r);
	}

}
