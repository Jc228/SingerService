package com.distribuida.rest.email;


import com.distribuida.rest.entidades.Singer;

import javax.mail.MessagingException;
import java.util.List;

public interface EmailServices {
    void enviar (String to, String subject, StringBuilder text);
    //void enviarList (String to, String subject, List<String> singers) throws MessagingException;

}
