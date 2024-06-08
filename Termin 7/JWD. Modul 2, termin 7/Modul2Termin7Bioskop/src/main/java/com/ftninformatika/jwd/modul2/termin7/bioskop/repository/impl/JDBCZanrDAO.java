package com.ftninformatika.jwd.modul2.termin7.bioskop.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.ZanrDAO;

@Repository
public class JDBCZanrDAO implements ZanrDAO {

	private final JdbcTemplate jdbcTemplate;
	
	public JDBCZanrDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static class ZanrRowMapper implements RowMapper<Zanr> {

		@Override
		public Zanr mapRow(ResultSet rset, int rowNum) throws SQLException {
			int kolona = 0;
			long id = rset.getLong(++kolona);
			String naziv = rset.getString(++kolona);
			
			Zanr zanr = new Zanr(id, naziv);
			return zanr;
		}
		
	}

	@Override
	public Zanr get(long id) {
		String sql = "SELECT id, naziv FROM zanrovi WHERE id = ?";
		
		List<Zanr> rezultat = jdbcTemplate.query(sql, new ZanrRowMapper(), id);
		return !rezultat.isEmpty()? rezultat.get(0): null;
	}

	@Override
	public Collection<Zanr> getAll() {
		String sql = "SELECT id, naziv FROM zanrovi";
		return jdbcTemplate.query(sql, new ZanrRowMapper());
	}

	@Override
	public Collection<Zanr> get(String naziv) {
		String sql = "SELECT id, naziv FROM zanrovi WHERE ? IS NULL OR naziv LIKE ?";
		return jdbcTemplate.query(sql, new ZanrRowMapper(), 
				naziv, "%" + naziv + "%");
	}
	
	@Override
	public void add(Zanr zanr) {
		String sql = "INSERT INTO zanrovi (naziv) VALUES (?)";
		jdbcTemplate.update(sql, zanr.getNaziv());
	}

	@Override
	public void update(Zanr zanr) {
		String sql = "UPDATE zanrovi SET naziv = ? WHERE id = ?";
		jdbcTemplate.update(sql, zanr.getNaziv(), zanr.getId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM zanrovi WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

}
