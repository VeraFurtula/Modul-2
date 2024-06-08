package com.ftninformatika.jwd.modul2.termin7.bioskop.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.FilmDAO;

@Repository
public class JDBCFilmJOINDAO implements FilmDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCFilmJOINDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static class FilmRowCallbackHandler implements RowCallbackHandler {

		private final Map<Long, Film> filmovi = new LinkedHashMap<>();

		@Override
		public void processRow(ResultSet rset) throws SQLException {
			int kolona = 0;
			long fId = rset.getLong(++kolona);
			String fNaziv = rset.getString(++kolona);
			int fTrajanje = rset.getInt(++kolona);
			
			Film film = filmovi.get(fId);
			if (film == null) {
				film = new Film(fId, fNaziv, fTrajanje);
				filmovi.put(fId, film);
			}
			long zId = rset.getLong(++kolona);
			if (zId != 0) {
				String zNaziv = rset.getString(++kolona);

				Zanr zanr = new Zanr(zId, zNaziv);
				film.addZanr(zanr);
			}		
		}

		public List<Film> getFilmovi() { return new ArrayList<>(filmovi.values()); }

	}

	@Override
	public Film get(long id) {
		FilmRowCallbackHandler rowCallbackHandler = new FilmRowCallbackHandler();

		String sql = 
				"SELECT f.id, f.naziv, f.trajanje, z.id, z.naziv FROM filmovi f " + 
				"LEFT JOIN filmZanr fz ON f.id = fz.filmId " + 
				"LEFT JOIN zanrovi z ON z.id = fz.zanrId " + 
				"WHERE f.id = ?";
		jdbcTemplate.query(sql, rowCallbackHandler, id);
		
		List<Film> filmovi = rowCallbackHandler.getFilmovi();
		return !filmovi.isEmpty()? filmovi.get(0): null;
	}

	@Override
	public Collection<Film> getAll() {
		FilmRowCallbackHandler rowCallbackHandler = new FilmRowCallbackHandler();
		
		String sql = 
				"SELECT f.id, f.naziv, f.trajanje, z.id, z.naziv FROM filmovi f " + 
				"LEFT JOIN filmZanr fz ON f.id = fz.filmId " + 
				"LEFT JOIN zanrovi z ON z.id = fz.zanrId " + 
				"ORDER BY f.id";
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getFilmovi();
	}

	@Override
	public Collection<Film> get(String naziv, long zanrId, int trajanjeOd, int trajanjeDo) {
		FilmRowCallbackHandler rowCallbackHandler = new FilmRowCallbackHandler();
		
		String sql = 
				"SELECT r1.fId, r1.fNaziv, r1.fTrajanje, r1.zId, r1.zNaziv FROM (" + 
				"	SELECT f.id AS fId, f.naziv AS fNaziv, f.trajanje AS fTrajanje, z.id AS zId, z.naziv AS zNaziv FROM filmovi f " + 
				"	LEFT JOIN filmZanr fz ON f.id = fz.filmId " + 
				"	LEFT JOIN zanrovi z ON z.id = fz.zanrId " + 
				"	WHERE (? IS NULL OR f.naziv LIKE ?) " + 
				"	AND (? <= 0 OR f.trajanje >= ?) " + 
				"	AND (? <= 0 OR f.trajanje <= ?) " + 
				"	ORDER BY f.id" + 
				") AS r1 JOIN (" + 
				"	SELECT f.id AS fId FROM filmovi f " + 
				"	LEFT JOIN filmZanr fz ON f.id = fz.filmId " + 
				"	WHERE ? <= 0 OR fz.zanrId = ?" + 
				") AS r2 ON r1.fId = r2.fId";
		jdbcTemplate.query(sql, rowCallbackHandler, 
				naziv, "%" + naziv + "%", 
				trajanjeOd, trajanjeOd, 
				trajanjeDo, trajanjeDo, 
				zanrId, zanrId);

		return rowCallbackHandler.getFilmovi();
	}

	@Override
	@Transactional
	public void add(Film film) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "INSERT INTO filmovi (naziv, trajanje) VALUES (?, ?)";

				PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // traži se generisani ključ za slog u bazi
				int param = 0;
				preparedStatement.setString(++param, film.getNaziv());
				preparedStatement.setInt(++param, film.getTrajanje());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		if (uspeh) {
			String sql = "INSERT INTO filmZanr (filmId, zanrId) VALUES (?, ?)";
			for (Zanr itZanr: film.getZanrovi()) {	
				jdbcTemplate.update(sql, keyHolder.getKey(), itZanr.getId());
			}
		}
	}

	@Override
	@Transactional
	public void update(Film film) {
		String sql = "DELETE FROM filmZanr WHERE filmId = ?";
		jdbcTemplate.update(sql, film.getId());
	
		sql = "INSERT INTO filmZanr (filmId, zanrId) VALUES (?, ?)";
		for (Zanr itZanr: film.getZanrovi()) {
			jdbcTemplate.update(sql, film.getId(), itZanr.getId());
		}
		sql = "UPDATE filmovi SET naziv = ?, trajanje = ? WHERE id = ?";	
		jdbcTemplate.update(sql, film.getNaziv(), film.getTrajanje(), film.getId());
	}

	@Override
	@Transactional
	public void delete(long id) {
		String sql = "DELETE FROM filmZanr WHERE filmId = ?";
		jdbcTemplate.update(sql, id);

		sql = "DELETE FROM filmovi WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

}
