package game.economy.websocket.impl;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import game.economy.GameServer;
import game.economy.GlobalGSON;
import game.economy.websocket.Request;
import game.economy.websocket.RequestType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameSocketHandlerSimple extends WebSocketServer {
	private Map<String, RequestType> requestTypes;

	@Getter
	private GameServer server;

	@Getter
	private Set<WebSocket> connections = new HashSet<>();

	public GameSocketHandlerSimple(GameServer server, Map<String, RequestType> requestTypes, int port, Draft d) {
		super(new InetSocketAddress(port), Collections.singletonList(d));

		this.requestTypes = requestTypes;
		this.server = server;
	}

	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		connections.remove(arg0);

		log.info("Disconnect: {}", arg0.getRemoteSocketAddress());
	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		log.error("WebSocket error: {}", arg1);
	}

	@Override
	public void onMessage(WebSocket arg0, String arg1) {
		try {
			Request r = Request.fromJson(GlobalGSON.instance(), arg1);

			RequestType type = requestTypes.get(r.getType());

			if (type == null) {
				// TODO send back an error message
				log.debug("Invalid request: {}", r);
				return;
			}

			// do what the request should do
			type.runTask(r, arg0, server);
		} catch (JsonSyntaxException e) {
			log.debug("Invalid request from {}: {}", arg0.getRemoteSocketAddress(), arg1);
			// TODO send back an error message
		}

	}

	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		connections.add(arg0);
		log.info("New connection from {}", arg0.getRemoteSocketAddress());
	}

	@Override
	public void onStart() {
		log.info("Websocket handler onStart");
	}

}
