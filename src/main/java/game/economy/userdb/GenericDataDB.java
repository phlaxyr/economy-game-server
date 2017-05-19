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

	public GenericDataDB() throws SQLException {
		connection = makeConn();
		stmt = connection.createStatement();
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

	protected abstract Connection makeConn() throws SQLException;

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
			
			put.setLong(1, System.currentTimeMillis());

			put.executeUpdate();
		} catch (PasswordException | SQLException e) {
			log.warn("An error happened that shouldn't happen: {}", e);
			return false;
		}

		return true;
	}

	@Override
	public boolean login(String username, String password) {
		try {
			PreparedStatement get = sql("getUserPassHash");
			
			get.setString(1, username);
			
			ResultSet rs = get.executeQuery();
			
			// username does not exist
			if(!rs.next())
				return false;
			
			String passHash = rs.getString("passhash");
			
			// this should be impossible normally
			if(passHash == null)
				throw new NullPointerException("passHash for user " + username + " was null!");
			
			Password pw = PasswordFactory.create();
			boolean verified = pw.verify(password, passHash);
			
			// wrong password
			if(!verified) {
				return false;
			}
			
		} catch (PasswordException | SQLException e) {
			log.warn("An error happened that shouldn't happen: {}", e);
			return false;
		}
		
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
