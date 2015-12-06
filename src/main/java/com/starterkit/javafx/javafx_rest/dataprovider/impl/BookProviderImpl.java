package com.starterkit.javafx.javafx_rest.dataprovider.impl;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starterkit.javafx.javafx_rest.dataprovider.BookProvider;
import com.starterkit.javafx.javafx_rest.helpers.HttpConnectionHelper;
import com.starterkit.javafx.javafx_rest.mapper.BookTOMapper;
import com.starterkit.javafx.javafx_rest.model.to.BookTO;

public class BookProviderImpl implements BookProvider {
	private URL serviceURL;
	private URL booksByTitlePrefixURL;
	private URL bookRest;
	private final String TITLE_PREFIX_PARAM = "titlePrefix";
	private final String BOOK_ID_PARAM = "bookId";

	private final ObjectMapper objectMapper = new ObjectMapper();

	public BookProviderImpl(String serviceURL) throws MalformedURLException {
		this(new URL(serviceURL));
	}

	public BookProviderImpl(URL serviceURL) throws MalformedURLException {
		this.serviceURL = serviceURL;
		String servURL = serviceURL.toString();
		this.booksByTitlePrefixURL = new URL(servURL + "/books-by-title");
		this.bookRest = new URL(servURL + "/book");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookTO> findAllBooks() {
		List<BookTO> books = null;
		try {
			/*
			 * REV: zawsze uzywaj loggera
			 */
			System.out.println("Request to: " + booksByTitlePrefixURL.toString());
			HttpURLConnection connection = HttpConnectionHelper.openConnection(booksByTitlePrefixURL,
					HttpConnectionHelper.RequestMethod.GET,
					HttpConnectionHelper.joinParamValue(TITLE_PREFIX_PARAM, ""));

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				/*
				 * REV: ObjectMapper powinien byc utworzony tylko raz i uzywany wielokrotnie
				 * masz juz ObjectMappera w klasie
				 */
				ObjectMapper mapper = new ObjectMapper();
				books = (List<BookTO>) objectMapper.readValue(connection.getInputStream(),
						mapper.getTypeFactory().constructCollectionType(List.class, BookTO.class));
			} else {
				/*
				 * REV: zawsze uzywaj loggera
				 */
				System.err.println("Invalid response code: " + connection.getResponseCode());
			}
		} catch (Exception ex) {
			/*
			 * REV: j.w.
			 */
			System.err.println("[BookProviderImpl.findAllBooks] Error:" + ex.getMessage());
			/*
			 * REV: wyjatek powinien byc rzucony dalej, tak aby wyswietlic blad na gui
			 */
		}

		return books;
	}

	@Override
	public BookTO saveBook(BookTO bookTO) {
		BookTO tmpBook = null;
		try {
			String bookTOJson = BookTOMapper.toJSON(bookTO);
			HttpURLConnection connection = HttpConnectionHelper.openConnection(bookRest,
					HttpConnectionHelper.RequestMethod.POST, bookTOJson);

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				tmpBook = objectMapper.readValue(connection.getInputStream(), BookTO.class);
			} else {
				/*
				 * REV: j.w.
				 */
				System.err.println("Invalid response code: " + connection.getResponseCode());
			}
		} catch (Exception ex) {
			/*
			 * REV: j.w.
			 */
			System.err.println("[BookProviderImpl.saveBook] Error:" + ex.getMessage());
		}

		return tmpBook;
	}

	@Override
	public boolean updateBook(BookTO bookTO) {
		/*
		 * REV: lepiej rzucic wyjatek niz zwracac flage
		 */
		boolean result = false;
		try {
			String bookTOJson = BookTOMapper.toJSON(bookTO);
			HttpURLConnection connection = HttpConnectionHelper.openConnection(bookRest,
					HttpConnectionHelper.RequestMethod.PUT, bookTOJson);

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				result = true;
			} else {
				/*
				 * REV: j.w.
				 */
				System.err.println("Invalid response code: " + connection.getResponseCode());
				result = false;
			}
		} catch (Exception ex) {
			/*
			 * REV: j.w.
			 */
			System.err.println("[BookProviderImpl.updateBook] Error:" + ex.getMessage());
			result = false;
		}

		return result;
	}

	@Override
	public boolean deleteBook(BookTO bookTO) {
		/*
		 * REV: j.w.
		 */
		boolean result = false;
		try {
			HttpURLConnection connection = HttpConnectionHelper.openConnection(bookRest,
					HttpConnectionHelper.RequestMethod.DELETE,
					HttpConnectionHelper.joinParamValue(BOOK_ID_PARAM, bookTO.getId().toString()));

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				result = true;
			} else {
				/*
				 * REV: j.w.
				 */
				System.err.println("Invalid response code: " + connection.getResponseCode());
				result = false;
			}
		} catch (Exception ex) {
			/*
			 * REV: j.w.
			 */
			System.err.println("[BookProviderImpl.deleteBook] Error:" + ex.getMessage());
			result = false;
		}

		return result;
	}

}
