package com.starterkit.javafx.javafx_rest.mapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starterkit.javafx.javafx_rest.model.to.BookTO;

public class BookTOMapper {

	public static BookTO fromJSON(InputStream json) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, BookTO.class);
	}

	public static OutputStream toJSON(OutputStream os, BookTO bookTO)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (os == null) {
			os = new ByteArrayOutputStream(256);
		}
		new ObjectMapper().writeValue(os, bookTO);
		return os;
	}

	public static String toJSON(BookTO bookTO) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(bookTO);
	}
}
