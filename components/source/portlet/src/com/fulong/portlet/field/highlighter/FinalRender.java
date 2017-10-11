package com.fulong.portlet.field.highlighter;

import java.io.StringReader;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;


import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.StringUtils;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;
/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class FinalRender extends PortletRender {
	public ActionForward render(ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		String keywords = request.getParameter("keyword");
		if(keywords==null){
			keywords="keyword";
		}
		Node node = lookupNode(request, response);
		if (node == null) {
			return mapping.findForward(NODONE);
		}
		Property property = node.getProperty(config.getField());
		if (property == null) 
			return null;
		String html = StringUtils.html2Text(property.getString());
		
		int fragmentNum = 3;
		try{
			fragmentNum = Integer.parseInt(preferences.getValue("count", "3"));
		}catch(Exception ex){
			//just ignore;
		}
		int fragmentLength = 200;
		try{
			fragmentLength = Integer.parseInt(preferences.getValue("length", "200"));
		}catch(Exception ex){
			//just ignore;
		}
		String fragmentSeparator = preferences.getValue("separator", "...");		
		Directory ramDir = new RAMDirectory();
		Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_CURRENT);
		IndexWriter writer = new IndexWriter(ramDir, analyzer, true,IndexWriter.MaxFieldLength.UNLIMITED);		
		Document d = new Document();
		Field f = new Field(property.getID(), html ,Field.Store.YES, Field.Index.ANALYZED);
		d.add(f);
		writer.addDocument(d);
		writer.optimize();
		writer.close();
		IndexReader reader = IndexReader.open(ramDir);
		IndexSearcher searcher = new IndexSearcher(ramDir,true);
		QueryParser parser=new QueryParser(Version.LUCENE_CURRENT,property.getID(), analyzer);
		Query query = parser.parse(keywords);
		//for any multi-term queries to work (prefix, wildcard, range,fuzzy etc) you must use a rewritten query!
		query=query.rewrite(reader);		
		TopDocs docs = searcher.search(query, 1000);			
		ScoreDoc[] hits=docs.scoreDocs;
		String result = "";
		if(hits.length>0){
		Formatter formatter = new SimpleHTMLFormatter("<span class=\""+ preferences.getValue("style", "")+"\">","</span>");
		Highlighter highlighter =new Highlighter(formatter,new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(fragmentLength));
		for (int i = 0; i < hits.length; i++)
		{
			Document doc=searcher.doc(hits[i].doc);
			String text = doc.get(property.getID());
			TokenStream tokenStream=analyzer.tokenStream(property.getID(),new StringReader(text));
			result =
				highlighter.getBestFragments(
					tokenStream,
					text,
					fragmentNum,
					fragmentSeparator);
		}		
		}
		request.setAttribute("value", result);
		
		request.setAttribute("preferences", request.getPreferences());
		request.setAttribute("node", node);
		return mapping.findForward("success");
	}
	
}
