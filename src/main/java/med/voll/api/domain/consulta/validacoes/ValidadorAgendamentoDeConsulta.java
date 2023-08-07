package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.ConsultaAgendarDTO;

public interface ValidadorAgendamentoDeConsulta {
	
	void validar(ConsultaAgendarDTO dados);

}
