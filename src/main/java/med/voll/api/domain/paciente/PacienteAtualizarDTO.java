package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.cadastrogeral.DadosEndereco;

public record PacienteAtualizarDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
		) {

}
