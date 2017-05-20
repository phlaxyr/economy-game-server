package game.economy.userdb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class TestSQLiteDB {
	@Test
	public void testUserbase() throws Exception {
		File testfile = new File("testusr.db");
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
	public void testBalance() throws Exception {
		File testfile = new File("testbal.db");
		testfile.delete();

		DataDB db = new SQLiteDataDB(testfile);

		assertTrue(db.registerUser("user", "password"));
		
		assertThat(db.getBalance("user"), is(equalTo(0L)));
		
		assertTrue(db.changeBalance("user", 100L));

		assertThat(db.getBalance("user"), is(equalTo(100L)));
		
		assertFalse(db.changeBalance("user", -101L));
		
		assertThat(db.getBalance("user"), is(equalTo(100L)));
		
		assertTrue(db.changeBalance("user", -100L));
		
		assertThat(db.getBalance("user"), is(equalTo(0L)));

	}
}
