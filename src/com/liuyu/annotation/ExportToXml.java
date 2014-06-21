package com.liuyu.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


/**
 * 将具有Exportable Annotation的对象转换为xml格式文本 
 * @author cleverpig
 *
 */
public class ExportToXml {
	
	
	/**
     * 返回对象的成员变量的值（字符串类型）
     * @param field 对象的成员变量
     * @param fieldTypeClass 对象的类型
     * @param obj 对象
     * @return 对象的成员变量的值（字符串类型）
     */
	private String getFieldValue(Field field,Class<?> fieldTypeClass,Object obj){
		String value = null;

		try {
			if(fieldTypeClass == String.class){
				value = (String) field.get(obj);
			}else if(fieldTypeClass == int.class){
				value = Integer.toString(field.getInt(obj));
			}else if(fieldTypeClass == long.class){
				value = Long.toString(field.getLong(obj));
			}else if(fieldTypeClass == short.class){
				value = Short.toString(field.getShort(obj));
			}else if(fieldTypeClass == float.class){
				value = Float.toString(field.getFloat(obj));
			}else if(fieldTypeClass == double.class){
				value = Double.toString(field.getDouble(obj));
			}else if(fieldTypeClass == byte.class){
				value = Byte.toString(field.getByte(obj));
			}else if(fieldTypeClass == char.class){
				value = Character.toString(field.getChar(obj));
			}else if(fieldTypeClass == boolean.class){
				value = Boolean.toString(field.getBoolean(obj));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			value = null;
		}
		
		return value;
	}
	
	/**
     * 输出对象的字段，当对象的字段为Collection或者Map类型时，要调用exportObject方法继续处理
     * @param obj 被处理的对象
     * @throws Exception
     */
	public void exportFields(Object obj) throws Exception{
		
		Exportable exportable = obj.getClass().getAnnotation(Exportable.class);
		if(exportable != null){
			if(exportable.value().length() > 0){
				System.out.println("Class annotation Name: " + exportable.value());
			}else{
				System.out.println("Class annotation Name: " + exportable.name());
			}
		}else{
			System.out.println("类不是使用Exportable标注过的");
		}
		
		//获取对象中的所有成员变量
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for(Field field : fields){
			//获取成员变量的注解
			Persistent fieldAnnotation = field.getAnnotation(Persistent.class);
			if(fieldAnnotation == null){
				continue;
			}
			//重要：避免java虚拟机检查对私有成员变量的访问权限
			field.setAccessible(true);
			Class<?> fieldTypeClass = field.getType();
			String name = field.getName();
			String value = this.getFieldValue(field, fieldTypeClass, obj);
			
			//如果获得成员变量的值，则输出
			if(value != null){
				System.out.println(getIndent()+"<"+name+">\n"
                         +getIndent()+"\t"+value+"\n"+getIndent()+"</"+name+">");
			}
			//处理成员变量中类型为Collection或Map
            else if ((field.get(obj) instanceof Collection)||
                            (field.get(obj) instanceof Map)){
                    exportObject(field.get(obj));
            }
            else{
                    exportObject(field.get(obj));
            }
			
		}
		
	}
	
	
	//缩进深度
    int levelDepth=0;
    //防止循环引用的检查者，循环引用现象如：a包含b，而b又包含a
    Collection<Object> cyclicChecker=new ArrayList<Object>();
    
    /**
     * 返回缩进字符串
     * @return
     */
    private String getIndent(){
            String s="";
            for(int i=0;i<levelDepth;i++){
                    s+="\t";
            }
            return s;
    }
    
    
    /**
     * 输出对象，如果对象类型为Collection和Map类型，则需要递归调用exportObject进行处理
     * @param obj
     * @throws Exception
     */
    public void exportObject(Object obj) throws Exception{
            Exportable exportable=null;
            String elementName=null;
            
            //循环引用现象处理
            if (cyclicChecker.contains(obj)){
                    return;
            }
            
            cyclicChecker.add(obj);
            
            //首先处理Collection和Map类型
            if (obj instanceof Collection){
                    for(Iterator i=((Collection)obj).iterator();i.hasNext();){
                            exportObject(i.next());
                    }
            }
            else if (obj instanceof Map){
                    for(Iterator i=((Map)obj).keySet().iterator();i.hasNext();){
                            exportObject(i.next());
                    }
            }
            else{

                    exportable=obj.getClass().getAnnotation(Exportable.class);
                    //如果obj已经被Exportable Annotation修饰过了（注意annotation是具有继承性的），
                    //则使用其name作为输出xml的元素name
                    if (exportable!=null){
                            if (exportable.value().length()>0){
                                    elementName=exportable.value();
                            }
                            else{
                                    elementName=exportable.name();
                            }
                    }
                    //未被修饰或者Exportable Annotation的值为空字符串，
                    //则使用类名作为输出xml的元素name
                    if (exportable==null||elementName.length()==0){
                            elementName=obj.getClass().getSimpleName();
                    }
                    //输出xml元素头
                    System.out.println(getIndent()+"<"+elementName+">");
                    levelDepth++;
                    //如果没有被修饰，则直接输出其toString()作为元素值
                    if (exportable==null){
                            System.out.println(getIndent()+obj.toString());
                    }
                    //否则将对象的成员变量导出为xml
                    else{
                            exportFields(obj);
                    }
                    levelDepth--;
                    //输出xml元素结尾
                    System.out.println(getIndent()+"</"+elementName+">");
                    
            }
            cyclicChecker.remove(obj);
    }
	
    
    
    public static void main(String[] argv){
        try{
                AddressForTest ad=new AddressForTest("China","Beijing",
                                "Beijing","winnerStreet","10");
                
                ExportToXml test=new ExportToXml();
                
                ArrayList<String> telephoneList=new ArrayList<String>();
                telephoneList.add("66608888");
                telephoneList.add("66608889");
                
                ArrayList<AddressForTest> adList=new ArrayList<AddressForTest>();
                adList.add(ad);
                
                AddressListForTest adl=new AddressListForTest("coolBoy",
                                18,telephoneList,adList,"some words");
                
                test.exportObject(adl);
        }
        catch(Exception ex){
                ex.printStackTrace();
        }
    }
    
    
    
}

/**
 * 	在ExportToXml类之前的类比较简单，这里必须说明一下ExportToXml类：此类的核心函数是exportObject和exportFields方法，
 * 前者输出对象的xml信息，后者输出对象成员变量的信息。由于对象类型和成员类型的多样性，所以采取了以下的逻辑：

	在exportObject方法中，当对象类型为Collection和Map类型时，则需要递归调用exportObject进行处理；
	而如果对象类型不是Collection和Map类型的话，将判断对象类是否被Exportable annotation修饰过：
	如果没有被修饰，则直接输出<对象类名>对象.toString()</对象类名>作为xml绑定结果的一部分；
	如果被修饰过，则需要调用exportFields方法对对象的成员变量进行xml绑定。

	在exportFields方法中，首先取出对象的所有成员，然后获得被Persisitent annotation修饰的成员。
	在其后的一句：field.setAccessible(true)是很重要的，因为bean类定义中的成员访问修饰都是private，
	所以为了避免java虚拟机检查对私有成员的访问权限，加上这一句是必需的。接着后面的语句便是输出<成员名>成员值</成员名>这样的xml结构。
	像在exportObject方法中一般，仍然需要判断成员类型是否为Collection和Map类型，如果为上述两种类型之一，
	则要在exportFields中再次调用exportObject来处理这个成员。

	在main方法中，本人编写了一段演示代码：建立了一个由单个友人地址类（AddressForTest）组成的ArrayList作为
	通讯录类（AddressForTest）的成员的通讯录对象，并且输出这个对象的xml绑定，运行结果如下：
 * <addresslist>
        <friendName>
                coolBoy
        </friendName>
        <age>
                18
        </age>
        <String>
                66608888
        </String>
        <String>
                66608889
        </String>
        <address>
                <country>
                        China
                </country>
                <province>
                        Beijing
                </province>
                <city>
                        Beijing
                </city>
                <street>
                        winnerStreet
                </street>
                <doorplate>
                        10
                </doorplate>
        </address>
        <note>
                some words
        </note>
	</addresslist>
 
 * 
 */

