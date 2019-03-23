package com.clancey.apollo.sys.controller;

import com.clancey.apollo.common.RedisService;
import com.clancey.apollo.common.base.BaseOperationController;
import com.clancey.apollo.common.constant.RedisConstant;
import com.clancey.apollo.common.vo.CommonResponse;
import com.clancey.apollo.sys.entity.User;
import com.clancey.apollo.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author ChenShuai
 * @date 2018/9/13 16:37
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseOperationController<UserService, User> {
    @Autowired
    RedisService redisService;

    /**
     * 更新用户
     *
     * @param entity
     * @return
     */
    @Override
    public CommonResponse update(@RequestBody User entity) {
        User user = baseService.selectById(entity.getId());
        String userName = user.getUsername();
        baseService.updateSelectiveById(entity);
        redisService.remove(RedisConstant.USER_PREFIX + userName);
        return success();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Override
    public CommonResponse remove(String id) {
        User user = baseService.selectById(id);
        user.setDeleted(true);
        baseService.updateSelectiveById(user);
        redisService.remove(RedisConstant.USER_PREFIX + user.getUsername());
        return success();
    }

    /**
     * 根据用户ID与的字段值 更新用户字段
     *
     * @param params
     * @return
     */
    @PostMapping("/updateUser")
    public CommonResponse updateUser(@RequestBody Map<String, String> params) throws IllegalAccessException {
        if (params.get("id") == null || StringUtils.isEmpty(params.get("id").toString())) {
            return CommonResponse.fail("000001", "id不存在");
        }
        User user = baseService.selectById(params.get("id").toString());
        if (user != null) {
            params.remove("id");
            Class<?> userClass = user.getClass();
            Field[] fields = userClass.getDeclaredFields();
            for (Field f : fields) {
                if (params.get(f.getName()) != null) {
                    f.setAccessible(true);
                    if (f.getType().toString().endsWith("String")) {
                        f.set(user, params.get(f.getName()).toString());
                    }
                    if (f.getType().toString().endsWith("boolean")) {
                        f.set(user, Boolean.valueOf(params.get(f.getName()).toString()));
                    }
                    if (f.getType().toString().endsWith("Integer")) {
                        f.set(user, Integer.valueOf(params.get(f.getName()).toString()));
                    }
                    if (f.getType().toString().endsWith("BigDecimal")) {
                        f.set(user, new BigDecimal(params.get(f.getName()).toString()));
                    }
                }
            }
            baseService.updateSelectiveById(user);
            redisService.set(RedisConstant.USER_PREFIX + user.getUsername(), user);
            return success();
        }
        return CommonResponse.fail("000002", "用户不存在");
    }
}
