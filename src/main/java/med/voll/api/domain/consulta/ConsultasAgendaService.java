package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;

@Service
public class ConsultasAgendaService {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadores;
	
	public ConsultaDetalharDTO agendar(ConsultaAgendarDTO dados){
		
		if (!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("Id do paciente informado não existe!");
		}
		
		if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("Id do medico informado não existe!");
		}
		
		validadores.forEach(v -> v.validar(dados));
		
		Paciente paciente = pacienteRepository.findById(dados.idPaciente()).get();
		Medico medico = escolherMedico(dados);
		Consulta consulta = new Consulta(null, medico, paciente, dados.data());
		
		consultaRepository.save(consulta);
		
		return new ConsultaDetalharDTO(consulta);
		
	}

	private Medico escolherMedico(ConsultaAgendarDTO dados) {
		
		if (dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		
		if (dados.especialidade() == null) {
			throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
		}
		
		Medico medicoAleatorio = medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
		
		return null;
	}

}
