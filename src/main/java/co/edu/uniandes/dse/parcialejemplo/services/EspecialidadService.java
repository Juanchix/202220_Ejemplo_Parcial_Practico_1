package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.CafeEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.CafeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class CafeService {

    @Autowired
    CafeRepository especialidadRepository;
    
    @Transactional
    public CafeEntity createCafees (CafeEntity especialidad)  throws IllegalOperationException{
        log.info("Se está creando una nueva especialidad... ");
        if (especialidad.getDescripcion().length() >= 10){
            return especialidadRepository.save(especialidad);
        }
        else{
            throw new IllegalOperationException("No fue posible crear una nueva especialidad");
        }
    }
}
