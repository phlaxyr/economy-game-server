package game.economy.websocket;

import game.economy.userdb.DataDB;
import lombok.Getter;
import lombok.Setter;

public interface GameServerSocket {
	
	public abstract void startServer(int port);
	
	public abstract void registerRequest(RequestType r);
	
	
}
