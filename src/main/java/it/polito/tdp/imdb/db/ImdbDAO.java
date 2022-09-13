package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		
		
		
		
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Movie> getVertici(){
		
		String sql = "select distinct * "
				+ "from movies "
				+ "where movies.rank <> \"NULL\" ";
	
	
	List<Movie> result = new ArrayList<Movie>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
					res.getInt("year"), res.getDouble("rank"));
			
			result.add(movie);
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	
}
	
	
}
	
	public List<Adiacenza> getAdiacenze(double rank,Map<Integer,Movie> idMap) {
		
		String sql = "select distinct m1.id as mov1, m2.id as mov2, count(distinct r1.actor_id) as peso "
				+ "from movies m1 , movies m2, roles r1, roles r2 "
				+ "where m1.id < m2.id "
				+ "and m1.rank >=  ? "
				+ "and m2.rank >=  ? "
				+ "and m1.id = r1.movie_id "
				+ "and m2.id = r2.movie_id "
				+ "and r1.actor_id = r2.actor_id "
				+ "group by mov1,mov2 ";
		
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setFloat(1,(float) rank);
			st.setFloat(2, (float) rank);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie m1 = idMap.get(res.getInt("mov1"));
				Movie m2 = idMap.get(res.getInt("mov2"));
				Adiacenza a = new Adiacenza(m1,m2,res.getDouble("peso"));
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		
		}
	}
	
	
	
	
}
