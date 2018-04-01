package com.hbasesoft.actsports.portal.biz.vo;

public class Article extends Content {

	/**
	 * serialVersionUID <br>
	 */
	private static final long serialVersionUID = -3168342559821336195L;

    /** title */
    private String title;

    /** summary */
    private String summary;

    /** content */
    private String content;

    /** column_id */
    private String columnId;

	/** article_sort */
    private Integer articleSort;

    /** PRICE_RANGE */
    private String priceRange;

    private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public Integer getArticleSort() {
		return articleSort;
	}

	public void setArticleSort(Integer articleSort) {
		this.articleSort = articleSort;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
