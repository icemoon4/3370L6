package edu.wit.cs.comp3370;

import java.util.ArrayList;

/* Implements a trie data structure 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 6 solution
 * 
 */

public class Trie extends Speller {

	private static int FIXES = 2; //the number of letters off a word can be to be suggested
	protected class TNode {
		TNode[] alphabet;
		
		public TNode(){	
			alphabet = new TNode[26];
		}
		
		boolean isDone;
	}
	
	private TNode root;
	public Trie(){
		root = new TNode();
	}
	
	@Override
	public void insertWord(String s) {
		TNode w = root; //set start of word = root so it starts there
		//create for loop to extract char
		for(int i = 0; i<s.length(); i++){
			char curr = s.charAt(i);
			if(w.alphabet[curr-'a'] == null){
				TNode t = new TNode(); //create node for index location
				w.alphabet[curr-'a'] = t;
				w = t; //change current node
			}
			else
				w = w.alphabet[curr-'a']; //if there's already a branch, switch to that
		}
		//maybe add new node which will be isDone
		w.isDone = true;
	}

	@Override
	public boolean contains(String s) {
		TNode w = root;
		for(int i = 0; i < s.length(); i++){
			char curr = s.charAt(i);
			if(w.alphabet[curr-'a'] != null)
				w = w.alphabet[curr-'a']; //switch to next node
			else
				return false; //if the char is not found, then contains is false
		}
		if(w.isDone)
			return true; //if the loop ends at a leaf, word exists
		else
			return false; 
	}

	@Override
	public String[] getSugg(String s) {
		TNode w = root;
		ArrayList<String> sugg = new ArrayList<String>();
		int t_fix = FIXES;
		genSugg(s, "", t_fix, sugg, w, 0);
		String[] edits = sugg.toArray(new String[sugg.size()]);
		return edits;
	}
	
	public void genSugg(String s, String buildWord, int fix, ArrayList<String> sugg, TNode c, int wordPos){
		if((!c.isDone || s.length() != buildWord.length()) && wordPos < s.length()){ 
			char key = s.charAt(wordPos); //current character
			//traverse alphabet until first non-null
			for(int i = 0; i<26; i++){
				if(c.alphabet[i] != null){
					char temp = (char) ('a'+i);
					buildWord += temp;
					//System.out.println("buildword after add: " + buildWord);
					if(temp == key)
						genSugg(s, buildWord, fix, sugg, c.alphabet[i], wordPos+1);
					else if(fix != 0)
						genSugg(s, buildWord, fix-1, sugg, c.alphabet[i], wordPos+1);
					//remove char from buildword
					buildWord = buildWord.substring(0,buildWord.length()-1);
					//System.out.println("buildword after delete: " + buildWord);
				}
			}
		}
		else if(c.isDone){
			sugg.add(buildWord);
		}
	}

}
