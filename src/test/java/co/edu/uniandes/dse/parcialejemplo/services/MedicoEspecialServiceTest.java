package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ EspecialidadService.class, MedicoEspecialService.class })
class EspecialidadMedicoServiceTest {

	@Autowired
	private MedicoEspecialService medicoEspecialService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<EspecialidadEntity> especialidadList = new ArrayList<>();
	private List<MedicoEntity> medicoList = new ArrayList<>();


	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}


	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
	}

	private void insertData() {
		for (int i = 0; i < 3; i++) {
			MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
			entityManager.persist(medico);
			medicoList.add(medico);
		}

		for (int i = 0; i < 3; i++) {
			EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
			entityManager.persist(entity);
			especialidadList.add(entity);
		}
	}


	@Test
	void testAddEspecialidad() throws EntityNotFoundException {
		EspecialidadEntity entity = especialidadList.get(0);
		MedicoEntity medicoEntity = medicoList.get(1);
		EspecialidadEntity response = medicoEspecialService.addEspecialidad(medicoEntity.getId(), entity.getId());

		assertNotNull(response);
		assertEquals(medicoEntity.getId(), response.getId());
	}
	

	@Test
	void testAddInvalidMedico() {
		assertThrows(EntityNotFoundException.class, ()->{
			EspecialidadEntity entity = especialidadList.get(0);
			medicoEspecialService.addEspecialidad(0L, entity.getId());
		});
	}
	

	@Test
	void testAddMedicoInvalidEspecialidad() {
		assertThrows(EntityNotFoundException.class, ()->{
			MedicoEntity bookEntity = medicoList.get(1);
			medicoEspecialService.addEspecialidad(medicoId, especialidadId)(bookEntity.getId(), 0L);
		});
	}
}
