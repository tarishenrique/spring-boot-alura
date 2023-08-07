package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.ConsultaAgendarDTO;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidadorMedicoComOutraConsultaNoMesmoHorario {

	private ConsultaRepository repository;
	
	public void validar(ConsultaAgendarDTO dados) {
		Boolean medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
		
		if (medicoPossuiOutraConsultaNoMesmoHorario) {
			throw new ValidacaoException("Medico já possui outra consulta agendada nesse mesmo horário");
		}
	}
	
}
