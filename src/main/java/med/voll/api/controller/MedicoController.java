package med.voll.api.controller;

import med.voll.api.domain.medico.MedicoAtualizarDTO;
import med.voll.api.domain.medico.MedicoCadastrarDTO;
import med.voll.api.domain.medico.MedicoDetalharDTO;
import med.voll.api.domain.medico.MedicoListarDTO;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
	
	@Autowired
	private MedicoRepository repository;
	
    @PostMapping("/medicos")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid MedicoCadastrarDTO dados, UriComponentsBuilder uriBuilder){
        Medico medico = new Medico(dados);
    	repository.save(medico);
		
        URI uri = uriBuilder.path("/api/v1/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        
        return ResponseEntity.created(uri).body(new MedicoDetalharDTO(medico));

    }
    
    @GetMapping("/medicos")
    public ResponseEntity<Page<MedicoListarDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
    	
    	Page<MedicoListarDTO> page = repository.findAllByAtivoTrue(paginacao).map(MedicoListarDTO::new);
    	
    	return ResponseEntity.ok(page);
    }
    
    @PutMapping("/medicos")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid MedicoAtualizarDTO dados) {
    	
    	Medico medico = repository.getReferenceById(dados.id()) ;
    	medico.atualizarInformacoes(dados);
		return ResponseEntity.ok(new MedicoDetalharDTO(medico));
    }
    
    @DeleteMapping("/medicos/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
    	var medico = repository.getReferenceById(id);
    	medico.inativar();
    	
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/medicos/{id}")
    public ResponseEntity listarPorId(@PathVariable Long id) {
    	Medico medico = repository.getReferenceById(id);
    	
    	return ResponseEntity.ok(new MedicoDetalharDTO(medico));
    }
    



}
