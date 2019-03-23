package com.clancey.apollo.common.utils;

import com.clancey.apollo.common.base.BaseEntity;

import java.util.Date;

/**
 * 实体类相关工具类 解决问题： 1、快速对实体的常驻字段，如：crtUser、crtHost、updUser等值快速注入
 */
public class EntityUtil {
	public static <T extends BaseEntity> void setCreateInfoIfNull(T entity) {
		Date now = new Date();
		if (entity.getCreateDate() == null) {
			entity.setCreateDate(now);
		}
		if (entity.getUpdateDate() == null) {
			entity.setUpdateDate(now);
		}
		if (StringUtil.isNullOrEmpty(entity.getCreateUser())) {
			System.out.println("before set, " + entity.getCreateUser());
			entity.setCreateUser(UserUtil.currentUserId());
			System.out.println("after set, " + entity.getCreateUser());
		}
		if (StringUtil.isNullOrEmpty(entity.getUpdateUser())) {
			entity.setUpdateUser(UserUtil.currentUserId());
		}
		setDeletedInfoIfNull(entity);
	}

	public static <T extends BaseEntity> void setUpdatedInfoIfNull(T entity) {
		entity.setUpdateDate(new Date());
		if (StringUtil.isNullOrEmpty(entity.getUpdateUser())) {
			entity.setUpdateUser(UserUtil.currentUserId());
		}
	}

	public static <T extends BaseEntity> void setDeletedInfoIfNull(T entity) {
		if (entity.getDeleted() == null) {
			entity.setDeleted(false);
		}
	}

	/**
	 * 根据主键属性，判断主键是否值为空
	 *
	 * @param entity
	 * @return 主键为空，则返回false；主键有值，返回true
	 */
	public static <T extends BaseEntity> boolean isIdNotNull(T entity) {
		return !StringUtil.isNullOrEmpty(entity.getId());
	}
}
