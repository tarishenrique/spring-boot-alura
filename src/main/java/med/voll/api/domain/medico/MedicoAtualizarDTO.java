package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.cadastrogeral.*;
import med.voll.api.domain.cadastrogeral.DadosEndereco;

public record MedicoAtualizarDTO(
		
		@NotNull
		Long id,
		String nome,
		String telefone,
		DadosEndereco endereco
		
		) {

}
