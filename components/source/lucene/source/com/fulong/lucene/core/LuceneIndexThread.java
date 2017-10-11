package com.fulong.lucene.core;

import java.io.IOException;
import java.util.Iterator;

import com.fulong.longcon.repository.Node;
import com.fulong.lucene.IndexManager;
import com.fulong.lucene.service.BatchCreateLuceneIndexService;
import com.fulong.lucene.service.RebuildLuceneIndexService;

/**
 * LuceneIndexThread 建索引线程
 * 
 * @author songbo 2010-10-25
 * 
 */
public class LuceneIndexThread extends Thread {
	private IndexManager indexManager;
	private Iterator<Node> nodes;
	private String[] properties;
	private int currentThread;

	public LuceneIndexThread(IndexManager indexManager, Iterator<Node> nodes,
			String[] properties, int currentThread) {
		this.indexManager = indexManager;
		this.nodes = nodes;
		this.properties = properties;
		this.currentThread = currentThread;
	}

	public void run() {
		try {
			try {
				Thread.sleep(currentThread * 10); // 让第一个线程先初始化createIndex()
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.indexManager.createIndex(nodes, properties, currentThread);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (BatchCreateLuceneIndexService.checkListBatch != null)
			BatchCreateLuceneIndexService.checkListBatch[currentThread] = true; // 索引建立完毕
		// true时代表线程关闭
		else
			RebuildLuceneIndexService.checkListRebuild[currentThread] = true;
	}
}
