package ru.ras.nbsurgu.telegram.database.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.ras.nbsurgu.telegram.database.dao.GenericDAOImpl;
import ru.ras.nbsurgu.telegram.database.entity.StateEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class StateService {

    private static volatile StateService instance;

    private static volatile GenericDAOImpl<StateEntity, Long> stateDao = new GenericDAOImpl<>(StateEntity.class);

    public static StateService getInstance() {
        if (instance == null) {
            synchronized (StateService.class) {
                if (instance == null) {
                    instance = new StateService();
                }
            }
        }

        return instance;
    }

    public void update(StateEntity entity) {
        stateDao.openCurrentSessionWithTransaction();
        stateDao.update(entity);
        stateDao.closeCurrentSessionWithTransaction();
    }

    public Optional<StateEntity> read(long tgId) {
        final Session session = stateDao.openCurrentSessionWithTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<StateEntity> criteriaQuery = criteriaBuilder.createQuery(StateEntity.class);

        final Root<StateEntity> root = criteriaQuery.from(StateEntity.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("tgId"), tgId));

        final Query<StateEntity> query = session.createQuery(criteriaQuery);
        final Optional<StateEntity> optional = query.uniqueResultOptional();

        stateDao.closeCurrentSessionWithTransaction();

        return optional;
    }

}