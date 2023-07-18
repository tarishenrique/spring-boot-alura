package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
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
	public ResponseEntity agendar(ConsultaAgendarDTO dados) {
		agenda.agendar(dados);
		return ResponseEntity.ok(new ConsultaDetalharDTO(null, null, null, null));
	}

}
