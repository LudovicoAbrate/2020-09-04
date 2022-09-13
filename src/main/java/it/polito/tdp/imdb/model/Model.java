package it.polito.tdp.imdb.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	
	private Map<Integer,Movie> idMap;
	private Graph<Movie, DefaultWeightedEdge> grafo;
	private ImdbDAO dao;
	List<Director> vertici;
	
	public Model() {
		this.dao = new ImdbDAO();
		this.idMap= new HashMap<>();
		this.vertici= new LinkedList<>();
		
	}
	
	public void creaGrafo(double rank) {
	grafo = new SimpleWeightedGraph<Movie,DefaultWeightedEdge>(DefaultWeightedEdge.class);

	 idMap.clear();
	for( Movie m : dao.getVertici()) {
		
		
	idMap.put(m.id, m);
	}
	
	
	 Graphs.addAllVertices(this.grafo,dao.getVertici());
	 
	for(Adiacenza a : dao.getAdiacenze(rank, idMap)) {
		
		Graphs.addEdgeWithVertices(this.grafo, a.m1, a.m2, a.peso);
	}
	
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
		
	}
	public int nArchi() {
		return grafo.edgeSet().size();
		
	
	}
	
	public String filmGradoMax() {
		
		int massimo = 0;
		String s = "";
		
		for(Movie m : this.grafo.vertexSet()) {
			
			double peso = this.calcolaPeso(m);
			
			if(peso > massimo) {
				massimo = (int) peso;
				
				s = m.id + " - " + m.name + " - (" + massimo+ ")";
			}
			
		}
		
		return s;
		
	}
	
	public double calcolaPeso(Movie m) {
 		double peso=0;
 			List<Movie> vicini= Graphs.neighborListOf(this.grafo, m);
 			for(Movie m1: vicini) {
 			peso+=grafo.getEdgeWeight(this.grafo.getEdge(m, m1));

 		}
 		return peso;
 	}


}
