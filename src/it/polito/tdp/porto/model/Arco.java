package it.polito.tdp.porto.model;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class Arco extends DefaultEdge {
	
	private Paper paper;
	
	public Arco() {
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

}
