package com.gorugoru.api.jackson;

public class Views {
	static interface Public {}
	public static interface LESS extends Public {}
	public static interface DEF extends LESS {}
	public static interface MORE extends DEF {}
}