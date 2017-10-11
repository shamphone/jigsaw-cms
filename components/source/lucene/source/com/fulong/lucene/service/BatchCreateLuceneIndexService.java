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
 * BatchCreateLuceneIndexService 多线程批量建索引服务
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-25
 * @modifier songbo 2010-10-25
 */
public class BatchCreateLuceneIndexService extends NodeService {

	private int currentThread; // 当前线程号
	private LuceneIndexManager indexManager;
	private String threadName = "BatchCreateLuceneIndexService";
	private String[] properties;
	private NodeDefinition definition; // 分类
	private Query query;
	private NodeIterator<Node> nodes;
	private int threadNum; // 线程数量
	public static boolean[] checkListBatch; // 线程状态

	private static Log log = LogFactory.getLog(BatchCreateLuceneIndexService.class);

	public void setIndexManager(LuceneIndexManager indexManager) {
		this.indexManager = indexManager;
		this.threadNum = indexManager.threadNum;
	}

	public void setThreadName(String name) {
		this.threadName = name;
	}

	public synchronized void doProcess(NodeDefinition definition,ServiceParameters parameters, HttpServletRequest request) throws Exception {
		log.info("Start");
		this.definition = definition;
		this.currentThread = 0;
		checkListBatch = new boolean[threadNum];
		query = this.repository.getQueryManager().createQuery(this.definition,
				Query.SQL);
		nodes = query.nodes();
		long stepThread = nodes.getSize() / threadNum; // 每个线程处理的节点个数
		this.properties = parameters.getValues("properties");
		this.properties = null;
		log.info("Begin Batch Create index......");
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
				checkListBatch[currentThread] = false; // false时线程是运行状态
			}
		}
		new PoliceThread().run();
	}

	/**
	 * PoliceThread 索引线程的监控线程
	 * 
	 * @author songbo 2010-10-25
	 * 
	 */
	private class PoliceThread extends Thread {
		public void run() {
			boolean isRun = true;
			int allStop = 0;
			while (isRun) {
				allStop = 0;
				for (int i = 0; i < threadNum; i++) {
					if (checkListBatch[i] == true) {
						allStop++; // 查询到线程是关闭状态则计数器加一
					}
				}
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					log.trace(e);
				}
				if (allStop == threadNum)
					isRun = false;
			}
			try {
				indexManager.addIndexesOptimize(); // 索引线程都运行完毕再优化
			} catch (IOException e) {
				log.trace(e);
			}
			long endTime = new Date().getTime();
			log.info("线程索引结束时间：" + endTime);
			log.info("Index Batch Create ready.");
		}
	}
}