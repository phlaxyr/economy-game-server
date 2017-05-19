package game.economy.userdb;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SQLiteDataDB extends GenericDataDB {

	// register jdbc driver
	static {
		try {
			Class.forName("org.sqlite.JDBC").newInstance();

			log.info("Registered SQLite JDBC driver");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.error("Something went wrong registering the SQLite jdbc driver! \n {}", e);
		}
	}

	public SQLiteDataDB() throws SQLException {
		this(null);
		log.info("Using in-memory database");
	}

	public SQLiteDataDB(File file) throws SQLException {
		// null file means in-memory
		super("jdbc:sqlite:" + ((file == null) ? ":memory:" : file.toString()), (file != null && !file.exists()));

		log.info("Using database at {}", file);
		
		commit();
	}

	@Override
	protected Connection makeConn(String conn) throws SQLException {
		log.info("Making DB connection");
		return DriverManager.getConnection(conn);
	}

}
