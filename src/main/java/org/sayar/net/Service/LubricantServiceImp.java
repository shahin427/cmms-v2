package org.sayar.net.Service;

import org.sayar.net.Dao.LubricantDao;
import org.sayar.net.Model.Lubricant;
import org.sayar.net.Service.newService.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LubricantServiceImp implements LubricantService {

    @Autowired
    private LubricantDao lubricantDao;

    @Autowired
    private AssetService assetService;

    @Override
    public boolean save(Lubricant lubricant) {
        return lubricantDao.save(lubricant);
    }

    @Override
    public Page<Lubricant> getAll(Lubricant lubricant, Pageable pageable, Integer totalElements) {
        return lubricantDao.getAll(lubricant, pageable, totalElements);
    }

    @Override
    public Lubricant getOne(String id) {
        return lubricantDao.getOne(id);
    }

    @Override
    public boolean update(Lubricant lubricant) {
        return lubricantDao.update(lubricant);
    }

    @Override
    public boolean delete(String id) {
        return lubricantDao.delete(id);
    }

    @Override
    public List<Lubricant> getAllWithNoPage() {
        return lubricantDao.getAllWithNoPage();
    }

    @Override
    public boolean checkIfLubricantUsedInAsset(String id) {
        return assetService.checkIfLubricantUsedInAsset(id);
    }

}
