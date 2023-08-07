package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.ConsultaAgendarDTO;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidadorPacienteAtivo {
	
	private PacienteRepository repository;
	
	public void validar(ConsultaAgendarDTO dados) {
		Boolean pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
		if (!pacienteEstaAtivo) {
			throw new ValidacaoException("Consulta nã pode ser agendada com paciente excluído");
		}
		
	}

}
