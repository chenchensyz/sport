package com.hbasesoft.actsports.portal.biz.vo;

/**
 * <Description> T_CMS_PAGE_MODULE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年06月20日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
public class PageModule extends AbstractVo {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** page_template_id */
    private String pageTemplateId;

    /** type */
    private String type;

    /** content_id */
    private String contentId;

    /** display_order */
    private Integer displayOrder;

    /** status */
    private String status;

    /** position */
    private String position;
    
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
