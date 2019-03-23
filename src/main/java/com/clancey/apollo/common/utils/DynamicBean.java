package com.clancey.apollo.common.utils;


import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DynamicBean {

    private  Object object = null;//动态生成的类
    private BeanMap beanMap = null;//存放属性名称以及属性的类型

    public DynamicBean() {
        super();
    }

    @SuppressWarnings("rawtypes")
    public DynamicBean(Map propertyMap, Class clasz) {
      this.object = generateBean(propertyMap,clasz);
      this.beanMap = BeanMap.create(this.object);
    }

    @SuppressWarnings("rawtypes")
    public DynamicBean(Map propertyMap) {
      this.object = generateBean(propertyMap);
      this.beanMap = BeanMap.create(this.object);
    }

    /**
      * 给bean属性赋值
      * @param property 属性名
      * @param value 值
      */
    public void setValue(Object property, Object value) {
      beanMap.put(property, value);
    }

    /**
      * 通过属性名得到属性值
      * @param property 属性名
      * @return 值
      */
    public Object getValue(String property) {
      return beanMap.get(property);
    }

    /**
      * 得到该实体bean对象
      * @return
      */
    public Object getObject() {
      return this.object;
    }

    /**
     * @param propertyMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Object generateBean(Map propertyMap,Class clasz) {
      BeanGenerator generator = new BeanGenerator();
      generator.setSuperclass(clasz);
      Set keySet = propertyMap.keySet();
      for (Iterator i = keySet.iterator(); i.hasNext();) {
       String key = (String) i.next();
       generator.addProperty(key, (Class) propertyMap.get(key));
      }
      return generator.create();
    }

    @SuppressWarnings("rawtypes")
    private Object generateBean(Map propertyMap) {
      BeanGenerator generator = new BeanGenerator();
      Set keySet = propertyMap.keySet();
      for (Iterator i = keySet.iterator(); i.hasNext();) {
       String key = (String) i.next();
       generator.addProperty(key, (Class) propertyMap.get(key));
      }
      return generator.create();
    }

//    public static void main(String[] args) throws Exception {
//    	 HashMap<String, Object> typeMap=new HashMap<String, Object>();
//    	 HashMap<String, Object> dataMap=new HashMap<String, Object>();
//
//    	 typeMap.put("clientName", Class.forName("java.lang.String"));
//    	 typeMap.put("totalQuantity",Class.forName("java.lang.Integer"));
//
//    	 dataMap.put("clientName","你好");
//    	 dataMap.put("totalQuantity", 19);
//    	 DynamicBean bean = new DynamicBean(typeMap,Order.class);
//    	 Set keys=typeMap.keySet();
//         for(Iterator it=keys.iterator();it.hasNext();){
//             String key = (String) it.next();
//             bean.setValue(key,dataMap.get(key));
//         }
//         Order obj= (Order) bean.getObject();
//       // System.out.println(obj.getCode());
//         System.out.println(bean.getValue("code"));
//         System.out.println(bean.getValue("totalQuantity"));
//	}

}
