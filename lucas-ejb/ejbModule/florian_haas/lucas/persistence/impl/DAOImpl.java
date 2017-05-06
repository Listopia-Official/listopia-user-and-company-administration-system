package florian_haas.lucas.persistence.impl;

import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import org.glassfish.api.logging.LogLevel;

import florian_haas.lucas.model.EntityBase;
import florian_haas.lucas.persistence.DAO;
import florian_haas.lucas.util.Utils;

public abstract class DAOImpl<E extends EntityBase> extends ReadOnlyDAOImpl<E> implements DAO<E> {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

	@Override
	public void persist(E entity) {
		try {
			manager.persist(entity);
		}
		catch (Throwable t) {
			LOGGER.log(LogLevel.SEVERE, "Could not persist entity: ", t);
			Set<ConstraintViolation<?>> violations = Utils.extractConstraintViolations(t);
			if (violations != null) {
				LOGGER.severe("Found " + violations.size() + " constraint violations:");
				violations.forEach(violation -> {
					LOGGER.severe(violation.toString());
				});
			}
			throw t;
		}
	}

	@Override
	public E merge(E entity) {
		return manager.merge(entity);
	}

	@Override
	public void delete(E entity) {
		manager.remove(entity);
	}

}
