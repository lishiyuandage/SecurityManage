package com.bigbrotherlee.dto;

import java.io.Serializable;

public class FileInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5187865113596130774L;
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
