package com.distribuida.rest.rabbitmq;

import com.distribuida.rest.Aplicacion;
import com.distribuida.rest.email.EmailServices;
import com.distribuida.rest.entidades.Instrument;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class Listener {

    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    EmailServices emailServices;
    @RabbitListener (queues = Aplicacion.QUEUE_SPECIFIC_NAME)
    public void receiveMessagex(final Instrument instrument) {
        System.out.println("Instrumento recibido:" +instrument.toString());
        //Enviar correos
        ResponseEntity <List<String>> responseEntity = restTemplate.exchange("http://localhost:8010/singer/correos", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        List<String> correos = responseEntity.getBody();
        for (String correo: correos){
            emailServices.enviar(correo,"Nuevo Instrumento", new StringBuilder("Se agrego un nuevo instrumento a la lista: ").append(instrument.toString()));
        }
    }

}
