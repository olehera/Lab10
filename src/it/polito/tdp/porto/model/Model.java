package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Graph<Author, Arco> grafo;
	private PortoDAO dao;
	private Map<Integer, Author> idMap;
	private List<Author> autori;
	private Map<Integer, Paper> pidMap;
	
	public Model() {
		dao = new PortoDAO();
		idMap = new HashMap<>();
		pidMap = new HashMap<>();
		autori = dao.getAutori(idMap);
		dao.getArticoli(pidMap);
	}
	
	public void creaGrafo() {
		grafo = new SimpleGraph<>(Arco.class);
		
		Graphs.addAllVertices(grafo, getAuthor());
		
		for (Coautori c : dao.getCoautori()) {
			Arco arco = grafo.addEdge(idMap.get(c.getId1()), idMap.get(c.getId2()));
			
			if (arco != null)
				arco.setPaper(pidMap.get(c.getEprintid()));
		}
		
	}
	
	public Graph<Author, Arco> getGrafo() {
		return grafo;
	}
	
	public Collection<Author> getAuthor() {
		Collections.sort(autori);
		return autori;
	}

	public List<Author> trovaCoautori(Author source) {
		List<Author> result = new ArrayList<>();
		
		for (Author author : getAuthor())
			if (grafo.containsEdge(source, author))
				result.add(author);
		
		return result;
	}
	
	public List<Author> nonCoautori(List<Author> coautori) {
		List<Author> result = new ArrayList<>();
		
		for (Author author : getAuthor())
			if (!coautori.contains(author))
				result.add(author);
			
		return result;
	}
	
	public List<Paper> sequenzaArticoli(Author source, Author target) {
		List<Paper> articoli = new ArrayList<Paper>();
		
		DijkstraShortestPath<Author, Arco> dijkstra = new DijkstraShortestPath<>(grafo);
		
		GraphPath<Author, Arco> path = dijkstra.getPath(source, target);
		
		for (Arco a : path.getEdgeList())
			articoli.add(a.getPaper());

		return articoli;
	}

}