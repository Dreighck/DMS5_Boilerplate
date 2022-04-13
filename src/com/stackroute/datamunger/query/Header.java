package com.stackroute.datamunger.query;

import java.util.HashMap;

//Header class containing a Collection containing the headers
public class Header extends HashMap<String, Integer> {
	private static final long serialVersionUID = 1L;
	private HashMap<String,Integer> header;

	public Header(HashMap<String,Integer> header) {
		this.header=header;
	}

	public HashMap<String, Integer> getHeader() {
		return header;
	}

	public void setHeader(HashMap<String, Integer> header) {
		this.header = header;
	}

	@Override
	public String toString() {
		return "Header{" +
				"header=" + header +
				'}';
	}
}
