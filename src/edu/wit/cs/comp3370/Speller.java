package edu.wit.cs.comp3370;

public abstract class Speller {
	
	public abstract void insertWord(String s);
	public abstract boolean contains(String s);
	public abstract String[] getSugg(String s);
	
}
