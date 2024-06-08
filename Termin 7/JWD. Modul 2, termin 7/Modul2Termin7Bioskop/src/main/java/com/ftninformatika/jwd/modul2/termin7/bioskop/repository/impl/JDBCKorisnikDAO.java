package com.ftninformatika.jwd.modul2.termin7.bioskop.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Korisnik;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.KorisnikDAO;

@Repository
public class JDBCKorisnikDAO implements KorisnikDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCKorisnikDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static class KorisnikRowMapper implements RowMapper<Korisnik> {

		@Override
		public Korisnik mapRow(ResultSet rset, int rowNum) throws SQLException {
			int kolona = 0;
			String korisnickoIme = rset.getString(++kolona);
			String eMail = rset.getString(++kolona);
			String pol = rset.getString(++kolona);
			boolean administrator = rset.getBoolean(++kolona);

			return new Korisnik(korisnickoIme, eMail, pol, administrator);
		}
		
	}

	@Override
	public Korisnik get(String korisnickoIme) {
		String sql = "SELECT korisnickoIme, eMail, pol, administrator FROM korisnici WHERE korisnickoIme = ?";

		List<Korisnik> rezultat = jdbcTemplate.query(sql, new KorisnikRowMapper(), korisnickoIme);
		return !rezultat.isEmpty()? rezultat.get(0): null;
	}

	@Override
	public Korisnik get(String korisnickoIme, String lozinka) {
		String sql = 
				"SELECT korisnickoIme, eMail, pol, administrator FROM korisnici " + 
				"WHERE korisnickoIme = ? AND lozinka = ?";

		List<Korisnik> rezultat = jdbcTemplate.query(sql, new KorisnikRowMapper(), korisnickoIme, lozinka);
		return !rezultat.isEmpty()? rezultat.get(0): null;
	}

	@Override
	public Collection<Korisnik> getAll() {
		String sql = "SELECT korisnickoIme, eMail, pol, administrator FROM korisnici";
		return jdbcTemplate.query(sql, new KorisnikRowMapper());
	}

	@Override
	public Collection<Korisnik> get(String korisnickoIme, String eMail, String pol, boolean administrator) {
		String sql = 
				"SELECT korisnickoIme, eMail, pol, administrator FROM korisnici " + 
				"WHERE (? IS NULL OR korisnickoIme LIKE ?) " + 
				"AND (? IS NULL OR eMail LIKE ?) " + 
				"AND (? IS NULL OR ? = '' OR pol = ?) " + 
				"AND (? = false OR administrator = ?)";
		return jdbcTemplate.query(sql, new KorisnikRowMapper(), 
				korisnickoIme, "%" + korisnickoIme + "%", 
				eMail, "%" + eMail + "%", 
				pol, pol, pol, 
				administrator, administrator);
	}

	@Override
	public void add(Korisnik korisnik) {
		String sql = 
				"INSERT INTO korisnici (korisnickoIme, lozinka, eMail, pol, administrator) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, 
				korisnik.getKorisnickoIme(), 
				korisnik.getLozinka(), 
				korisnik.geteMail(), 
				korisnik.getPol(), 
				korisnik.isAdministrator());
	}

	@Override
	public void update(Korisnik korisnik) {
		String sql = "UPDATE korisnici SET lozinka = IF(? != '', ?, lozinka), eMail = ?, pol = ?, administrator = ? WHERE korisnickoIme = ?";
		jdbcTemplate.update(sql, 
				korisnik.getLozinka(), korisnik.getLozinka(), 
				korisnik.geteMail(), 
				korisnik.getPol(), 
				korisnik.isAdministrator(), 
				korisnik.getKorisnickoIme());
	}

	@Override
	public void delete(String korisnickoIme) {
		String sql = "DELETE FROM korisnici WHERE korisnickoIme = ?";
		jdbcTemplate.update(sql, korisnickoIme);
	}

}
