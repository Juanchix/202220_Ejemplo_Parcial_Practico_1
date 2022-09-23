
package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;

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
@Import(MedicoService.class)

public class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    
    private List<MedicoEntity> MedicoList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate(); 
    }
    

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
            entityManager.persist(medicoEntity);
            MedicoList.add(medicoEntity);
        }
    }

    @Test
    void testCreateMedicos() throws IllegalOperationException{
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setRegisrto("RM1");   //forzar a que todo inicie con RM
        MedicoEntity result = medicoService.createMedicos(newEntity);
        assertNotNull(result);
        MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());
    
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getApellido(), entity.getApellido());
        assertEquals(newEntity. getEspecialidades(), entity.getEspecialidades());
    }

    @Test
    void testMedicoInvalido() throws IllegalOperationException{
        assertThrows(IllegalOperationException.class, () ->{
            MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
            newEntity.setRegisrto("");  //forzar a que la prueba "falle" o sea que funcione
            medicoService.createMedicos(newEntity);
        });
    }
}
