/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.db.core.BaseEntity;
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
public interface CommonService {

    <T extends BaseEntity> Serializable save(T entity) throws ServiceException;

    <T extends BaseEntity> void delete(T entity) throws ServiceException;

    <T extends BaseEntity> void batchSave(List<T> entitys) throws ServiceException;

    /**
     * 根据实体名称和主键获取实体
     * 
     * @param <T extends BaseEntity>
     * @param entityName
     * @param id
     * @return
     */
    <T extends BaseEntity> T get(Class<T> class1, Serializable id) throws ServiceException;

    /**
     * 根据实体名称和主键获取实体
     * 
     * @param <T extends BaseEntity>
     * @param entityName
     * @param id
     * @return
     */
    <T extends BaseEntity> T getEntity(Class<T> entityName, Serializable id) throws ServiceException;

    /**
     * 根据实体名称和字段名称和字段值获取唯一记录
     * 
     * @param <T extends BaseEntity>
     * @param entityClass
     * @param propertyName
     * @param value
     * @return
     */
    <T extends BaseEntity> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value)
        throws ServiceException;

    /**
     * 按属性查找对象列表.
     */
    <T extends BaseEntity> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value)
        throws ServiceException;

    /**
     * 加载全部实体
     * 
     * @param <T extends BaseEntity>
     * @param entityClass
     * @return
     */
    <T extends BaseEntity> List<T> loadAll(final Class<T> entityClass) throws ServiceException;

    /**
     * cq方式分页
     * 
     * @param cq
     * @param isOffset
     * @return
     */
    <T> List<T> getPageList(DetachedCriteria detachedCriteria, int pageIndex, int pageSize)
        throws ServiceException;

    /**
     * 通过cq获取全部实体
     * 
     * @param <T extends BaseEntity>
     * @param cq
     * @return
     */
    <T> List<T> getListByCriteriaQuery(DetachedCriteria detachedCriteria) throws ServiceException;

    <T> T getCriteriaQuery(DetachedCriteria detachedCriteria) throws ServiceException;

    /**
     * 删除实体主键删除
     * 
     * @param <T extends BaseEntity>
     * @param entities
     */
    <T extends BaseEntity> void deleteEntityById(Class<T> entityName, Serializable id) throws ServiceException;

    /**
     * 删除实体集合
     * 
     * @param <T extends BaseEntity>
     * @param entities
     */
    <T extends BaseEntity> void deleteAllEntitie(Collection<T> entities) throws ServiceException;

    /**
     * Description: 根据Id集合删除实体<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param ids <br>
     */
    <T extends BaseEntity> void deleteAllEntitiesByIds(Class<T> entityName, Collection<String> ids)
        throws ServiceException;

    /**
     * 更新指定的实体
     * 
     * @param <T extends BaseEntity>
     * @param pojo
     */
    <T extends BaseEntity> void updateEntity(T pojo) throws ServiceException;

    /**
     * 通过属性称获取实体带排序
     * 
     * @param <T extends BaseEntity>
     * @param clas
     * @return
     */
    <T extends BaseEntity> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value,
        boolean isAsc) throws ServiceException;

    IGenericBaseDao getGenericBaseDao();
}
