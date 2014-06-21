package com.liuyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 用于修饰类的类型的annotation
 * @author cleverpig
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Exportable {
	
	//名称、
	String name() default "";
	//描述
	String description() default "";
	//省略name和description后，用来保存name值
	String value() default "";
}


