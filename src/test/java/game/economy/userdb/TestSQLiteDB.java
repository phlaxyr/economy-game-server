package game.economy.userdb;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class TestSQLiteDB {
	@Test
	public void testUserbase() throws Exception {
		File testfile = new File("test.db");
		testfile.delete();
		
		DataDB db = new SQLiteDataDB(testfile);

		boolean success = db.registerUser("username", "password");
		boolean duplicate = db.registerUser("username", "password");
		
		assertFalse(duplicate);
		assertTrue(success);
		
		db.commit();

		boolean login = db.login("username", "password");
		boolean wrongpw = db.login("username", "password2");
		
		assertTrue(login);
		assertFalse(wrongpw);
	}
	
	
	@Test
	public void testBalance() {
		
	}
}
