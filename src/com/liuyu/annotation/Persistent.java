package com.liuyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
 *  
 * @Description: 
 * @author ly   
 * @date 2014-6-8 上午1:05:40 
 *    
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Persistent {
	String value() default "";
}


