package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.ConsultaAgendarDTO;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidadorMedicoAtivo {
	
	private MedicoRepository repository;
	
	public void validar(ConsultaAgendarDTO dados) {
		//escolha de medico opcional
		if (dados.idMedico() == null) {
			return;
		}
		
		Boolean medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
		if (!medicoEstaAtivo) {
			throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
		}
		
		
	}

}
