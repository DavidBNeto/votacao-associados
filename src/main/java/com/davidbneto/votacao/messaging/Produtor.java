package com.davidbneto.votacao.messaging;


import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;

@Slf4j
@Component
@RequiredArgsConstructor
public class Produtor {

    private final Channel channel;

    public void enviarMensagem(String mensagem, String exchange, String routingKey) {
        try {
            byte[] messageBodyBytes = mensagem.getBytes();
            AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder();
            var timestamp = now().toInstant(UTC);
            props.timestamp(Date.from(timestamp));
            channel.basicPublish(exchange, routingKey, props.build(), messageBodyBytes);
            log.error("Mensagem publicada na exchange '{}' com sucesso!", exchange);
        } catch (Exception e) {
            log.error("Erro ao publicar mensagem: ", e);
            throw new RuntimeException(e);
        }
    }

    public void enviarMensagemComDelay(String mensagem, String exchange, String routingKey, Long tempoDeDelayMs) {
        try {
            byte[] messageBodyBytes = mensagem.getBytes();
            AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder();
            var headers = new HashMap<String, Object>();
            headers.put("x-delay", tempoDeDelayMs);
            props.headers(headers);
            var timestamp = now().toInstant(UTC);
            props.timestamp(Date.from(timestamp));
            channel.basicPublish(exchange, routingKey, props.build(), messageBodyBytes);
            log.error("Mensagem publicada na exchange '{}' com delay de {}ms com sucesso!", exchange, tempoDeDelayMs);
        } catch (Exception e) {
            log.error("Erro ao publicar mensagem com delay: ", e);
            throw new RuntimeException(e);
        }
    }

    public void enviarObjetoComDelay(Object object, String exchange, String routingKey, Long tempoDeDelayMs) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        enviarMensagemComDelay(json, exchange, routingKey, tempoDeDelayMs);
    }

    public void enviarObjeto(Object object, String exchange, String routingKey) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        enviarMensagem(json, exchange, routingKey);
    }
}
