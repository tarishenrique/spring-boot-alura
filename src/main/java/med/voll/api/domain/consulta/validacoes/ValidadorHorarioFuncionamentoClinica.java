package med.voll.api.domain.consulta.validacoes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import med.voll.api.domain.consulta.ConsultaAgendarDTO;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidadorHorarioFuncionamentoClinica {
	
	public void validar(ConsultaAgendarDTO dados) {
		LocalDateTime dataConsulta = dados.data();
		
		boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		boolean antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
		boolean depoisDaAberturaDaClinica = dataConsulta.getHour() > 18;
		
		if(domingo || antesDaAberturaDaClinica || depoisDaAberturaDaClinica) {
			throw new ValidacaoException("Consulta fora do horário de funcionamento da clinica");
		}
		
	}

}
