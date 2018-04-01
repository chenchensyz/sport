package com.hbasesoft.manager.bean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 微发布
 * @author onlineGenerator
 * @date 2017-07-26 14:16:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weixin_release", schema = "")
@SuppressWarnings("serial")
public class WeixinReleaseEntity implements java.io.Serializable {
    
    public static final String AD_MENU_NAME = "wgg";
    
    public static final String MENU_NAME = "wlm";
    
    public static final String AD_POSITION = "1";
    
    public static final String MENU_POSITION = "2";
    
	/**id*/
	private java.lang.String id;
	/**所属栏目*/
	@Excel(name="所属栏目")
	private java.lang.String fatherId;
	/**图文*/
	@Excel(name="图文")
	private java.lang.String newsId;
	/**顺序*/
	@Excel(name="顺序")
	private java.lang.String orders;
	/**位置*/
	private java.lang.String position;
	
	private java.lang.String code;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属栏目
	 */
	@Column(name ="FATHER_ID",nullable=true,length=128)
	public java.lang.String getFatherId(){
		return this.fatherId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属栏目
	 */
	public void setFatherId(java.lang.String fatherId){
		this.fatherId = fatherId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图文
	 */
	@Column(name ="NEWS_ID",nullable=true,length=128)
	public java.lang.String getNewsId(){
		return this.newsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图文
	 */
	public void setNewsId(java.lang.String newsId){
		this.newsId = newsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  顺序
	 */
	@Column(name ="ORDERS",nullable=true,length=32)
	public java.lang.String getOrders(){
		return this.orders;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  顺序
	 */
	public void setOrders(java.lang.String orders){
		this.orders = orders;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  位置
	 */
	@Column(name ="POSITION",nullable=true,length=32)
	public java.lang.String getPosition(){
		return this.position;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  位置
	 */
	public void setPosition(java.lang.String position){
		this.position = position;
	}

	@Column(name ="CODE",nullable=true,length=32)
    public java.lang.String getCode() {
        return code;
    }

    public void setCode(java.lang.String code) {
        this.code = code;
    }
	
	
}
