package com.greek.service.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.greek.main.hibernate.model.TipoDocumentoIdentificacion;
import com.gvt.data.JPAConfiguration;
import com.gvt.data.security.support.SecurityEvaluationContextExtension;
import com.gvt.security.test.context.support.WithMockedUser;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { JPAConfiguration.class, DataSourceAutoConfiguration.class,
		SecurityEvaluationContextExtension.class })
@EnableJpaRepositories(basePackages = { "com.greek.service.repositories" })
@TestPropertySource({ "classpath:application.properties" })
@WithMockedUser
public class IdentityDocumentTypeJPALayerIT {

	@Autowired
	private IdentityDocumentTypeRepository identityDocumentTypeRepository;

	@Test
	public void when_find_all_provinces_by_spain_ok() {
		List<TipoDocumentoIdentificacion> identityDocumentsType = identityDocumentTypeRepository.findAll();

		assertThat(identityDocumentsType.size(), is(4));
	}

}