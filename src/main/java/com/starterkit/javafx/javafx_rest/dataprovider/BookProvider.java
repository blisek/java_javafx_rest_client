package com.starterkit.javafx.javafx_rest.dataprovider;

import java.util.List;

import com.starterkit.javafx.javafx_rest.model.to.BookTO;

public interface BookProvider {

	List<BookTO> findAllBooks();

	BookTO saveBook(BookTO bookTO);

	boolean updateBook(BookTO bookTO);

	boolean deleteBook(BookTO bookTO);
}
