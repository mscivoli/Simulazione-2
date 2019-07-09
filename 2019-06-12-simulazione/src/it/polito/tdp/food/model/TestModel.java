package it.polito.tdp.food.model;

import java.util.Map;

import it.polito.tdp.food.db.Condiment;

public class TestModel {
	public static void main(String[] args) {
		Model m = new Model();
		
		m.creaGrafo(37);
		
		for(Condiment c : m.insiemeMigliore(91406500)) {
			System.out.println(c.toString());
		}
	}
}
