package com.starterkit.javafx.javafx_rest;

import java.util.Arrays;
import java.util.List;

import com.starterkit.javafx.javafx_rest.dataprovider.BookProvider;
import com.starterkit.javafx.javafx_rest.dataprovider.impl.BookProviderImpl;
import com.starterkit.javafx.javafx_rest.model.to.AuthorTO;
import com.starterkit.javafx.javafx_rest.model.to.BookTO;

/**
 * Hello world!
 *
 */
public class App {
	private static BookProvider provider;

	public static void main(String[] args) throws Exception {
		provider = new BookProviderImpl("http://localhost:9721/workshop/services/books");

		BookTO anotherBook = new BookTO(null, "Another book", Arrays.asList(new AuthorTO(null, "Johnny", "Brasco")),
				null);

		provider.saveBook(anotherBook);

		List<BookTO> books = listBooks();

		provider.deleteBook(books.get(2));

		listBooks();
	}

	private static List<BookTO> listBooks() {
		List<BookTO> books = provider.findAllBooks();

		for (BookTO book : books) {
			System.out.println(book);
			for (AuthorTO author : book.getAuthors()) {
				System.out.println("\t" + author);
			}
			System.out.println("\t" + book.getLibrary());
		}

		return books;
	}
}
