package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Graph<Author, DefaultEdge> grafo;
	private PortoDAO dao;
	private Map<Integer, Author> idMap;
	
	public Model() {
		dao = new PortoDAO();
		idMap = new HashMap<>();
		dao.getAutori(idMap);
	}
	
	public void creaGrafo() {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafo, getAuthor());
		
		for (Coautori c : dao.getCoautori())
			grafo.addEdge(idMap.get(c.getId1()), idMap.get(c.getId2()));
		
	}
	
	public Graph<Author, DefaultEdge> getGrafo() {
		return grafo;
	}
	
	public Collection<Author> getAuthor() {
		return idMap.values();
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

}
