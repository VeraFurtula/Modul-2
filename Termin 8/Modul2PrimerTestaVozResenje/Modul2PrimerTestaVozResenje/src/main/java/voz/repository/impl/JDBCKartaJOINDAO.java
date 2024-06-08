package voz.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import voz.model.Karta;
import voz.model.Voz;
import voz.repository.KartaDAO;

@Primary
@Repository
public class JDBCKartaJOINDAO implements KartaDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCKartaJOINDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private class KartaRowMapper implements RowMapper<Karta> {

		@Override
		public Karta mapRow(ResultSet rset, int rowNum) throws SQLException {
			int kolona = 0;
			long kId = rset.getLong(++kolona);
			LocalDateTime kDatumIVremeProdaje = rset.getTimestamp(++kolona).toLocalDateTime();
			String kKupac = rset.getString(++kolona);
			int kRazred = rset.getInt(++kolona);
			long vId = rset.getLong(++kolona);
			String vBroj = rset.getString(++kolona);
			String vRang = rset.getString(++kolona);
			LocalDateTime vDatumIVremePolaska = rset.getTimestamp(++kolona).toLocalDateTime();
			double vCenaKarte = rset.getDouble(++kolona);
			int vBrojMesta = rset.getInt(++kolona);

			Voz voz = new Voz(vId, vBroj, vRang, vDatumIVremePolaska, vCenaKarte, vBrojMesta);		
			Karta karta = new Karta(kId, kDatumIVremeProdaje, kKupac, kRazred, voz);
			return karta;
		}

	}

	@Override
	public Karta get(long id) {
		String sql = 
				"SELECT k.id, k.datumIVremeProdaje, k.kupac, k.razred, " + 
				"v.id, v.broj, v.rang, v.datumIVremePolaska, v.cenaKarte, v.brojMesta FROM karte k " + 
				"LEFT JOIN vozovi v ON k.vozId = v.id " + 
				"WHERE k.id = ?";
		List<Karta> rezultat = jdbcTemplate.query(sql, new KartaRowMapper(), id);
		return !rezultat.isEmpty()? rezultat.get(0): null;
	}

	@Override
	public Collection<Karta> getAll() {
		String sql = 
				"SELECT k.id, k.datumIVremeProdaje, k.kupac, k.razred, " + 
				"v.id, v.broj, v.rang, v.datumIVremePolaska, v.cenaKarte, v.brojMesta FROM karte k " + 
				"LEFT JOIN vozovi v ON k.vozId = v.id";
		return jdbcTemplate.query(sql, new KartaRowMapper());
	}

	@Override
	public Collection<Karta> get(
			String vozBroj, 
			String vozRang, 
			LocalDateTime vozDatumIVremePolaskaOd, LocalDateTime vozDatumIVremePolaskaDo, 
			double vozCenaKarteOd, double vozCenaKarteDo, 
			String kupac, 
			int razredOd, int razredDo) {
		String sql = 
				"SELECT k.id, k.datumIVremeProdaje, k.kupac, k.razred, " + 
				"v.id, v.broj, v.rang, v.datumIVremePolaska, v.cenaKarte, v.brojMesta FROM karte k " + 
				"LEFT JOIN vozovi v ON k.vozId = v.id " + 
				"WHERE (? IS NULL OR v.broj LIKE ?) " + 
				"AND (? IS NULL OR ? = '' OR v.rang = ?) " + 
				"AND (? IS NULL OR v.datumIVremePolaska >= ?) " + 
				"AND (? IS NULL OR v.datumIVremePolaska <= ?) " + 
				"AND (? <= 0 OR v.cenaKarte >= ?) " + 
				"AND (? <= 0 OR v.cenaKarte <= ?) " + 
				"AND (? IS NULL OR k.kupac LIKE ?) " + 
				"AND (? <= 0 OR k.razred >= ?) " + 
				"AND (? <= 0 OR k.razred <= ?) ";
		return jdbcTemplate.query(sql, new KartaRowMapper(), 
				vozBroj, "%" + vozBroj + "%", 
				vozRang, vozRang, vozRang, 
				vozDatumIVremePolaskaOd, vozDatumIVremePolaskaOd, 
				vozDatumIVremePolaskaDo, vozDatumIVremePolaskaDo, 
				vozCenaKarteOd, vozCenaKarteOd, 
				vozCenaKarteDo, vozCenaKarteDo, 
				kupac, "%" + kupac + "%", 
				razredOd, razredOd, 
				razredDo, razredDo);
	}

	@Override
	public void add(Karta karta) {
		String sql = "INSERT INTO karte (datumIVremeProdaje, kupac, razred, vozId) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, karta.getDatumIVremeProdaje(), karta.getKupac(), karta.getRazred(), karta.getVoz().getId());
	}

	@Override
	public void update(Karta karta) {
		String sql = "UPDATE karte SET datumIVremeProdaje = ?, kupac = ?, razred = ?, vozId = ? WHERE id = ?";
		jdbcTemplate.update(sql, karta.getDatumIVremeProdaje(), karta.getKupac(), karta.getRazred(), karta.getVoz().getId(), karta.getId());
	}

}
