package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.ConsultaAgendarDTO;
import med.voll.api.domain.consulta.ConsultaDetalharDTO;
import med.voll.api.domain.consulta.ConsultasAgendaService;

@RestController
@RequestMapping("/api/v1/")
public class ConsultaController {
	
	@Autowired
	private ConsultasAgendaService agenda;
	
	@PostMapping("/consultas")
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid ConsultaAgendarDTO dados) {
		ConsultaDetalharDTO dto = agenda.agendar(dados);
		return ResponseEntity.ok(dto);
	}

}
