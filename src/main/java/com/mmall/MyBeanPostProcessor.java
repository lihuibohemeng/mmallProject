package com.mmall;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/8/31
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

        public Object postProcessBeforeInitialization(Object bean,
                                                      String beanName) throws BeansException {
            // TODO Auto-generated method stub

            System.out.println("postProcessBeforeInitialization被调用");
            return bean;
        }

        public Object postProcessAfterInitialization(Object bean,
                                                     String beanName) throws BeansException {
            // TODO Auto-generated method stub
            System.out.println("postProcessAfterInitialization被调用");
            return bean;
        }

    }



