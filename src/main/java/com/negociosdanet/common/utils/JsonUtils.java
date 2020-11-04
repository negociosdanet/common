package com.negociosdanet.common.utils;

import java.io.IOException;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	static ObjectMapper objectMapper = new ObjectMapper();
	static ModelMapper modelMapper = new ModelMapper();

	private JsonUtils() {
		super();
	}

	public static <T> T convertValue(Object source, Class<T> response) {
		return modelMapper.map(source, response);
	}

	public static String convertToJson(Object obj) {
		String json = null;
		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	public static <T> T convertJsonToObj(String json, Class<T> objResponse) {
		T response = null;
		try {
			response = objectMapper.readValue(json, objResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

}
