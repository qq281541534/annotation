package com.liuyu.annotation;

import java.util.ArrayList;

/**
 * 友人通讯录
 * 包含：姓名、年龄、电话、住址（多个）、备注
 * @author cleverpig
 *
 */
@Exportable(name="addresslist",description="address list")
public class AddressListForTest {
	//友人姓名
    @Persistent
    private String friendName=null;
    
    //友人年龄
    @Persistent
    private int age=0;
    
    //友人电话
    @Persistent
    private ArrayList<String> telephone=null;
    
    //友人住址：家庭、单位
    @Persistent
    private ArrayList<AddressForTest> AddressForText=null;
    
    //备注
    @Persistent
    private String note=null;
    
    public AddressListForTest(String name,int age,
                    ArrayList<String> telephoneList, 
                    ArrayList<AddressForTest> addressList,
                    String note){
            this.friendName=name;
            this.age=age;
            this.telephone=new ArrayList<String>(telephoneList);
            this.AddressForText=new ArrayList<AddressForTest>(addressList);
            this.note=note;
            
    }
}


