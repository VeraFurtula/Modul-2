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

@Primary
@Repository
public class JDBCPozivJOINDAO implements PozivDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCPozivJOINDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private class PozivRowMapper implements RowMapper<Poziv> {

		@Override
		public Poziv mapRow(ResultSet rset, int rowNum) throws SQLException {
			int kolona = 0;
			long pId = rset.getLong(++kolona);
			LocalDateTime pDatumIVreme = rset.getTimestamp(++kolona).toLocalDateTime();
			String pUlica = rset.getString(++kolona);
			int pBroj = rset.getInt(++kolona);
			long vId = rset.getLong(++kolona);
			String vBroj = rset.getString(++kolona);
			String vVozac = rset.getString(++kolona);

			Vozilo vozilo = new Vozilo(vId, vBroj, vVozac);		
			Poziv poziv = new Poziv(pId, pDatumIVreme, pUlica, pBroj, vozilo);
			return poziv;
		}
		
	}

	@Override
	public Collection<Poziv> getAll() {
		String sql = 
				"SELECT p.id, p.datumIVreme, p.ulica, p.broj, v.id, v.broj, v.vozac FROM pozivi p " + 
				"LEFT JOIN vozila v ON p.voziloId = v.id";
		return jdbcTemplate.query(sql, new PozivRowMapper());
	}

	@Override
	public Collection<Poziv> get(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, String ulica, int brojOd,
			int brojDo, long voziloId) {
		String sql = 
				"SELECT p.id, p.datumIVreme, p.ulica, p.broj, v.id, v.broj, v.vozac FROM pozivi p " + 
				"LEFT JOIN vozila v ON p.voziloId = v.id " + 
				"WHERE (? IS NULL OR p.datumIVreme >= ?) " + 
				"AND (? IS NULL OR p.datumIVreme <= ?) " + 
				"AND (? IS NULL OR p.ulica LIKE ?) " + 
				"AND (? <= 0 OR p.broj >= ?) " + 
				"AND (? <= 0 OR p.broj <= ?) " + 
				"AND (? <= 0 OR p.voziloId = ?)"; 
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
