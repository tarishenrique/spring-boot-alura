package med.voll.api.domain.consulta.validacoes;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

import med.voll.api.domain.consulta.ConsultaAgendarDTO;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidadorHorarioAntecendencia {
	
	public void validar(ConsultaAgendarDTO dados) {
		LocalDateTime dataConsulta = dados.data();
		
		LocalDateTime agora = LocalDateTime.now();
		long diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
		
		if(diferencaEmMinutos < 30) {
			throw new ValidacaoException("Consulta deve ser agendada com antecedencia minima de 30 minuitos");
		}
		
	}
}
