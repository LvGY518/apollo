package com.clancey.apollo.common.base;

import com.clancey.apollo.common.utils.Query;
import com.clancey.apollo.common.vo.CommonResponse;
import com.clancey.apollo.common.utils.Query;
import com.clancey.apollo.common.vo.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author ChenShuai
 * @date 2018/8/31 10:48
 */
public abstract class BaseSelectController<Service extends BaseService, Entity extends BaseEntity> extends BaseController<Service, Entity> {
    @ApiIgnore
    @ResponseBody
    @GetMapping("/list")
    public CommonResponse list(@RequestParam Map<String, Object> params) {
        if (params.size() > 0) {
            return success(baseService.selectList(params));
        }
        return success(baseService.selectList());
    }

    @ApiIgnore
    @ResponseBody
    @GetMapping("/get/{id}")
    public CommonResponse get(@PathVariable String id) {
        BaseEntity baseEntity = baseService.selectById(id);
        if (baseEntity == null) {
            return fail("000001", "没有找到指定数据");
        }
        return success(baseEntity);
    }

    @ApiIgnore
    @ResponseBody
    @GetMapping("/page")
    public CommonResponse page(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        return success(baseService.selectByQuery(query));
    }
}
