package com.example.skydog.service.impl;

import com.example.skydog.dao.CartDao;
import com.example.skydog.dao.CollectDao;
import com.example.skydog.dao.RecommendDao;
import com.example.skydog.dao.UserDao;
import com.example.skydog.enums.ResultEnum;
import com.example.skydog.module.dto.ProductDto;
import com.example.skydog.module.entity.Order;
import com.example.skydog.module.entity.User;
import com.example.skydog.module.vo.PageBeans;
import com.example.skydog.module.vo.ProductVo;
import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.module.vo.UserVO;
import com.example.skydog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private RecommendDao recommendDao;

    @Autowired
    private CollectDao collectDao;


    public void add(User user) {
        userDao.add(user);
    }


    /**
     *
     */
    public ResultVO delete(Integer userId) {
        User u = new User();
        u.setUserId(userId);
        if (userDao.queryCondition(u).isEmpty()) {
            return new ResultVO(ResultEnum.DELETE_FAIL, "用户不存在");
        } else {
            userDao.delete(userId);
            return new ResultVO(ResultEnum.DELETE_SUCCESS);
        }
    }


    public ResultVO update(User user) {
        User u = new User();
        u.setUserId(user.getUserId());
        if (userDao.queryCondition(u).isEmpty()) {
            return new ResultVO(ResultEnum.UPDATE_FAIL, "用户不存在");
        } else {
            userDao.update(user);
            return new ResultVO(ResultEnum.UPDATE_SUCCESS);
        }
    }


    public ResultVO queryId(Integer userId) {
        return new ResultVO(userDao.queryId(userId));
    }


    public ResultVO register(User user) {
        User u = new User();
        String nick = "User " + new Date();
        u.setNick(nick);
        u.setUserName(user.getUserName());

        List<User> list = userDao.queryCondition(u);
        if (list.isEmpty()) {
            u.setPassword(user.getPassword());
            userDao.add(u);
            return new ResultVO(ResultEnum.SUCCESS, "注册成功");
        } else {
            return new ResultVO(ResultEnum.FAIL, "该账号已存在");
        }
    }


    public ResultVO login(User user) {
        User u = new User();
        u.setUserName(user.getUserName());

        List<User> list = userDao.queryCondition(u);
        if (list == null || list.isEmpty()) {
            return new ResultVO(ResultEnum.FAIL, "该账号不存在");
        } else if (list.get(0).getPassword().equals(user.getPassword())) {
            return new ResultVO(list.get(0));
        } else {
            return new ResultVO(ResultEnum.FAIL, "密码错误");
        }
    }

    @Override
    public ResultVO queryCondition(User user) {
        return new ResultVO(userDao.queryCondition(user));
    }


    @Override
    public ResultVO updatePassword(String oldPassword, String newPassword, Integer userId) {
        User u = userDao.queryId(userId);
        if (u == null) {
            return new ResultVO(ResultEnum.UPDATE_FAIL, "该用户不存在");
        } else if (u.getPassword().equals(oldPassword)) {
            u.setPassword(newPassword);
            userDao.update(u);
            return new ResultVO(ResultEnum.UPDATE_SUCCESS);
        } else {
            return new ResultVO(ResultEnum.UPDATE_FAIL, "密码错误");
        }
    }


    public void updateAvatar() {
        // TODO: implement
    }


    public ResultVO getCart(Integer userId) {
        return new ResultVO(ResultEnum.SUCCESS, cartDao.getMyCart(userId));
    }


    public ResultVO getOrder(Integer userId) {
        /**调用*/
        return null;
    }

    @Override
    public ResultVO getCollect(Integer userId) {
        return null;
    }

    @Override
    public ResultVO getEvaluate(Integer uerId) {
        return null;
    }

    /**
     * 分页查询
     *
     * @param userVO
     * @return
     */
    @Override
    public ResultVO getUser(UserVO userVO) {
        PageBeans pageBeans = new PageBeans();
        pageBeans.setCurrentPage(userVO.getCurrentPage());
        pageBeans.setPageSize(userVO.getPageSize());
        pageBeans.setCount(userDao.countBySelectActive(userVO));
        pageBeans.setData(userDao.queryBySelectActive(userVO));
        return new ResultVO(ResultEnum.SUCCESS, pageBeans);
    }

    @Override
    public ResultVO getUserRecommend(Integer userId) {
        //获取用户消费水平
        String consume = userDao.queryId(userId).getConsume();

        //获取用户收藏商品类别
        List<Integer> collectList = userDao.getCollectCategory(userId);
        //获取用户购物车商品类别
        List<Integer> cartList = userDao.getCartCategory(userId);
        collectList.addAll(cartList);

        List<ProductDto> buildRes = new ArrayList();
        for (int i = 0; i < collectList.toArray().length; i++) {
            System.out.println(collectList.get(i));
            ProductVo productVo = new ProductVo();
            productVo.setCategoryId(collectList.get(i));
            productVo.setCurrentPage(1);
            productVo.setPageSize(100);
            buildRes.addAll(recommendDao.hotRecommend(productVo));
        }

        String search = userDao.queryId(userId).getSearch();
        String[] searchList = search.split(",");
        List<ProductDto> searchRes = new ArrayList();
        for (int i = 0; i < searchList.length; i++) {
            System.out.println(searchList[i]);
            ProductVo productVo = new ProductVo();
            productVo.setKeyword(searchList[i]);
            productVo.setCurrentPage(1);
            productVo.setPageSize(30);
            searchRes.addAll(recommendDao.hotRecommend(productVo));
        }

        String browse = userDao.queryId(userId).getBrowse();
        String[] browseList = browse.split(",");
        List<ProductDto> browseRes = new ArrayList();
        for (int i = 0; i < browseList.length; i++) {
            System.out.println(browseList[i]);
            ProductVo productVo = new ProductVo();
            productVo.setCategoryId(Integer.parseInt(browseList[i]));
            productVo.setCurrentPage(1);
            productVo.setPageSize(100);
            browseRes.addAll(recommendDao.hotRecommend(productVo));
        }

        Set<ProductDto> set = new HashSet<ProductDto>();
        set.addAll(buildRes);
        set.addAll(browseRes);
        List<ProductDto> res = new ArrayList<ProductDto>(set);
        res.addAll(searchRes);
        System.out.println(res);

        return new ResultVO(ResultEnum.SUCCESS, res);
    }

}