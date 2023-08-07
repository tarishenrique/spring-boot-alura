package med.voll.api.domain.consulta.validacoes;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.ConsultaAgendarDTO;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.infra.exception.ValidacaoException;
@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {
	@Autowired
	private ConsultaRepository repository;
	
	public void validar(ConsultaAgendarDTO dados) {
		LocalDateTime primeiroHorario = dados.data().withHour(7);
		LocalDateTime ultimoHorario = dados.data().withHour(18);
		Boolean pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
		if (pacientePossuiOutraConsultaNoDia) {
			throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
		}
	}

}
