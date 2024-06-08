package voz.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import voz.model.Voz;
import voz.repository.VozDAO;

@Repository
public class JDBCVozDAO implements VozDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCVozDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static class VozRowMapper implements RowMapper<Voz> {

		@Override
		public Voz mapRow(ResultSet rset, int rowNum) throws SQLException {
			int kolona = 0;
			long id = rset.getLong(++kolona);
			String broj = rset.getString(++kolona);
			String rang = rset.getString(++kolona);
			LocalDateTime datumIVremePolaska = rset.getTimestamp(++kolona).toLocalDateTime();
			double cenaKarte = rset.getDouble(++kolona);
			int brojMesta = rset.getInt(++kolona);

			Voz voz = new Voz(id, broj, rang, datumIVremePolaska, cenaKarte, brojMesta);
			return voz;
		}

	}

	@Override
	public Voz get(long id) {
		String sql = "SELECT id, broj, rang, datumIVremePolaska, cenaKarte, brojMesta FROM vozovi WHERE id = ?";
		
		List<Voz> rezultat = jdbcTemplate.query(sql, new VozRowMapper(), id);
		return !rezultat.isEmpty()? rezultat.get(0): null;
	}

	@Override
	public Collection<Voz> getAll() {
		String sql = "SELECT id, broj, rang, datumIVremePolaska, cenaKarte, brojMesta FROM vozovi";
		return jdbcTemplate.query(sql, new VozRowMapper());
	}

}
