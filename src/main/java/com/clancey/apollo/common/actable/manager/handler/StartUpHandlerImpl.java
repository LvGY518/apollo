package com.clancey.apollo.common.actable.manager.handler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clancey.apollo.common.actable.manager.system.SysMysqlCreateTableManager;

/**
 * 启动时进行处理的实现类
 * @author chenbin.sun
 *
 */
@SuppressWarnings("restriction")
@Service
public class StartUpHandlerImpl implements StartUpHandler {

	private static final Logger	log	= LoggerFactory.getLogger(StartUpHandlerImpl.class);

	@Autowired
	private SysMysqlCreateTableManager sysMysqlCreateTableManager;

	@PostConstruct
	public void startHandler() {

		log.info("databaseType=mysql，开始执行mysql的处理方法");

		sysMysqlCreateTableManager.createMysqlTable();
	}


}
