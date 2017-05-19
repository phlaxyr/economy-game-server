package game.economy.websocket.request;

import java.util.Map;

import org.java_websocket.WebSocket;

import game.economy.GameServer;
import game.economy.websocket.Request;
import game.economy.websocket.RequestType;

public class RegisterRequest implements RequestType {

	@Override
	public String getType() {
		return "register";
	}

	@Override
	public String[] getData() {
		return new String[] {"username", "password"};
	}

	@Override
	public void runTask(Request req, WebSocket ws, GameServer gs) {
		Map<String, String> reqdata = req.getData();
		String user = reqdata.get("username");
		String pass = reqdata.get("password");
		
		if(user == null || pass == null) {
			// TODO: send back error message
			return;
		}
		
		boolean success = gs.getDatabase().registerUser(user, pass);
		
		if(!success) {
			// TODO: send back error message
			return;
		}
		
		// TODO: send back success message
	}

}
