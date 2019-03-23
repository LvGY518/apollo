package com.clancey.apollo.common.utils.jep;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JEPUtil {

    public static Result evaluateString(String str) {
        Context ctx = Context.enter();
        Scriptable scope = ctx.initStandardObjects();

        Result retResult = new Result();
        retResult.setSucess(true);
        try {
            Object result = ctx.evaluateString(scope, str, null, 0, null);
            retResult.setResult(result.toString());
        } catch (Exception ex) {
            System.out.println(str + "::::" + ex.getMessage());
            retResult.setSucess(false);
            retResult.setResult(str);
        }
        return retResult;
    }

    public static void main(String args[]) {

        /* String str=
         * "Array.prototype.indexOf = function(val) {for (var i = 0; i < this.length; i++) {if (this[i] == val) return i;} return -1;}; Array.prototype.remove = function(val) { var index = this.indexOf(val); if (index > -1) { this.splice(index, 1);}};"
         * ; str+="var array = [100, 200, 300]; array.remove(100);array.remove(300);" ; */

        String str = "(42689.3333333333-25569)*86400-28800";
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
		try {
			System.out.println(new Date(Long.valueOf(nf.format(Double.valueOf(evaluateString(str).getResult()))+"000")));
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println(evaluateString(str));

    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
