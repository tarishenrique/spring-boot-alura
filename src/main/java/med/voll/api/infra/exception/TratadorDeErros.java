package med.voll.api.infra.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarErro404() {
		
		return ResponseEntity.notFound().build();
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
		
		List<FieldError> erros = exception.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(ErrosValidacaoDTO::new).toList());
	}
	
	private record ErrosValidacaoDTO(String campo, String mensagem) {
		public ErrosValidacaoDTO(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
	
}
