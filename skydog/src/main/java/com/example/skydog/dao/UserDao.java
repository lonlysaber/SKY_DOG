package com.example.skydog.dao;

import com.example.skydog.module.dto.UserDto;
import com.example.skydog.module.entity.Cart;
import com.example.skydog.module.entity.Collect;
import com.example.skydog.module.entity.User;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.module.vo.UserVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface UserDao {

    void add(User user);

    void delete(Integer userId);

    UserDto queryId(Integer userId);

    void update(User user);

    List<User> queryCondition(User user);

    List<User> queryBySelectActive(UserVO userVO);

    Integer countBySelectActive(UserVO userVO);

    List getUserRecommend(Integer userId);//个性推荐

    List<Integer> getCartCategory(Integer userId);

    List<Integer> getCollectCategory(Integer userId);


//    void batchDelete(List list);

//    List<User> pageQuery();


}