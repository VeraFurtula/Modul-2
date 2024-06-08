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
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.ProjekcijaDAO;

@Primary
@Repository
public class JDBCProjekcijaJOINDAO implements ProjekcijaDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCProjekcijaJOINDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static class ProjekcijaRowMapper implements RowMapper<Projekcija> {

		@Override
		public Projekcija mapRow(ResultSet rset, int rowNum) throws SQLException {
			int kolona = 0;
			long pId = rset.getLong(++kolona);
			LocalDateTime pDatumIVreme = rset.getTimestamp(++kolona).toLocalDateTime();
			String pTip = rset.getString(++kolona);
			int pSala = rset.getInt(++kolona);
			double pCenaKarte = rset.getDouble(++kolona);

			Projekcija projekcija = new Projekcija(pId, pDatumIVreme, pTip, pSala, pCenaKarte);
			
			long fId = rset.getLong(++kolona);
			if (fId != 0) {
				String fNaziv = rset.getString(++kolona);
				int fTrajanje = rset.getInt(++kolona);

				Film film = new Film(fId, fNaziv, fTrajanje);
				projekcija.setFilm(film);
			}
			return projekcija;
		}
		
	}

	@Override
	public Projekcija get(long id) {
		String sql = 
				"SELECT p.id, p.datumIVreme, p.tip, p.sala, p.cenaKarte, f.id, f.naziv, f.trajanje FROM projekcije p " + 
				"LEFT JOIN filmovi f ON p.filmId = f.id " + 
				"WHERE p.id = ?";

		List<Projekcija> rezultat = jdbcTemplate.query(sql, new ProjekcijaRowMapper(), id);
		return (!rezultat.isEmpty())? rezultat.get(0): null;
	}

	@Override
	public Collection<Projekcija> getAll() {
		String sql = 
				"SELECT p.id, p.datumIVreme, p.tip, p.sala, p.cenaKarte, f.id, f.naziv, f.trajanje FROM projekcije p " + 
				"LEFT JOIN filmovi f ON p.filmId = f.id";
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
				"SELECT p.id, p.datumIVreme, p.tip, p.sala, p.cenaKarte, f.id, f.naziv, f.trajanje FROM projekcije p " + 
				"LEFT JOIN filmovi f ON p.filmId = f.id " + 
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
