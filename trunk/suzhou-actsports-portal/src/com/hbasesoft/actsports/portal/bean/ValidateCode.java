package com.hbasesoft.actsports.portal.bean;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * 校验码Bean
 * 
 * @author jiangtianyang
 */
public class ValidateCode extends BaseEntity {

    /** 保存5分钟 */
    public static final long MAX_TIMES = 5 * 60 * 1000;

    /** 最小发送间隔 */
    public static final long MIN_TIMES = 60 * 1000;

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -8176062935233056801L;

    private String code;

    private long createTime = System.currentTimeMillis();

    private long lastSendTime = 0;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(long lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    /**
     * Description: 是否可用 <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return <br>
     */
    public boolean isAvalible() {
        return System.currentTimeMillis() - MAX_TIMES <= createTime;
    }

    /**
     * Description: 是否可以发送短信<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return <br>
     */
    public boolean canSend() {
        return System.currentTimeMillis() - MIN_TIMES >= lastSendTime;
    }

}