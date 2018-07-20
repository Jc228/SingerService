package com.distribuida.rest.interfaces;

import com.distribuida.rest.entidades.Singer;

import java.util.List;

public interface SingerDao{
    List<Singer> listar();
    Singer findById(Long id);
    Singer addSinger(Singer singer);
    Singer updateSinger(Singer singer);
    Integer deleteSinger(Singer singer);
    List<String> getEmails();
}
