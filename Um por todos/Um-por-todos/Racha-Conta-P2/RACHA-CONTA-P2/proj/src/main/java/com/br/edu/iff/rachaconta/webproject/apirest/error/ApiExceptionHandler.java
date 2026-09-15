package com.br.edu.iff.rachaconta.webproject.apirest.error;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br.edu.iff.rachaconta.webproject.exception.EntidadeDuplicadaException;
import com.br.edu.iff.rachaconta.webproject.exception.RecursoNaoEncontradoException;
import com.br.edu.iff.rachaconta.webproject.exception.RegraDeNegocioException;

@RestControllerAdvice(basePackages = "com.br.edu.iff.rachaconta.webproject.apirest")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> tratarRecursoNaoEncontrado(
        RecursoNaoEncontradoException exception,
        WebRequest request
    ) {
        ProblemDetail problem = criarProblemDetail(
            HttpStatus.NOT_FOUND,
            "Recurso não encontrado",
            exception.getMessage(),
            "recurso-nao-encontrado",
            request
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ProblemDetail> tratarRegraDeNegocio(
        RegraDeNegocioException exception,
        WebRequest request
    ) {
        ProblemDetail problem = criarProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Regra de negócio inválida",
            exception.getMessage(),
            "regra-de-negocio",
            request
        );
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ResponseEntity<ProblemDetail> tratarEntidadeDuplicada(
        EntidadeDuplicadaException exception,
        WebRequest request
    ) {
        ProblemDetail problem = criarProblemDetail(
            HttpStatus.CONFLICT,
            "Entidade duplicada",
            exception.getMessage(),
            "entidade-duplicada",
            request
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        List<Map<String, String>> invalidParams = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> Map.of(
                "campo", error.getField(),
                "mensagem", error.getDefaultMessage() == null ? "Valor inválido." : error.getDefaultMessage()
            ))
            .toList();

        ProblemDetail problem = criarProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Dados de entrada inválidos",
            "Um ou mais campos enviados não passaram pela validação.",
            "dados-invalidos",
            request
        );
        problem.setProperty("invalid_params", invalidParams);

        return handleExceptionInternal(exception, problem, headers, HttpStatus.BAD_REQUEST, request);
    }

    private ProblemDetail criarProblemDetail(
        HttpStatus status,
        String titulo,
        String detalhe,
        String tipo,
        WebRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detalhe);
        problem.setTitle(titulo);
        problem.setType(URI.create("https://rachaconta.local/problemas/" + tipo));

        if (request instanceof ServletWebRequest servletRequest) {
            problem.setInstance(URI.create(servletRequest.getRequest().getRequestURI()));
        }

        return problem;
    }
}
