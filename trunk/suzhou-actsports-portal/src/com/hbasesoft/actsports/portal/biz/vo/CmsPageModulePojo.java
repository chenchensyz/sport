package com.hbasesoft.actsports.portal.biz.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_CMS_PAGE_MODULE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年06月20日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_CMS_PAGE_MODULE")
public class CmsPageModulePojo extends BaseEntity {

	public static final String STATUS = "status";
	
	public static final String PAGE_TEMPLATE_ID = "pageTemplateId";
	
	public static final String DISPLAY_ORDER = "displayOrder";
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /** page_template_id */
    @Column(name = "page_template_id")
    private String pageTemplateId;

    /** type */
    @Column(name = "type")
    private String type;

    /** content_id */
    @Column(name = "content_id")
    private String contentId;

    /** display_order */
    @Column(name = "display_order")
    private Integer displayOrder;

    /** status */
    @Column(name = "status")
    private String status;

    /** position */
    @Column(name = "position")
    private String position;

    /** name */
    @Column(name = "name")
    private String name;
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPageTemplateId() {
        return this.pageTemplateId;
    }

    public void setPageTemplateId(String pageTemplateId) {
        this.pageTemplateId = pageTemplateId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentId() {
        return this.contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
