package game.economy.websocket.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.economy.websocket.GameServerSocket;
import game.economy.websocket.RequestType;

public class GameServerSocketSimple implements GameServerSocket {

	Map<String, RequestType> requestTypes = new HashMap<>();

	@Override
	public void startServer(int port) {

	}

	@Override
	public void registerRequest(RequestType r) {
		requestTypes.put(r.getType(), r);
	}

}
