package game.economy.userdb;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class TestGenericDB {

	@Test
	public void testGetSQL() throws Throwable {
		String sql = GenericDataDB.getSQL("mkUserTable.sql");
		
		assertThat(sql.length(), is(not(equalTo(0))));
	}

}
