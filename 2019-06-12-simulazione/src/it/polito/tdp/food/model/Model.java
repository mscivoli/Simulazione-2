package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao dao = new FoodDao();
	
	private List<Condiment> condimenti;
	private Graph<Condiment, DefaultWeightedEdge> grafo;
	private Map<Integer, Condiment> mapId;
	private List<Collegamento> collegamenti;
	private List<Condiment> best;

	private int calorie;
	
	
	
	public void creaGrafo(int calorie) {
		this.mapId = new HashMap<Integer, Condiment>();
		this.condimenti = dao.listAllCondiment();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		for(Condiment c : condimenti) {
			mapId.put(c.getFood_code(), c);
		}
		
		this.collegamenti = dao.listaCollegamenti(mapId);
		
		for(Collegamento c : this.collegamenti) {
			if(!c.getC1().equals(c.getC2()) && !this.grafo.containsEdge(this.grafo.getEdge(c.getC1(), c.getC2()))) {
				if(c.getC1().getCondiment_calories() < calorie && c.getC2().getCondiment_calories() < calorie)
					Graphs.addEdgeWithVertices(this.grafo, c.getC1(), c.getC2(), c.getCibiDiversi());
			}
		}
		
		System.out.println("GRAFO CREATO");
		System.out.println("Vertici: "+this.grafo.vertexSet().size());
		System.out.println("Archi: "+this.grafo.edgeSet().size());
		
		List<Ingrediente> ingredienti = new LinkedList<Ingrediente>();
		
		for(Condiment c : this.grafo.vertexSet()) {
			int peso = 0;
			Set<DefaultWeightedEdge> stemp = this.grafo.incomingEdgesOf(c);
			
			for(DefaultWeightedEdge e : stemp) {
				peso+=this.grafo.getEdgeWeight(e);
			}
			
			ingredienti.add(new Ingrediente(c, peso));
		}
		
		Collections.sort(ingredienti);
		
		for(Ingrediente i : ingredienti) {
			System.out.println(i.toString());
		}
		
	}
	
	
	public List<Condiment> insiemeMigliore(int vertice) {
		Condiment verticeClasse = mapId.get(vertice);
		this.calorie = 0;
		List<Condiment> parziale = new LinkedList<Condiment>();
		Set<Condiment> nonAmmissibili = new HashSet<Condiment>();
		this.best = new LinkedList<Condiment>();
		parziale.add(verticeClasse);
		
		cercaInsieme(parziale, nonAmmissibili, verticeClasse);
		
		return this.best;
		
	}
	
	public void cercaInsieme(List<Condiment> parziale, Set<Condiment> nonAmmissibili, Condiment vertice) {
		
		System.out.println(parziale.toString());
		int peso = esisteMaggiore(parziale);
		if(peso>this.calorie) {
			this.best = new LinkedList<Condiment>(parziale);
			this.calorie=peso;
		}
		
		List<Condiment> vicini = Graphs.neighborListOf(this.grafo, vertice);
		
		for(Condiment c : vicini) {
			nonAmmissibili.add(c);
		}
		
		
		for(Condiment ctemp : this.grafo.vertexSet()) {
			if(!parziale.contains(ctemp) && !nonAmmissibili.contains(ctemp)) {
				parziale.add(ctemp);
				cercaInsieme(parziale, nonAmmissibili, ctemp);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
		
	}


	public int esisteMaggiore(List<Condiment> parziale) {
		int peso = 0;
		for(Condiment c : parziale) {
			peso+=c.getCondiment_calories();
		}
		
		return peso;
	}
	

}
