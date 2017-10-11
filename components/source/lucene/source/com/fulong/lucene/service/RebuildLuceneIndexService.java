/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.lucene.service;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.lucene.core.LuceneIndexManager;
import com.fulong.lucene.core.LuceneIndexThread;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * RebuildLuceneIndexService 重建索引服务
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-25
 */
public class RebuildLuceneIndexService extends NodeService {

	private int currentThread;
	private LuceneIndexManager indexManager;
	private String threadName = "RebuildIndexThread";
	private String[] properties;
	private NodeDefinition definition; // 分类
	private Query query;
	private NodeIterator<Node> nodes;
	private int threadNum;
	public static boolean[] checkListRebuild; // 线程状态

	private static Log log = LogFactory.getLog(RebuildLuceneIndexService.class);

	public void setIndexManager(LuceneIndexManager indexManager) {
		this.indexManager = indexManager;
		this.threadNum = indexManager.threadNum;
	}

	public void setThreadName(String name) {
		this.threadName = name;
	}

	@Override
	public synchronized void doProcess(NodeDefinition definition,ServiceParameters parameters, HttpServletRequest request) throws Exception {
		log.info("Start");
		this.definition = definition;
		this.currentThread = 0;
		checkListRebuild = new boolean[threadNum];
		query = this.repository.getQueryManager().createQuery(this.definition,
				Query.SQL);
		nodes = query.nodes();
		long stepThread = nodes.getSize() / threadNum;
		this.properties = parameters.getValues("properties");
		this.properties = null;
		log.info("Begin rebuild index......");
		long startTime = new Date().getTime();
		log.info("线程索引开始时间：" + startTime);
		for (; currentThread < this.threadNum; currentThread++) {

			LinkedList<Node> nodeList = new LinkedList<Node>();
			for (long i = 0; nodes.hasNext(); i++) {
				if (currentThread != threadNum - 1) {
					if (i >= stepThread)
						break;
				}
				nodeList.add(nodes.next());
			}
			Iterator<Node> it = nodeList.iterator();

			Thread threadIndex = new Thread(new LuceneIndexThread(indexManager,
					it, properties, currentThread), this.threadName
					+ currentThread + "--");
			if (!threadIndex.isAlive()) {
				threadIndex.start();
				checkListRebuild[currentThread] = false;
			}
		}
		new PoliceThread().run();
	}

	private class PoliceThread extends Thread {
		public void run() {
			boolean isRun = true;
			int allStop = 0;
			while (isRun) {
				allStop = 0;
				for (int i = 0; i < threadNum; i++) {
					if (checkListRebuild[i] == true) {
						allStop++;
					}
				}
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					log.debug(e);
				}
				if (allStop == threadNum)
					isRun = false;
			}
			try {
				indexManager.addIndexesOptimize();
			} catch (IOException e) {
				log.trace(e);
			}
			long endTime = new Date().getTime();
			log.info("线程索引结束时间：" + endTime);
			log.info("Index rebuild ready.");
		}
	}
}