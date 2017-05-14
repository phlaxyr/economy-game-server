package game.economy.websocket;

import java.util.Map;

import com.google.gson.Gson;

import lombok.Data;

@Data
public class Request {
	String type;
	Map<String, String> data;

	public static Request fromJson(Gson gson, String request) {
		return gson.fromJson(request, Request.class);
	}

	public String toJson(Gson gson) {
		return gson.toJson(this);
	}
}
