package com.fishpan.ioc.api;

/**
 * Created by yupan on 16/7/23.
 */
public interface ViewInjector<T> {
    void inject(T t , Object object);
}
