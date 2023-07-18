package med.voll.api.domain.paciente;

public record PacienteListarDTO(
		Long id, 
		String nome, 
		String email, 
		String cpf) {

	public PacienteListarDTO(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
	}

}
