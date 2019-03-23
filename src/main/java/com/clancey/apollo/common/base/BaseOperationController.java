package com.clancey.apollo.common.base;

import com.clancey.apollo.common.vo.CommonResponse;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author ChenShuai
 * @date 2018/8/31 10:48
 */
public abstract class BaseOperationController<Service extends BaseService, Entity extends BaseEntity> extends BaseSelectController<Service, Entity> {
    @ApiIgnore
    @ResponseBody
    @PostMapping("/add")
    public CommonResponse add(@RequestBody Entity entity) {
        baseService.insertSelective(entity);
        return success();
    }

    @ApiIgnore
    @ResponseBody
    @PutMapping("/put")
    public CommonResponse update(@RequestBody Entity entity) {
        baseService.updateSelectiveById(entity);
        return success();
    }

    @ApiIgnore
    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public CommonResponse remove(@PathVariable String id) {
        BaseEntity entity = baseService.selectById(id);
        entity.setDeleted(true);
        baseService.updateSelectiveById(entity);
        return success();
    }
}
