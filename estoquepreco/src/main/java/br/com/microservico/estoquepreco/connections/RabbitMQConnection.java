package br.com.microservico.estoquepreco.connections;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import br.com.microservico.estoquepreco.constants.RabbitMQConstants;
import jakarta.annotation.PostConstruct;

@Component
public class RabbitMQConnection {
    
    private static final String NOME_EXCHANGE = "amq.direct";
    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }


    private Queue fila(String nomeFila){
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta(){
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona (){
        Queue filaEstoque = this.fila(RabbitMQConstants.FILA_ESTOQUE);
        Queue filaPreco = this.fila(RabbitMQConstants.FILA_PRECO);

        DirectExchange troca= this.trocaDireta();

        Binding ligacaoPreco =  this.relacionamento(filaPreco, troca);
        Binding ligacaoEstoque =  this.relacionamento(filaEstoque, troca);

        //criando as filas no rabbitmq
        amqpAdmin.declareQueue(filaEstoque);
        amqpAdmin.declareQueue(filaPreco);

        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(ligacaoEstoque);
        this.amqpAdmin.declareBinding(ligacaoPreco);
    }
}
