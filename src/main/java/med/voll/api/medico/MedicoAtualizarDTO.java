package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.cadastrogeral.*;

public record MedicoAtualizarDTO(
		
		@NotNull
		Long id,
		String nome,
		String telefone,
		DadosEndereco endereco
		
		) {

}
