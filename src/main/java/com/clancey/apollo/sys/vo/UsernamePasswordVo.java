package com.clancey.apollo.sys.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author ChenShuai
 * @date 2018/9/19 18:56
 */
@Getter
@Setter
public class UsernamePasswordVo {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
