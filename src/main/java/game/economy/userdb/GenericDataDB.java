package game.economy.userdb;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import de.qaware.heimdall.Password;
import de.qaware.heimdall.PasswordException;
import de.qaware.heimdall.PasswordFactory;
import de.qaware.heimdall.SecureCharArray;
import game.economy.items.Item;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GenericDataDB implements DataDB {

	protected Map<String, PreparedStatement> statements = new HashMap<>();

	protected Connection connection;
	protected Statement stmt;

	public GenericDataDB(String conn, boolean init) throws SQLException {
		connection = makeConn(conn);
		stmt = connection.createStatement();

		if (init) {
			log.info("Making new database");

			initDB();
		}

		prepareStatements();

		// disable auto commit
		connection.setAutoCommit(false);
	}

	protected void initDB() throws SQLException {
		log.debug("Initializing new database");
		stmt.execute(getSQL("mkUserTable.sql"));
	}

	protected void prepareStatements() throws SQLException {
		prepareStatement("putUser", getSQL("putUser.sql"));
		prepareStatement("getUserPassHash", getSQL("getUserPassHash.sql"));
	}

	protected PreparedStatement sql(String name) {
		return statements.get(name);
	}

	protected void prepareStatement(String name, String sql) throws SQLException {
		statements.put(name, connection.prepareStatement(sql));
	}

	@Override
	public void commit() {
		try {
			log.info("Committing to database...");
			connection.commit();
			log.info("Commit done!");
		} catch (SQLException e) {
			log.error("Something went wrong committing database! ", e);
		}
	}

	public static String getSQL(String file) {
		file = "/game/sql/" + file;

		try {
			URL url = GenericDataDB.class.getResource(file);
			return IOUtils.toString(url, Charset.forName("UTF-8"));
		} catch (Exception e) {
			log.error("Error reading SQL file {}: {}", file, e);
			throw new RuntimeException();
		}
	}

	protected abstract Connection makeConn(String conn) throws SQLException;

	@Override
	public boolean registerUser(String username, String password) {
		try {
			PreparedStatement put = sql("putUser");
			put.setString(1, username);

			Password pw = PasswordFactory.create();

			try (SecureCharArray cleartext = new SecureCharArray(password.toCharArray())) {
				String hash = pw.hash(cleartext);

				put.setString(2, hash);
			}

			put.setLong(3, System.currentTimeMillis());

			put.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == 19)
				log.warn("Tried to register duplicate user {}!", username);
			else
				log.warn("An error happened during user registration [{}]: ", e.getErrorCode(), e);
			return false;
		} catch (PasswordException e) {
			log.warn("An error happened during user registration: ", e);
			return false;
		}
		log.info("Successfully registered new user `{}`", username);
		return true;
	}

	@Override
	public boolean login(String username, String password) {
		try {
			PreparedStatement get = sql("getUserPassHash");

			get.setString(1, username);

			ResultSet rs = get.executeQuery();

			// username does not exist
			if (!rs.next()) {
				log.info("User {} tried to login, but does not exist! ", username);
				return false;
			}

			String passHash = rs.getString("passhash");

			// this should be impossible normally
			if (passHash == null)
				throw new NullPointerException("passHash for user " + username + " was null!");

			Password pw = PasswordFactory.create();
			boolean verified = pw.verify(password, passHash);

			// wrong password
			if (!verified) {
				return false;
			}

		} catch (SQLException e) {
			log.warn("An error happened during login [{}]", e.getErrorCode(), e);
			return false;
		} catch (PasswordException e) {
			log.warn("An error happened during login: ", e);
			return false;
		}

		log.info("User {} logged in successfully", username);
		return true;
	}

	@Override
	public Map<Item, Integer> getInventory(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasItems(Map<Item, Integer> items) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addItem(String user, Item item, long count) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeItem(String user, Item item, long count) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long itemCount(Item item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getBalance(String user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean changeBalance(String user, long delta) {
		// TODO Auto-generated method stub
		return false;
	}

}
