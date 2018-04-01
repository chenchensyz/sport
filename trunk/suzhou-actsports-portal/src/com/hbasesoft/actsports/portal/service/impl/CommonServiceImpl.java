/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.actsports.portal.service.CommonService;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.db.core.BaseEntity;
import com.hbasesoft.framework.db.core.DaoException;
import com.hbasesoft.framework.db.hibernate.BaseHibernateDao;
import com.hbasesoft.framework.db.hibernate.IGenericBaseDao;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月12日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.common <br>
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entity
     * @return
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends BaseEntity> Serializable save(T entity) throws ServiceException {
        try {
            return getGenericBaseDao().save(entity);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entity
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends BaseEntity> void delete(T entity) throws ServiceException {
        try {
            getGenericBaseDao().delete(entity);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entitys
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends BaseEntity> void batchSave(List<T> entitys) throws ServiceException {
        try {
            getGenericBaseDao().batchSave(entitys);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param class1
     * @param id
     * @return
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(readOnly = true)
    public <T extends BaseEntity> T get(Class<T> class1, Serializable id) throws ServiceException {
        try {
            return getGenericBaseDao().get(class1, id);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entityName
     * @param id
     * @return
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(readOnly = true)
    public <T extends BaseEntity> T getEntity(Class<T> entityName, Serializable id) throws ServiceException {
        try {
            return getGenericBaseDao().getEntity(entityName, id);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entityClass
     * @param propertyName
     * @param value
     * @return
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(readOnly = true)
    public <T extends BaseEntity> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value)
        throws ServiceException {
        try {
            return getGenericBaseDao().findUniqueByProperty(entityClass, propertyName, value);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entityClass
     * @param propertyName
     * @param value
     * @return
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(readOnly = true)
    public <T extends BaseEntity> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value)
        throws ServiceException {
        try {
            return getGenericBaseDao().findByProperty(entityClass, propertyName, value);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entityClass
     * @return
     * @throws ServiceException <br>
     */
    @Override
    public <T extends BaseEntity> List<T> loadAll(Class<T> entityClass) throws ServiceException {
        try {
            return getGenericBaseDao().loadAll(entityClass);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entityName
     * @param id
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends BaseEntity> void deleteEntityById(Class<T> entityName, Serializable id) throws ServiceException {
        try {
            getGenericBaseDao().deleteEntityById(entityName, id);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entities
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends BaseEntity> void deleteAllEntitie(Collection<T> entities) throws ServiceException {
        try {
            getGenericBaseDao().deleteAllEntitie(entities);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entityName
     * @param ids
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends BaseEntity> void deleteAllEntitiesByIds(Class<T> entityName, Collection<String> ids)
        throws ServiceException {
        try {
            getGenericBaseDao().deleteAllEntitiesByIds(entityName, ids);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param pojo
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends BaseEntity> void updateEntity(T pojo) throws ServiceException {
        try {
            getGenericBaseDao().updateEntity(pojo);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param entityClass
     * @param propertyName
     * @param value
     * @param isAsc
     * @return
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(readOnly = true)
    public <T extends BaseEntity> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value,
        boolean isAsc) throws ServiceException {
        try {
            return getGenericBaseDao().findByPropertyisOrder(entityClass, propertyName, value, isAsc);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return <br>
     */
    @Override
    public IGenericBaseDao getGenericBaseDao() {
        return applicationContext.getBean(BaseHibernateDao.class);
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param applicationContext
     * @throws BeansException <br>
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param detachedCriteria
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws DaoException <br>
     */
    @Override
    @Transactional(readOnly = true)
    public <T> List<T> getPageList(DetachedCriteria detachedCriteria, int pageIndex, int pageSize)
        throws ServiceException {
        try {
            return getGenericBaseDao().getPageList(detachedCriteria, pageIndex, pageSize);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param detachedCriteria
     * @return
     * @throws DaoException <br>
     */
    @Override
    @Transactional(readOnly = true)
    public <T> List<T> getListByCriteriaQuery(DetachedCriteria detachedCriteria) throws ServiceException {
        try {
            return getGenericBaseDao().getListByCriteriaQuery(detachedCriteria);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param detachedCriteria
     * @return
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(readOnly = true)
    public <T> T getCriteriaQuery(DetachedCriteria detachedCriteria) throws ServiceException {
        try {
            return getGenericBaseDao().getCriteriaQuery(detachedCriteria);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
