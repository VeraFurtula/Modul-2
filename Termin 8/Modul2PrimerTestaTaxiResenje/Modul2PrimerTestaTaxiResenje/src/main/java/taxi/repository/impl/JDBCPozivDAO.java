package taxi.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import taxi.model.Poziv;
import taxi.model.Vozilo;
import taxi.repository.PozivDAO;
import taxi.repository.VoziloDAO;

//@Primary
@Repository
public class JDBCPozivDAO implements PozivDAO {

	private final JdbcTemplate jdbcTemplate;
	private final VoziloDAO voziloDAO;

	public JDBCPozivDAO(JdbcTemplate jdbcTemplate, VoziloDAO voziloDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.voziloDAO = voziloDAO;
	}

	private class PozivRowMapper implements RowMapper<Poziv> {

		@Override
		public Poziv mapRow(ResultSet rset, int rowNum) throws SQLException {
			int kolona = 0;
			long id = rset.getLong(++kolona);
			LocalDateTime datumIVreme = rset.getTimestamp(++kolona).toLocalDateTime();
			String ulica = rset.getString(++kolona);
			int broj = rset.getInt(++kolona);
			long voziloId = rset.getLong(++kolona);

			Vozilo vozilo = voziloDAO.get(voziloId);		
			Poziv poziv = new Poziv(id, datumIVreme, ulica, broj, vozilo);
			return poziv;
		}
		
	}

	@Override
	public Collection<Poziv> getAll() {
		String sql = "SELECT id, datumIVreme, ulica, broj, voziloId FROM pozivi";
		return jdbcTemplate.query(sql, new PozivRowMapper());
	}

	@Override
	public Collection<Poziv> get(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, String ulica, int brojOd,
			int brojDo, long voziloId) {
		String sql = 
				"SELECT id, datumIVreme, ulica, broj, voziloId FROM pozivi " + 
				"WHERE (? IS NULL OR datumIVreme >= ?) " + 
				"AND (? IS NULL OR datumIVreme <= ?) " + 
				"AND (? IS NULL OR ulica LIKE ?) " + 
				"AND (? <= 0 OR broj >= ?) " + 
				"AND (? <= 0 OR broj <= ?) " + 
				"AND (? <= 0 OR voziloId = ?)"; 
		return jdbcTemplate.query(sql, new PozivRowMapper(), 
				datumIVremeOd, datumIVremeOd, 
				datumIVremeDo, datumIVremeDo, 
				ulica, "%" + ulica + "%", 
				brojOd, brojOd, 
				brojDo, brojDo, 
				voziloId, voziloId);
	}

	@Override
	public void add(Poziv poziv) {
		String sql = "INSERT INTO pozivi (datumIVreme, ulica, broj, voziloId) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, poziv.getDatumIVreme(), poziv.getUlica(), poziv.getBroj(), poziv.getVozilo().getId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM pozivi WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

}
