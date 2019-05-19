package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Coautori;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {
	
	/*
	 * Ottengo la lista di tutti gli Autori
	 */
	public List<Author> getAutori(Map<Integer, Author> idMap) {

		final String sql = "SELECT * FROM author";
		List<Author> autori = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author a = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				if (!idMap.containsKey(a.getId())) {
					idMap.put(a.getId(), a);
					autori.add(a);
				} else
				autori.add(a);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Errore metodo getAutori()");
		}
		return autori;
	}
	
	/*
	 * Ottengo la lista di tutte le relazioni tra gli Autori
	 */
	public List<Coautori> getCoautori() {

		final String sql = "SELECT c1.authorid as id1, c2.authorid as id2, c1.eprintid " +
		                   "FROM creator c1, creator c2 " +
				           "WHERE c1.eprintid = c2.eprintid AND c1.authorid > c2.authorid";
		List<Coautori> coautori = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				coautori.add(new Coautori(rs.getInt("id1"), rs.getInt("id2"), rs.getInt("eprintid")));
			}

		} catch (SQLException e) {
			throw new RuntimeException("Errore metodo getCoautori()");
		}
		return coautori;
	}

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}