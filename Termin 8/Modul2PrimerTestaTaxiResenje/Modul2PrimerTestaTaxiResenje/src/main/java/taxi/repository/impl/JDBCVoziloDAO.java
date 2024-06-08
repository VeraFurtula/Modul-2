package taxi.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import taxi.model.Vozilo;
import taxi.repository.VoziloDAO;

@Repository
public class JDBCVoziloDAO implements VoziloDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCVoziloDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static class VoziloRowMapper implements RowMapper<Vozilo> {

		@Override
		public Vozilo mapRow(ResultSet rset, int rowNum) throws SQLException {
			int kolona = 0;
			long id = rset.getLong(++kolona);
			String broj = rset.getString(++kolona);
			String vozac = rset.getString(++kolona);

			Vozilo vozilo = new Vozilo(id, broj, vozac);
			return vozilo;
		}

	}

	@Override
	public Vozilo get(long id) {
		String sql = "SELECT id, broj, vozac FROM vozila WHERE id = ?";
		
		List<Vozilo> rezultat = jdbcTemplate.query(sql, new VoziloRowMapper(), id);
		return !rezultat.isEmpty()? rezultat.get(0): null;
	}

	@Override
	public Collection<Vozilo> getAll() {
		String sql = "SELECT id, broj, vozac FROM vozila";
		return jdbcTemplate.query(sql, new VoziloRowMapper());
	}

}
