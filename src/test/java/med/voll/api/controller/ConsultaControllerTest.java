package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.consulta.ConsultaAgendarDTO;
import med.voll.api.domain.consulta.ConsultaDetalharDTO;
import med.voll.api.domain.consulta.ConsultasAgendaService;
import med.voll.api.domain.medico.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<ConsultaAgendarDTO> dadosAgendamentoConsultaJson;
	
	@Autowired
	private JacksonTester<ConsultaDetalharDTO> dadosDetalhamentoConsultaJson;
	
	@MockBean
	private ConsultasAgendaService agendaDeConsultas;
	
	@Test
	@DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
	@WithMockUser
	void agendarCenario1() throws Exception{
		
		MockHttpServletResponse response = mvc.perform(post("/api/v1/consultas"))
			.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
	}
	
	@Test
	@DisplayName("Deveria devolver codigo http 200 quando informacoes estao invalidas")
	@WithMockUser
	void agendarCenario2() throws Exception {

		LocalDateTime data = LocalDateTime.now().plusHours(1);
		Especialidade especialidade = Especialidade.CARDIOLOGIA;
		ConsultaDetalharDTO dadosDetalhamento = new ConsultaDetalharDTO(null, 2l, 5l, data);

		when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

		MockHttpServletResponse response = mvc
				.perform(
						post("/api/v1/consultas")
								.contentType(MediaType.APPLICATION_JSON)
								.content(dadosAgendamentoConsultaJson.write(
										new ConsultaAgendarDTO(2l, 5l, data, especialidade)).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		String jsonEsperado = dadosDetalhamentoConsultaJson.write(
				dadosDetalhamento).getJson();

		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

}
