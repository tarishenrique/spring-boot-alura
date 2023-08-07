package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.ConsultaAgendarDTO;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.infra.exception.ValidacaoException;
@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {
	@Autowired
	private ConsultaRepository repository;
	
	public void validar(ConsultaAgendarDTO dados) {
		Boolean medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
		
		if (medicoPossuiOutraConsultaNoMesmoHorario) {
			throw new ValidacaoException("Medico já possui outra consulta agendada nesse mesmo horário");
		}
	}
	
}
