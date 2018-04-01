package com.hbasesoft.manager.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hbasesoft.framework.manager.core.common.service.impl.CommonServiceImpl;
import com.hbasesoft.manager.service.WeixinUserAccountService;
import com.hbasesoft.manager.vo.WeixinAccount;
import com.hbasesoft.manager.vo.WeixinUserAccount;





@Service("weixinUserAccountService")
@Transactional
public class WeixinUserAccountServiceImpl extends CommonServiceImpl implements WeixinUserAccountService {

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate template;

	@Override
	public void deleteByAccount(String account) {
		template.bulkUpdate("delete WeixinUserAccount where name=?", new Object[] { account });

	}

	@Override
	public WeixinUserAccount findByAccount(String account) {
		String hql = "from WeixinUserAccount where account=?";
		List<WeixinUserAccount> list = (List<WeixinUserAccount>) template.find(hql, account);
		if (list.size() > 0) {
			for (int i = 0; i < list.size();) {
				return list.get(0);
			}
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeixinUserAccount> findUserAccountId(String accountId) {
		String hql = "from WeixinUserAccount where accountId =?";
		return (List<WeixinUserAccount>) template.find(hql,accountId);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WeixinUserAccount> findUserAccountByPage(final int start, final int pageSize, String groupId) {
		final String hql = "from WeixinUserAccount";
		List<WeixinUserAccount> list=
				(List<WeixinUserAccount>) template.execute(new HibernateCallback<WeixinUserAccount>() {
            @Override
			public WeixinUserAccount doInHibernate(Session session) throws HibernateException {
    			Query query=session.createQuery(hql);
    			//绑定 查询参数
//    			query.setString(0, userId);
    			//绑定分页参数:
    			query.setFirstResult(start);
    			query.setMaxResults(pageSize);
    			//query.list() 执行查询
    			//返回查询结果
    			return (WeixinUserAccount) query.list();
			}
		});
				
		return list;
	}

	@Override
	public List<WeixinUserAccount> findNicknameLike(String weixinId, String nickName) {
		String hql = "from WeixinUserAccount where accountId=? and nickName like ?";
		return (List<WeixinUserAccount>) template.find(hql, new Object[]{weixinId,"%"+nickName+"%"});
	}

}