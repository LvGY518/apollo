package com.clancey.apollo.common.base;

import com.clancey.apollo.common.utils.Query;
import com.clancey.apollo.common.utils.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author chens
 * @date 2018/11/28 11:25
 */
public abstract class BaseRestSelectController<Service extends BaseService, Entity extends BaseEntity> extends BaseController<Service, Entity> {
    @ApiIgnore
    @ResponseBody
    @GetMapping("/list")
    public ResponseEntity list(@RequestParam Map<String, Object> params) {
        if (params.size() > 0) {
            return ResponseEntity.ok((baseService.selectList(params)));
        }
        return ResponseEntity.ok(baseService.selectList());
    }

    @ApiIgnore
    @ResponseBody
    @GetMapping("/get/{id}")
    public ResponseEntity get(@PathVariable String id) {
        BaseEntity baseEntity = baseService.selectById(id);
        if (baseEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(baseEntity);
    }

    @ApiIgnore
    @ResponseBody
    @GetMapping("/page")
    public ResponseEntity page(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        return ResponseEntity.ok(baseService.selectByQuery(query));
    }
}
