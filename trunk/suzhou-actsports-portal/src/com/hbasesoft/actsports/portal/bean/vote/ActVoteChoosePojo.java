package com.hbasesoft.actsports.portal.bean.vote;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_VOTE_CHOOSE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2016年12月21日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_VOTE_CHOOSE")
public class ActVoteChoosePojo extends BaseEntity {

	public static final String VOTE_ID = "voteId";
	
	public static final String ID = "id";
	
	public static final int PAGE_SIZE = 8;
	
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

    /** title */
    @Column(name = "title")
    private String title;

    /** choose_img */
    @Column(name = "choose_img")
    private String chooseImg;

    /** vote_id */
    @Column(name = "vote_id")
    private String voteId;

    /** remark */
    @Column(name = "remark")
    private String remark;

    /** seq */
    @Column(name = "seq")
    private Integer seq;

    /** vote_num */
    @Column(name = "vote_num")
    private String voteNum;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChooseImg() {
        return this.chooseImg;
    }

    public void setChooseImg(String chooseImg) {
        this.chooseImg = chooseImg;
    }

    public String getVoteId() {
        return this.voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getVoteNum() {
        return this.voteNum;
    }

    public void setVoteNum(String voteNum) {
        this.voteNum = voteNum;
    }

}
