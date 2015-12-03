package com.starterkit.javafx.javafx_rest.model.to;

public class LibraryTO {
	private Long id;
	private String name;

	protected LibraryTO() {
	}

	public LibraryTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("[%d] %s", id, name);
	}

}
