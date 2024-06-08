package voz.repository;

import java.util.Collection;

import voz.model.Voz;

public interface VozDAO {

	public Voz get(long id);
	public Collection<Voz> getAll();

}
