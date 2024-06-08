package com.ftninformatika.jwd.modul2.termin7.bioskop.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.FilmDAO;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.ProjekcijaDAO;

//@Primary
@Repository
public class JDBCProjekcijaDAO implements ProjekcijaDAO {

	private final JdbcTemplate jdbcTemplate;
	private final FilmDAO filmDAO;

	public JDBCProjekcijaDAO(JdbcTemplate jdbcTemplate, FilmDAO filmDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.filmDAO = filmDAO;
	}

	private class ProjekcijaRowMapper implements RowMapper<Projekcija> {

		@Override
		public Projekcija mapRow(ResultSet rset, int rowNum) throws SQLException {
			int kolona = 0;
			long id = rset.getLong(++kolona);
			LocalDateTime datumIVreme = rset.getTimestamp(++kolona).toLocalDateTime();
			long filmId = rset.getLong(++kolona);
			String tip = rset.getString(++kolona);
			int sala = rset.getInt(++kolona);
			double cenaKarte = rset.getDouble(++kolona);

			Projekcija projekcija = new Projekcija(id, datumIVreme, tip, sala, cenaKarte);
			if (filmId != 0) {
				Film film = filmDAO.get(filmId);
				projekcija.setFilm(film);
			}
			return projekcija;
		}

	}

	@Override
	public Projekcija get(long id) {
		String sql = 
				"SELECT id, datumIVreme, filmId, tip, sala, cenaKarte FROM projekcije " + 
				"WHERE id = ?";

		List<Projekcija> rezultat = jdbcTemplate.query(sql, new ProjekcijaRowMapper(), id);
		return (!rezultat.isEmpty())? rezultat.get(0): null;
	}

	@Override
	public Collection<Projekcija> getAll() {
		String sql = "SELECT id, datumIVreme, filmId, tip, sala, cenaKarte FROM projekcije";
		return jdbcTemplate.query(sql, new ProjekcijaRowMapper());
	}

	@Override
	public Collection<Projekcija> get(
			LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, 
			long filmId, 
			String tip,
			int sala, 
			double cenaKarteOd, double cenaKarteDo) {
		Timestamp timestampOd = (datumIVremeOd != null)? Timestamp.valueOf(datumIVremeOd): null;
		Timestamp timestampDo = (datumIVremeDo != null)? Timestamp.valueOf(datumIVremeDo): null;
		String sql = 
				"SELECT id, datumIVreme, filmId, tip, sala, cenaKarte FROM projekcije " + 
				"WHERE (? IS NULL OR datumIVreme >= ?) " + 
				"AND (? IS NULL OR datumIVreme <= ?) " + 
				"AND (? <= 0 OR filmId = ?) " + 
				"AND (? IS NULL OR ? = '' OR tip = ?) " + 
				"AND (? <= 0 OR ? > 3 OR sala = ?) " + 
				"AND (? <= 0 OR cenaKarte >= ?) " + 
				"AND (? <= 0 OR cenaKarte <= ?)";
		return jdbcTemplate.query(sql, new ProjekcijaRowMapper(), 
				timestampOd, timestampOd, 
				timestampDo, timestampDo, 
				filmId, filmId, 
				tip, tip, tip,  
				sala, sala, sala, 
				cenaKarteOd, cenaKarteOd,  
				cenaKarteDo, cenaKarteDo);
	}

	@Override
	public void add(Projekcija projekcija) {
		String sql = "INSERT INTO projekcije (datumIVreme, filmId, tip, sala, cenaKarte) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, 
				projekcija.getDatumIVreme(), 
				projekcija.getFilm().getId(), 
				projekcija.getTip(), 
				projekcija.getSala(), 
				projekcija.getCenaKarte());
	}

	@Override
	public void update(Projekcija projekcija) {
		String sql = "UPDATE projekcije SET datumIVreme = ?, filmId = ?, tip = ?, sala = ?, cenaKarte = ? WHERE id = ?";
		jdbcTemplate.update(sql, 
				projekcija.getDatumIVreme(), 
				projekcija.getFilm().getId(), 
				projekcija.getTip(), 
				projekcija.getSala(), 
				projekcija.getCenaKarte(), 
				projekcija.getId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM projekcije WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

}
