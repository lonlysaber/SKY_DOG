package com.example.skydog.dao;

import com.example.skydog.module.entity.Collect;
import com.example.skydog.module.entity.Evaluate;
import com.example.skydog.module.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: 张振彬
 * @create: 2022-09-07
 * @version: 1.0
 */
@Mapper
public interface CollectDao {

    void add(Collect collect);

    void delete(Collect collect);

    List<Collect> getMyCollect(Integer userId);

    List<Collect> getMyStore(Integer userId);

    List<Collect> getUseless(Integer userId);

    List<Collect> getSearch(Integer userId, String keyword);

    Collect queryId(Integer collectId);

    List<Collect> queryCondition(Collect collect);

    void batchDelete(Integer userId, List list);

}
