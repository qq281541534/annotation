package com.liuyu.simpleannotation;

import java.lang.reflect.Method;

/**   
 *  
 * @Description: 
 * @author ly   
 * @date 2014-6-8 下午2:00:03 
 *    
 */
public class RunTests {
	
	public static void main(String[] args) throws Exception {
        int passed = 0, failed = 0;
        //从命令行中读取输出的类，并获取该类对应的所有方法，遍历没个方法
        for (Method m : Class.forName(args[0]).getMethods()) {
        	//判断方法是否为Test注解
           if (m.isAnnotationPresent(Test.class)) {
              try {
                 m.invoke(null);
                 passed++;
              } catch (Throwable ex) {
                 System.out.printf("Test %s failed: %s %n", m, ex.getCause());
                 failed++;
              }
           }
        }
        System.out.printf("Passed: %d, Failed %d%n", passed, failed);
     }
}


