package com.fulong.lucene.core;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
//import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopFieldDocs;

import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.RepositoryException;
import com.fulong.longcon.repository.Row;
import com.fulong.longcon.repository.RowIterator;
import com.fulong.longcon.repository.Node;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class LuceneRowIterator implements RowIterator {
    private LuceneIndexManager manager;
    private String[] columns;
    private int size;
    private Iterator<Row> rows;
    private int position;
    private int pageSize;
    private Repository repository;
    private LuceneQuery query;
    public LuceneRowIterator(Repository repository,
                             LuceneIndexManager manager,
                             LuceneQuery query) {
        this.repository = repository;
        this.manager = manager;
        this.position = 0;
        this.pageSize = 20;
        this.query = query;

        this.init();

    }

    /**
     * Returns an array of all the property names (column names) in this result set.
     *
     * @return a <code>PropertyIterator</code>
     * @throws RepositoryException if an error occurs.
     */
    public String[] getColumnNames() throws RepositoryException {
        return this.columns;
    }

    /**
     * Returns the current position within the iterator.
     *
     * @return a long
     */
    public long getPosition() {
        return this.position;
    }

    /**
     * Returns the number of elements in the iterator.
     *
     * @return a long
     */
    public long getSize() {
        return this.size;
    }

    /**
     * Returns <tt>true</tt> if the iteration has more elements.
     *
     * @return <tt>true</tt> if the iterator has more elements.
     */
    public boolean hasNext() {
        return this.position < this.size;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration.
     */
    public Row next() {

        return this.nextRow();
    }

    /**
     *
     * @return Row
     */
    public Row nextRow() {
        if (this.position >= this.size)
            throw new NoSuchElementException();
        if (!this.rows.hasNext())
            this.fechMore();
        this.position++;
        return (Row)this.rows.next();
    }

    /**
     * Removes from the underlying collection the last element returned by the iterator (optional operation).
     *
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param size int
     */
    public void setFetchSize(int size) {
        this.pageSize = size;
    }

    /**
     * Skip a number of elements in the iterator.
     *
     * @param skipNum the non-negative number of elements to skip
     */
    public void skip(long skipNum) {
        this.position = this.position + (int) skipNum;
    }

    private void init() {
       /* IndexSearcher searcher = null;
        try {
            searcher = this.createSearcher();
            Hits hits = searcher.search(this.query.getQuery(),
                                        this.query.getSorts());
            this.size = hits.length();
            if (hits.length() == 0)
                this.columns = new String[0];
            else {
                Document doc = hits.doc(0);
                Collection columns = new Vector();
                for (Enumeration fields = doc.fields(); fields.hasMoreElements(); ) {
                    Field field = (Field) fields.nextElement();
                    columns.add(field.name());
                }
                this.columns = (String[]) columns.toArray(new String[columns.
                    size()]);
                this.rows = new Vector().iterator();
            }

        } catch (IOException ex) {
            throw new RepositoryException(ex);
        }

        finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException ex) {
                    throw new RepositoryException(ex);
                }
            }
        }
*/	
    	IndexSearcher searcher = null;
		try {
			searcher = this.createSearcher();
			// TopFieldDocs search(Query query, Filter filter,int n,Sort sort)
			// Finds the top n hits for query, applying filter if non-null, and
			// sorting the hits by the criteria in sort.
			TopFieldDocs hits = searcher.search(this.query.getQuery(), null,
					this.query.getHits(), this.query.getSorts());
			// the scores are returned for every matching ;but not the max score
			// for all matching docs is computed
			searcher.setDefaultFieldSortScoring(true, false);
			this.size = hits.totalHits;
			if (this.size == 0)
				this.columns = new String[0];
			else {
				Collection<String> columns = new Vector<String>();
				ScoreDoc[] docs = hits.scoreDocs;
				for (int i = 0; i < docs.length; i++) {
					// 根据docID获得document
					Document doc = searcher.doc(docs[i].doc);

					List<Fieldable> fields = doc.getFields();
					for (int fs = 0; fs < fields.size(); fs++) {
						Field field = (Field) fields.get(fs);
						columns.add(field.name());
					}
				}
				this.columns = (String[]) columns.toArray(new String[columns
						.size()]);
			}
			this.rows = new Vector<Row>().iterator();
		} catch (IOException ex) {
			throw new RepositoryException(ex);
		}

		finally {
			if (searcher != null) {
				try {
					searcher.close();
				} catch (IOException ex) {
					throw new RepositoryException(ex);
				}
			}
		}
    }

    private void fechMore() {
        /*IndexSearcher searcher = null;
        try {
            searcher = this.createSearcher();
            Hits hits = searcher.search(this.query.getQuery(),
                                        this.query.getSorts());
            this.size = hits.length();
            Collection rows = new Vector();
            for (int i = position;
                 (i < hits.length()) && (i < position + pageSize); i++)
                rows.add(new LuceneRow(this.repository,
                                       this.manager,
                                       this.query,
                                       this,
                                       hits.doc(i)));
            this.rows = rows.iterator();

        } catch (IOException ex) {
            throw new RepositoryException(ex);
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException ex) {
                    throw new RepositoryException(ex);
                }
            }
        }*/
    	IndexSearcher searcher = null;
		try {
			searcher = this.createSearcher();
			TopFieldDocs hits = searcher.search(this.query.getQuery(), null,
					searcher.maxDoc(), this.query.getSorts());
			searcher.setDefaultFieldSortScoring(true, false);
			this.size = hits.totalHits;
			Collection<Row> rows = new Vector<Row>();
			ScoreDoc[] docs = hits.scoreDocs;
			for (int i = position; (i < this.size) && (i < position + pageSize); i++) {
				Document doc = searcher.doc(docs[i].doc);
				rows.add(new com.fulong.lucene.core.LuceneRow(this.repository,this.query,this,doc));
			}
			this.rows = rows.iterator();

		} catch (IOException ex) {
			throw new RepositoryException(ex);
		} finally {
			if (searcher != null) {
				try {
					searcher.close();
				} catch (IOException ex) {
					throw new RepositoryException(ex);
				}
			}
		}
    }

    public IndexSearcher createSearcher() throws IOException {
        return new IndexSearcher(manager.getDirectory());
    }

    public void setFetchSize(long size) {
        this.pageSize=(int)size;
    }

    public Node nextNode() {
        return this.nextRow().getNode();
    }

}
