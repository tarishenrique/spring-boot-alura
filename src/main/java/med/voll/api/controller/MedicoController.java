package med.voll.api.controller;

import med.voll.api.medico.MedicoAtualizarDTO;
import med.voll.api.medico.MedicoCadastrarDTO;
import med.voll.api.medico.MedicoDetalhamentoDTO;
import med.voll.api.medico.MedicoListarDTO;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

import java.util.List;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class MedicoController {
	
	@Autowired
	private MedicoRepository repository;
	
    @PostMapping("/medicos")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid MedicoCadastrarDTO dados){
        repository.save(new Medico(dados));

    }
    
//    public Medico criarMedico(Medico medico ) {
//    	return repository.save(medico);
//    }
    
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
		return ResponseEntity.ok(new MedicoDetalhamentoDTO(medico));
    }
    
    @DeleteMapping("/medicos/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
    	var medico = repository.getReferenceById(id);
    	medico.inativar();
    	
    	return ResponseEntity.noContent().build();
    }
    
//    @DeleteMapping("/medicos/{id}")
//    @Transactional
//    public void excluir(@PathVariable Long id) {
//    	repository.deleteById(id);
//    }


}
