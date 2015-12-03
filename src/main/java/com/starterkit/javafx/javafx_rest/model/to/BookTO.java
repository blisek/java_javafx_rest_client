package com.starterkit.javafx.javafx_rest.model.to;

import java.util.List;

public class BookTO {
	private Long id;
	private String title;
	private List<AuthorTO> authors;
	private LibraryTO library;

	protected BookTO() {
	}

	public BookTO(Long id, String title, List<AuthorTO> authors, LibraryTO library) {
		super();
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.library = library;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<AuthorTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorTO> authors) {
		this.authors = authors;
	}

	public LibraryTO getLibrary() {
		return library;
	}

	public void setLibrary(LibraryTO library) {
		this.library = library;
	}

	@Override
	public String toString() {
		return String.format("[%d] Book: %s", id, title);
	}
}
