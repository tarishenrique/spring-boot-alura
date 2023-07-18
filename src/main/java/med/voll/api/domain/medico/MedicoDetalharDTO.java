package med.voll.api.domain.medico;

import med.voll.api.domain.cadastrogeral.Endereco;

public record MedicoDetalharDTO(Long id, String nome, String email, String crm, String telefone,
		Especialidade especialidade, Endereco endereco) {

	public MedicoDetalharDTO(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(),
				medico.getEspecialidade(), medico.getEndereço());
	}

}
