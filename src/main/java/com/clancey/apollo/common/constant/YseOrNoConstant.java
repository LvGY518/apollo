package com.clancey.apollo.common.constant;

import lombok.Getter;

/**
 * @author lvgaungyao
 * @create 18-11-20
 */
@Getter
public enum YseOrNoConstant {
   YES("1", "是"),
    NO("0", "否");

   private String value;
   private String description;

   private YseOrNoConstant(String value, String description){
       this.description = description;
       this.value = value;
   }

}
