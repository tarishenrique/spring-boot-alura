package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record ConsultaDetalhamentoDTO(
		Long id,
		Long idMedico,
		Long idPaciente,
		LocalDateTime data
		) {

}
