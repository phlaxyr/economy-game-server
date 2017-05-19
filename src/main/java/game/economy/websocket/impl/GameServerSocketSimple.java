package game.economy.websocket.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.java_websocket.drafts.Draft_17;

import game.economy.GameServer;
import game.economy.websocket.GameServerSocket;
import game.economy.websocket.RequestType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameServerSocketSimple implements GameServerSocket {

	Map<String, RequestType> requestTypes = new HashMap<>();

	GameSocketHandlerSimple handler;
	
	@Override
	public void startServer(GameServer gs, int port) {
		
		log.info("Server socket on port {}", port);
		handler = new GameSocketHandlerSimple(gs, requestTypes, port, new Draft_17());
		handler.start();
	}

	@Override
	public void registerRequest(RequestType r) {
		log.debug("Registered request '{}'", r.getType());
		requestTypes.put(r.getType(), r);
	}

}
