package game.economy.userdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySQLDataDB extends GenericDataDB {

	// register jdbc driver
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			log.info("Registered MySQL JDBC driver");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.error("Something went wrong registering the MySQL jdbc driver! \n {}", e);
		}
	}

	public MySQLDataDB(String dbip, String db, String dbuser, String dbpass) throws SQLException {
		// TODO: init db if neccesary
		super("jdbc:mysql://" + dbip + "/" + db + "?user=" + dbuser + "&password=" + dbpass, false);
		log.info("Using database {} on ip {}", db, dbip);
		
		commit();
	}

	@Override
	protected Connection makeConn(String conn) throws SQLException {
		log.info("Making DB connection");
		return DriverManager.getConnection(conn);
	}

}
