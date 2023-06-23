package br.com.microservico.estoquepreco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservico.estoquepreco.constants.RabbitMQConstants;
import br.com.microservico.estoquepreco.dto.PrecoDto;
import br.com.microservico.estoquepreco.service.RabbitMQService;

@RestController
@RequestMapping("/preco")
public class PrecoController {

    @Autowired
    private RabbitMQService rabbitMQService;
    
    @PutMapping()
    private ResponseEntity<?> alteraPreco(@RequestBody PrecoDto request){

        rabbitMQService.enviaMensagem(RabbitMQConstants.FILA_PRECO, request);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
