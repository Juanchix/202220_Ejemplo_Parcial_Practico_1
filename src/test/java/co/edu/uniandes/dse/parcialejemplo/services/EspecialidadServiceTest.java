  
package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(EspecialidadService.class)

public class EspecialidadServiceTest {

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    
    private List<EspecialidadEntity> EspecialidadList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate(); 
    }
    

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(especialidadEntity);
            EspecialidadList.add(especialidadEntity);
        }
    }

    @Test
    void testCreateEspecialidads() throws IllegalOperationException{
        EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
        newEntity.setDescripcion("llllllllll");
        EspecialidadEntity result = especialidadService.createEspecialidades(newEntity);
        assertNotNull(result);
        EspecialidadEntity entity = entityManager.find(EspecialidadEntity.class, result.getId());
    
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

    @Test
    void testEspecialidadInvalido() throws IllegalOperationException{
        assertThrows(IllegalOperationException.class, () ->{
            EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
            newEntity.setDescripcion("");  //forzar a que la prueba "falle" o sea que funcione
            especialidadService.createEspecialidades(newEntity);
        });
    }
}

