package com.davidbneto.votacao.messaging;


import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Produtor {

    private final Channel channel;

    public void enviarMensagem(String mensagem, String fila) {
        try {
            channel.queueDeclare(fila, false, false, false, null);
            channel.basicPublish("", fila, null, mensagem.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void enviarObjeto(Object object, String fila) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        enviarMensagem(json, fila);
    }
}
