package com.cgi.ecm.reports.rest_service;

public class Dimensions {
	private float height;
	private float width;
	
	public Dimensions(float height, float width) {
		super();
		this.height = height;
		this.width = width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getWidth() {
		return width;
	}
	
	@SuppressWarnings("unused")
	private Dimensions(){}
}
