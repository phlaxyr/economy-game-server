package game.economy.websocket.request;

import org.java_websocket.WebSocket;

import game.economy.GameServer;
import game.economy.GlobalGSON;
import game.economy.websocket.Request;
import game.economy.websocket.RequestType;

public class PingRequest implements RequestType {

	@Override
	public String getType() {
		return "ping";
	}

	@Override
	public String[] getData() {
		return new String[] {};
	}

	@Override
	public void runTask(Request req, WebSocket ws, GameServer gs) {
		Request r = Request.builder().type("pong").data("sent", Long.toString(System.currentTimeMillis())).build();
		String response = r.toJson(GlobalGSON.instance());
		ws.send(response);
	}

}
