package org.sayar.net.Dao;

import org.sayar.net.Model.Lubricant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LubricantDao {

    boolean save(Lubricant lubricant);

    Page<Lubricant> getAll(Lubricant lubricant, Pageable pageable, Integer totalElements);

    Lubricant getOne(String id);

    boolean update(Lubricant lubricant);

    boolean delete(String id);

    List<Lubricant> getAllWithNoPage();
}
