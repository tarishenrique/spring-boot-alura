package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteAtualizacaoDTO;
import med.voll.api.domain.paciente.PacienteCadastroDTO;
import med.voll.api.domain.paciente.PacienteDetalhamentoDTO;
import med.voll.api.domain.paciente.PacienteListagemDTO;
import med.voll.api.domain.paciente.PacienteRepository;

@RestController
@RequestMapping("/api/v1/")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping("/pacientes")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid PacienteCadastroDTO dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new PacienteDetalhamentoDTO(paciente));
    }

    @GetMapping("/pacientes")
    public ResponseEntity<Page<PacienteListagemDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(PacienteListagemDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/pacientes")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid PacienteAtualizacaoDTO dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new PacienteDetalhamentoDTO(paciente));
    }

    @DeleteMapping("/pacientes/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new PacienteDetalhamentoDTO(paciente));
    }


}
