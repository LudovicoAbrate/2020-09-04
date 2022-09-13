package it.polito.tdp.imdb.model;

public class Adiacenza {
	
	Movie m1;
	Movie m2;
	double peso;
	
	public Adiacenza(Movie m1, Movie m2, double peso) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.peso = peso;
	}
	public Movie getM1() {
		return m1;
	}
	public void setM1(Movie m1) {
		this.m1 = m1;
	}
	public Movie getM2() {
		return m2;
	}
	public void setM2(Movie m2) {
		this.m2 = m2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	

}
