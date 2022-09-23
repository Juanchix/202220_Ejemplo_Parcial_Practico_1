package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository;
    
    @Transactional
    public MedicoEntity createMedicos (MedicoEntity medico)  throws IllegalOperationException{
        log.info("Se está creando un nuevo médico... ");
        if (medico.getRegisrto().startsWith("RM")){
            return medicoRepository.save(medico);
        }
        else{
            throw new IllegalOperationException("No fue posible crear un médico");
        }
    }

}
