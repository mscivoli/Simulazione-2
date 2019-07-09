package it.polito.tdp.food.model;

import it.polito.tdp.food.db.Condiment;

public class Ingrediente implements Comparable<Ingrediente>{

	private Condiment codimenti;
	private int numeroCibi;
	public Ingrediente(Condiment codimenti, int numeroCibi) {
		super();
		this.codimenti = codimenti;
		this.numeroCibi = numeroCibi;
	}
	public Condiment getCodimenti() {
		return codimenti;
	}
	public void setCodimenti(Condiment codimenti) {
		this.codimenti = codimenti;
	}
	public int getNumeroCibi() {
		return numeroCibi;
	}
	public void setNumeroCibi(int numeroCibi) {
		this.numeroCibi = numeroCibi;
	}
	@Override
	public int compareTo(Ingrediente o) {
		// TODO Auto-generated method stub
		return this.codimenti.getCondiment_calories().compareTo(o.getCodimenti().getCondiment_calories());
	}
	@Override
	public String toString() {
		return "Ingrediente [codimenti=" + codimenti + ", numeroCibi=" + numeroCibi + "]";
	}
	
	
}
