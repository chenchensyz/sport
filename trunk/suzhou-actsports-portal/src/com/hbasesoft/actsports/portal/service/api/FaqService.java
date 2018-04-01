/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.api;

import java.util.List;

import com.hbasesoft.actsports.portal.biz.vo.Ad;
import com.hbasesoft.actsports.portal.biz.vo.Article;
import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinArticle;
import com.hbasesoft.actsports.portal.biz.vo.Faq;
import com.hbasesoft.actsports.portal.biz.vo.Menu;
import com.hbasesoft.actsports.portal.biz.vo.NewsItem;
import com.hbasesoft.actsports.portal.biz.vo.PageTemplate;
import com.hbasesoft.actsports.portal.biz.vo.WeixinReleasePojo;
import com.hbasesoft.framework.common.ServiceException;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年9月4日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.api.biz <br>
 */
public interface FaqService {
//    List<Faq> queryFaq(String orgId, int pageIndex, int pageSize) throws Exception;
    
    List<Faq> queryFaqByCode(String orgId, String code, int pageIndex, int pageSize) throws Exception;
    
    NewsItem queryArticleById(String id) throws Exception;
    
    List<NewsItem> queryAritcleList(String orgId, String categoryCode, int pageIndex, int pageSize) throws Exception;
    
    Menu queryMenuByCode(String categoryCode) throws Exception;
    
    Menu queryMenuById(String id) throws Exception;
    
    PageTemplate getPageTemplate(String templateCode) throws Exception;
    
    Menu queryMenu(String contentId, String orgCode, int pageIndex,
    		int pageSize, String serverPath, String imagePath) throws Exception;
    
    Ad queryAd(String contentId, String orgCode) throws Exception;
    
    NewsItem queryArticleByReleaseId(String id) throws Exception;
    
    Article queryCmsArticleById(String id) throws Exception;
}
