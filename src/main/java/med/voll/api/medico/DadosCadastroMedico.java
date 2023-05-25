package med.voll.api.medico;

import med.voll.api.cadastrogeral.CgEndereco;

public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade, CgEndereco endereco) {
}
