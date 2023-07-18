package med.voll.api.domain.consulta;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@Service
public class ConsultasAgendaService {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	
	public void agendar(ConsultaAgendarDTO dados){
		
		Paciente paciente = pacienteRepository.findById(dados.idPaciente()).get();
		Medico medico = medicoRepository.findById(dados.idMedico()).get();
		Consulta consulta = new Consulta(null, medico, paciente, dados.data());
		
		consultaRepository.save(consulta);
		
	}

}
