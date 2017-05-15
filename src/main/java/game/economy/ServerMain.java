package game.economy;

public class ServerMain {

	public static void main(String[] args) {
		GameServer gs = GameServer.builder().build();
		
		gs.start();
	}

}
