/**
 * 
 */
package org.qburst.search.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Cyril
 * 
 */
public class Search {
	private List<String> highlights = new LinkedList<String>();
	private String author = "";
	private String id = "";

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<String> getHighlights() {
		return highlights;
	}

	public void setHighlights(List<String> highlights) {
		this.highlights = highlights;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
