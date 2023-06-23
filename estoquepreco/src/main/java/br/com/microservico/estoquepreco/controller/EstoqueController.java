package br.com.microservico.estoquepreco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservico.estoquepreco.constants.RabbitMQConstants;
import br.com.microservico.estoquepreco.dto.EstoqueDto;
import br.com.microservico.estoquepreco.service.RabbitMQService;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private RabbitMQService rabbitMQService;
    
    @PutMapping()
    private ResponseEntity<?> alteraEstoque(@RequestBody EstoqueDto estoqueDto){

        System.out.println(estoqueDto.codigoProduto);
        this.rabbitMQService.enviaMensagem(RabbitMQConstants.FILA_ESTOQUE, estoqueDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
