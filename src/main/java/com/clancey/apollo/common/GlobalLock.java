package com.clancey.apollo.common;

/**
 * @Author: zzy65
 * @Date: 2018/11/8 15:20
 * @Version 1.0
 * @Description
 */
public class GlobalLock {

    private static  GlobalLock GLOBAL_LCOK;

    private GlobalLock(){

    }

    public static GlobalLock getLock(){
        if(GLOBAL_LCOK==null){
            synchronized (GlobalLock.class){
                if(GLOBAL_LCOK==null){
                    GLOBAL_LCOK = new GlobalLock();
                }
            }
        }
        return GLOBAL_LCOK;
    }

}
