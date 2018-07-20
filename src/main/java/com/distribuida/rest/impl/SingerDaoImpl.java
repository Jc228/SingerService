package com.distribuida.rest.impl;

import com.distribuida.rest.entidades.Singer;
import com.distribuida.rest.interfaces.SingerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class SingerDaoImpl implements SingerDao {
    private final String LISTAR = "SELECT * FROM SINGER";
    private final String BUSCAR_POR_ID = "SELECT * FROM SINGER WHERE id=?";
    private final String INSERTAR = "INSERT INTO SINGER (FIRST_NAME,LAST_NAME,BIRTH_DATE, EMAIL) values (?,?,?,?)";
    private final String UPDATE = "UPDATE SINGER SET FIRST_NAME=?,LAST_NAME=?,BIRTH_DATE=?, EMAIL=? WHERE id=?";
    private final String DELETE = "DELETE FROM SINGER WHERE id=?";
    private final String GET_MAILS = "SELECT EMAIL FROM SINGER";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Singer> listar ( ) {
        return jdbcTemplate.query(LISTAR, new SingerMapper());
    }

    @Override
    public Singer findById (Long id) {
        return jdbcTemplate.queryForObject(BUSCAR_POR_ID, new Object[]{id}, new SingerMapper());
    }

    @Override
    public Singer addSinger (Singer singer) {
        Object[] datos = new Object[] { singer.getFirstName(), singer.getLastName(), singer.getBirthDate(), singer.getEmail() };
        int[] tipos = new int[]{Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR};
        int registro = jdbcTemplate.update(INSERTAR,datos,tipos);
        System.out.println("Se inserto: "+singer.getFirstName()+" - "+singer.getLastName()+" - "+singer.getBirthDate()+ " - "+singer.getEmail());
        return singer;
    }

    @Override
    public Singer updateSinger (Singer singer) {
        Object[] datos = new Object[] { singer.getFirstName(), singer.getLastName(), singer.getBirthDate(),singer.getEmail(), singer.getId() };
        int[] tipos = new int[]{Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.INTEGER};
        int registro = jdbcTemplate.update(UPDATE,datos,tipos);
        System.out.println("Se actualizo: "+singer.getFirstName()+" - "+singer.getLastName()+" - "+singer.getBirthDate()+" - "+singer.getEmail());
        return singer;
    }

    @Override
    public Integer deleteSinger (Singer singer) {
        Long id = singer.getId();
        return jdbcTemplate.update(DELETE, new Object[]{Long.valueOf(id)});
    }

    @Override
    public List<String> getEmails ( ) {
        return jdbcTemplate.query(GET_MAILS, new EmailMapper());
    }
}


class SingerMapper implements RowMapper<Singer> {

    @Override
    public Singer mapRow (ResultSet rs, int rowNum) throws SQLException {
        Singer singer = new Singer();
        singer.setId(rs.getLong("id"));
        singer.setFirstName(rs.getString("first_name"));
        singer.setLastName(rs.getString("last_name"));
        singer.setBirthDate(rs.getDate("birth_date"));
        singer.setVersion(rs.getInt("version"));
        singer.setEmail(rs.getString("email"));
        return singer;
    }
}

class EmailMapper implements RowMapper<String>{

    @Override
    public String mapRow (ResultSet rs, int rowNum) throws SQLException {
        Singer singer = new Singer();
        singer.setEmail(rs.getString("email"));
        return singer.getEmail();
    }
}
