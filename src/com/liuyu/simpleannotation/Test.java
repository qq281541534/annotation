package com.liuyu.simpleannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
 *  
 * @Retention(RetentionPolicy.RUNTIME)这个meta-annotation表示了此类型的 annotation将被虚拟机保留使其能够在运行时通过反射被读取。
 * 而@Target(ElementType.METHOD)表示此类型的 annotation只能用于修饰方法声明
 * @Inherited 如果一个使用了@Inherited修饰的annotation类型被用于一个class， 则这个annotation将被用于该class的子类
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {

}


