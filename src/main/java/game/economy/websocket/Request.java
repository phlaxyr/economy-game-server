package game.economy.websocket;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class Request {
	String type;
	
	@Singular("data")
	Map<String, String> data;

	public static Request fromJson(Gson gson, String request) throws JsonSyntaxException {
		return gson.fromJson(request, Request.class);
	}

	public String toJson(Gson gson) {
		return gson.toJson(this);
	}
}
