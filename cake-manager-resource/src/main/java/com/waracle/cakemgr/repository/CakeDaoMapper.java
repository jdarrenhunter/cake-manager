package com.waracle.cakemgr.repository;

import com.waracle.cakemgr.domain.Cake;

public class CakeDaoMapper {

    public CakeDao fromCake(Cake cake) {
        CakeDao cakeDao = new CakeDao();
        cakeDao.setTitle(cake.getTitle());
        cakeDao.setDescription(cake.getDesc());
        cakeDao.setImage(cake.getImage());
        return cakeDao;
    }

    public Cake toCake(CakeDao cakeDao) {
        return Cake.builder()
                .title(cakeDao.getTitle())
                .desc(cakeDao.getDescription())
                .image(cakeDao.getImage()).build();
    }

}
