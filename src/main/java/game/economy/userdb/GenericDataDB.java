package game.economy.userdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import game.economy.items.Item;

public abstract class GenericDataDB implements DataDB {

	protected Map<String, PreparedStatement> statements = new HashMap<>();

	protected Connection connection;
	protected Statement stmt;

	public GenericDataDB() throws SQLException {
		connection = makeConn();
		stmt = connection.createStatement();
	}

	protected void initDB() throws SQLException {

	}

	protected void prepareStatements() throws SQLException {

	}

	protected PreparedStatement sql(String name) {
		return statements.get(name);
	}

	protected void prepareStatement(String name, String sql) throws SQLException {
		statements.put(name, connection.prepareStatement(sql));
	}

	protected abstract Connection makeConn() throws SQLException;

	public String getTablePrefix() {
		return "ECON_";
	}

	@Override
	public boolean registerUser(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
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
