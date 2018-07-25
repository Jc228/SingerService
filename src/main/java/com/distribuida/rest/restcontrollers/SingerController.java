package com.distribuida.rest.restcontrollers;

import com.distribuida.rest.email.EmailServices;
import com.distribuida.rest.entidades.Singer;
import com.distribuida.rest.impl.SingerDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/singer")
public class SingerController {
    @Autowired
    private SingerDaoImpl singerSingerDao;


    @Autowired
    EmailServices emailServices;

    @RequestMapping(value = "/listar", method = RequestMethod.GET )
    public List<Singer> listar(){
        return singerSingerDao.listar();
    }

    @RequestMapping(value = "/buscar/{id}", method = RequestMethod.GET)
    public Singer buscarSinger(@PathVariable ("id") Long id){
        Singer n = singerSingerDao.findById(id);
        System.out.println("singer "+n);
        return n;
    }

    @RequestMapping(value = "/actualizar/{id}" , method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Singer actualizarSinger(@PathVariable ("id") Long id, @RequestBody Singer actSinger){
        Singer singer = singerSingerDao.findById(id);
        singer.setFirstName(actSinger.getFirstName());
        singer.setLastName(actSinger.getLastName());
        singer.setBirthDate(actSinger.getBirthDate());
        singer.setEmail(actSinger.getEmail());
        Singer act = singerSingerDao.updateSinger(singer);
        return act;
    }

    @RequestMapping(value = "/crear", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Singer crearSinger(@Valid @RequestBody Singer singer){
        return singerSingerDao.addSinger(singer); }

    @RequestMapping(value = "/eliminar/{id}",method = RequestMethod.DELETE)
    public boolean eliminarSinger(@PathVariable Long id){
        boolean eliminar = false;
        Singer eliminarSinger = singerSingerDao.findById(id);
        if (eliminarSinger!=null){
            singerSingerDao.deleteSinger(eliminarSinger);
            eliminar = true;
        }else{
            eliminar = false;
        }
        return eliminar;
    }

    @RequestMapping(value = "/correos", method = RequestMethod.GET)
    public List<String> getEmail(){
        List<String> mails = singerSingerDao.getEmails();
        return mails;
    }
}
