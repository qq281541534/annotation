package com.liuyu.annotation;




/**
 * 用于测试的地址类
 * @author cleverpig
 *
 */
@Exportable("address")
public class AddressForTest {
	
	//国家
	@Persistent
	private String country = null;
	
	//省级
	@Persistent
	private String province = null;
	
	//城市
    @Persistent
    private String city=null;
    
    //街道
    @Persistent
    private String street=null;

    //门牌
    @Persistent
    private String doorplate=null;
    
    public AddressForTest(String country,String province,
                    String city,String street,String doorplate){
            this.country=country;
            this.province=province;
            this.city=city;
            this.street=street;
            this.doorplate=doorplate;
    }
}


