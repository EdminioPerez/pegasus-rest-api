package com.greek.service.repositories.custom;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.greek.main.hibernate.model.Persona;
import com.gvt.security.utils.JwtUtils;
import com.greek.service.repositories.PersonCustomRepository;

public class PersonCustomRepositoryImpl implements PersonCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<Persona> findPersonsLike(@Param("globalFilter") String globalFilter, Pageable pageable) {
		StringBuilder query = new StringBuilder("from Persona p where (");
		StringBuilder queryCount = new StringBuilder("select count(p) from Persona p where (");
		List<Object> parameters = new ArrayList<>();
		int paramPlace = 1;

		if (StringUtils.isNotBlank(globalFilter)) {
			String[] busqueda = StringUtils.splitByWholeSeparator(Normalizer
					.normalize(globalFilter, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""),
					null, 4);

			boolean putOr = false;

			for (int x = 0; x < busqueda.length; ++x) {
				if (putOr) {
					query.append(" or ");
					queryCount.append(" or ");
				}

				query.append("(upper(p.nombrePersona) like upper(?" + paramPlace
						+ ") or upper(p.apellidoPersona) like upper(?" + paramPlace + "))");
				queryCount.append("(upper(p.nombrePersona) like upper(?" + paramPlace
						+ ") or upper(p.apellidoPersona) like upper(?" + paramPlace + "))");

				putOr = true;
				++paramPlace;
			}

			query.append(")");
			queryCount.append(")");

			Collections.addAll(parameters, busqueda);
		} else {
			query.append("1=?1)");
			queryCount.append("1=?1)");

			parameters.add(Integer.valueOf(1));

			++paramPlace;
		}

		query.append(" and p.organizacion.id = ?" + paramPlace);
		queryCount.append(" and p.organizacion.id = ?" + paramPlace);
		parameters.add(JwtUtils.getJwtAuthenticationDetails().getRootCenterId());

		Query jpqlquery = entityManager.createQuery(query.toString());
		for (int x = 0; x < parameters.size(); ++x) {
			jpqlquery.setParameter(x + 1, parameters.get(x));
		}
		jpqlquery.setFirstResult((int) pageable.getOffset());
		jpqlquery.setMaxResults(pageable.getPageSize());

		Query countQuery = entityManager.createQuery(queryCount.toString());
		for (int x = 0; x < parameters.size(); ++x) {
			countQuery.setParameter(x + 1, parameters.get(x));
		}

		List<Persona> content = jpqlquery.getResultList();
		Long total = (Long) countQuery.getSingleResult();

		return new PageImpl<>(content, pageable, total);
	}
}
