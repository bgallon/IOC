package com.fishpan.ioc.api;

import android.app.Activity;

/**
 * Created by yupan on 16/7/23.
 */
public class IOC{
    public static void inject(Activity activity){
        inject(activity , activity);
    }
    public static void inject(Object host , Object root){
        Class<?> clazz = host.getClass();
        String proxyClassFullName = clazz.getName()+"$$ViewInjector";
        //省略try,catch相关代码
        Class<?> proxyClazz = null;
        try {
            proxyClazz = Class.forName(proxyClassFullName);
            ViewInjector viewInjector = (ViewInjector) proxyClazz.newInstance();
            viewInjector.inject(host,root);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}