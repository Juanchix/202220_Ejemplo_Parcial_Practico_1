package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class MedicoEspecialService {

    @Autowired
    private MedicoRepository medicoRepository;
    
    @Autowired 
    private EspecialidadRepository especialidadRepository;


    /**Asociar una especialidad al medico */
    @Transactional
    public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException{
        log.info("Se asociará la especialidad con id={0}",especialidadId, " al medico con id={0}" , medicoId);
        Optional < EspecialidadEntity > especialidadEntity = especialidadRepository.findById(especialidadId);
        Optional < MedicoEntity > medicoEntity = medicoRepository.findById(medicoId);

        if (especialidadEntity.isEmpty())
            throw new EntityNotFoundException("No se encontro esta especialidad");

        if (medicoEntity.isEmpty())
            throw new EntityNotFoundException("No se encontró este médico");

        medicoEntity.get().getEspecialidades().add(especialidadEntity.get());
        log.info("Se le ha asociado la especialidad al medico id = {0}", medicoId);
        return especialidadEntity.get();
    }
    
}
