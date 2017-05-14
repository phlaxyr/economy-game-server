package game.economy.websocket;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

public class TestRequest {
	@Test
	public void testDeser() {
		String json = "{\"type\": \"typeString\", \"data\": {\"keyA\": \"valueA\", \"keyB\": \"valueB\"}}";

		Request r = Request.fromJson(new Gson(), json);

		assertEquals("typeString", r.getType());
		assertEquals(2, r.getData().size());
		assertEquals("valueA", r.getData().get("keyA"));
		assertEquals("valueB", r.getData().get("keyB"));
	}
}
