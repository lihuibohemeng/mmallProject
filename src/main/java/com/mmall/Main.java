package com.mmall;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/8/31
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */
public class Main {

        public static void main(String[] args) {
            // TODO Auto-generated method stub

            System.out.println("开始初始化容器");
            ApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");

            System.out.println("xml加载完毕");
            Person person1 = (Person) ac.getBean("person1");
            System.out.println(person1);

            System.out.println("关闭容器");
            ((ClassPathXmlApplicationContext)ac).close();

        }

    }



