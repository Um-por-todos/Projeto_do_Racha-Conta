package com.br.edu.iff.rachaconta.webproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "com.br.edu.iff.rachaconta.webproject.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tratarRecursoNaoEncontrado(RecursoNaoEncontradoException exception, Model model) {
        model.addAttribute("titulo", "Recurso não encontrado");
        model.addAttribute("mensagem", exception.getMessage());
        model.addAttribute("codigo", 404);
        return "error/404";
    }

    @ExceptionHandler({RegraDeNegocioException.class, EntidadeDuplicadaException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String tratarRegraDeNegocio(RuntimeException exception, Model model) {
        model.addAttribute("titulo", "Não foi possível concluir a operação");
        model.addAttribute("mensagem", exception.getMessage());
        model.addAttribute("codigo", 400);
        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String tratarErroInesperado(Exception exception, Model model) {
        model.addAttribute("titulo", "Erro inesperado");
        model.addAttribute("mensagem", "Ocorreu um erro interno. Tente novamente ou retorne à página inicial.");
        model.addAttribute("codigo", 500);
        return "error/500";
    }
}
