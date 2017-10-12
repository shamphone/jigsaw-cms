package com.fulong.longcon.repository.property;

import java.io.InputStream;
import java.util.Calendar;

import com.fulong.longcon.repository.Item;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.repository.ValueUtils;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.core.InternalValue;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public abstract class BasicProperty implements Property {
    protected InternalRepository repository;
    protected Node node;
    protected PropertyDefinition definition;
    private Value[] _values;

    public BasicProperty(InternalRepository repository,
                         Node node,
                         PropertyDefinition definition) {
        this.repository = repository;
        this.node = node;
        this.definition = definition;

    }



    /**
     * 从数据库中加载当前属性值。
     * @return Value[]
     */
    protected abstract Value[] loadValues();

    /**
     * 将当前属性值保存到数据库中。
     * @param values Value[]
     */
    protected abstract void saveValues(Value[] values);
    /**
     * 创建缺省值
     * @return InternalValue
     */
    protected InternalValue createDefaultValue() {
        return (InternalValue)this.repository.getValueFactory().createValue((String)null, this.definition.getType());
    }

    /**
       *
       * @param values PropertyValue 数据格式转换错误
       * @throws ValueFormatException
       */
      public void setValue(Value[] values) throws ValueFormatException {
          this._values = values;
          this.saveValues(this._values);
    }
    /**
       *
       * @return PropertyValue
       */
      public Value[] getValues() 
      {
        if (this._values == null){
			this._values = this.loadValues();
			// if this is not multiple values, then set to default value;
			if ((!this.definition.isMultiple() && this._values.length == 0)
			&&(this.definition.getType()==PropertyType.BOOLEAN		
			||this.definition.getType()==PropertyType.DOUBLE		
			||this.definition.getType()==PropertyType.LONG		
			)) {
				InternalValue svalue = this.createDefaultValue();
				this._values = new InternalValue[1];
				this._values[0] = svalue;
			}
		}
		return this._values;
    }

    /**
     *
     * @return String[]
     * @throws ValueFormatException 数据格式转换错误
     */
    public String[] getArray() throws ValueFormatException {
        return ValueUtils.toStringArray(this.getValues());
    }


    /**
     * @return an <code>long</code>.
     *
     * @return an <code>long</code>.
     */
    public long getLength() {
        long length = 0;
        for (int i = 0; i < this.getValues().length; i++)
            length += this.getValues()[i].getString().length();
        return length;
    }

    /**
     *
     * @return an array of lengths
     */
    public long[] getLengths() {
        long[] lengths = new long[this.getValues().length];
        for (int i = 0; i < this.getValues().length; i++)
            lengths[i] = this.getValues()[i].getString().length();
        return lengths;
    }


    /**
     *
     * @return PropertyValue
     */
    public Value getValue() {
        if(this.getValues().length==0)
            return null;
        return this.getValues()[0];
    }

    /**
     *
     * @param value String
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(String value) throws ValueFormatException {
        InternalValue svalue = this.createDefaultValue();
        svalue.setValue(value);
        this.setValue(svalue);

    }

    /**
     *
     * @param value Calendar
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(Calendar value) throws ValueFormatException {
        InternalValue svalue = this.createDefaultValue();
        svalue.setValue(value);
        this.setValue(svalue);
    }

    /**
     *
     * @param value InputStream
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(InputStream value) throws ValueFormatException {
        InternalValue svalue = this.createDefaultValue();
        svalue.setValue(value);
        this.setValue(svalue);
    }

    /**
     *
     * @param value long
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(long value) throws ValueFormatException {
        InternalValue svalue = this.createDefaultValue();
        svalue.setValue(value);
        this.setValue(svalue);
    }

    /**
     *
     * @param value PropertyValue 数据格式转换错误
     * @throws ValueFormatException
     */
    public void setValue(Value value) throws ValueFormatException {
        this.setValue(new Value[] {value});
    }

    /**
     *
     * @param value Content
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(Node value) throws ValueFormatException {
        InternalValue svalue = this.createDefaultValue();
        svalue.setValue(value);
        this.setValue(svalue);
    }

    /**
     *
     * @param values String[]
     * @throws ValueFormatException
     */
    public void setValue(String[] values) throws ValueFormatException {
        if((values!=null)&&(values.length>0)){
            Value[] result = new Value[values.length];
            for (int i = 0; i < values.length; i++) {
                InternalValue svalue = this.createDefaultValue();
                svalue.setValue(values[i]);
                result[i] = svalue;
            }
            this.setValue(result);
        }
    }

    /**
     *
     * @param value double
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(double value) throws ValueFormatException {
        InternalValue svalue = this.createDefaultValue();
        svalue.setValue(value);
        this.setValue(svalue);
    }

    /**
     *
     * @param value boolean
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(boolean value) throws ValueFormatException {
        InternalValue svalue = this.createDefaultValue();
        svalue.setValue(value);
        this.setValue(svalue);
    }


    /**
     *
     * @return boolean
     * @throws ValueFormatException 数据格式转换错误
     */
    public boolean getBoolean() throws ValueFormatException {
        if(this.getValues().length==0)
            return false;
        return this.getValue().getBoolean();
    }

    /**
     *
     * @return Calendar
     * @throws ValueFormatException 数据格式转换错误
     */
    public Calendar getDate() throws ValueFormatException {
        if(this.getValues().length==0)
            return null;
        return this.getValue().getDate();
    }

    /**
     *
     * @return PropertyDefinition
     */
    public PropertyDefinition getDefinition() {
        return this.definition;
    }

    /**
     *
     * @return double
     * @throws ValueFormatException 数据格式转换错误
     */
    public double getDouble() throws ValueFormatException {
        if(this.getValues().length==0)
            return 0;
        return this.getValue().getDouble();
    }

    /**
     *
     * @return String
     */
    public String getID() {
        return this.definition.getID();
    }


    /**
     *
     * @return long
     * @throws ValueFormatException 数据格式转换错误
     */
    public long getLong() throws ValueFormatException {
        if(this.getValues().length==0)
            return 0l;
        return this.getValue().getLong();
    }

    /**
     *
     * @return Content
     */
    public Node getParent() {
        return this.node;
    }

    /**
     *
     * @return Content
     * @throws ValueFormatException 数据格式转换错误
     */
    public Node getReference() throws ValueFormatException {
        String reference = this.getValue().getString();
        return this.node.getRepository().getNode(reference);
    }


    /**
     *
     * @return ContentRepository
     */
    public Repository getRepository() {
        return this.node.getRepository();
    }

    /**
     *
     * @return InputStream
     * @throws ValueFormatException 数据格式转换错误
     */
    public InputStream getStream() throws ValueFormatException {
        if(this.getValues().length==0)
            return null;
        return this.getValue().getStream();
    }

    /**
     *
     * @return String
     * @throws ValueFormatException 数据格式转换错误
     */
    public String getString() throws ValueFormatException {
        if(this.getValues().length==0)
            return null;
        return this.getValue().getString();
    }

    /**
     *
     * @return int
     */
    public int getType() {
        return this.definition.getType();
    }

    /**
        * 返回节点或者属性的深度，即从根节点到当前节点/属性的距离
        * <ul>
        * <li>根节点深度为 0 .
        * <li>节点/属性的深度为其父节点的深度+1.
        * </ul>
        *
        * @return 节点或者属性的深度，即从根节点到当前节点/属性的距离.
        */
       public int getDepth(){
           return this.getParent().getDepth()+1;
       }
       /**
        * 获取指定深度的父节点
        * @param depth int 0<=depth<= getDepth()，0为根节点，以此类推
        * @return Item
        */
    public Item getAncestor(int depth){
        int MaxDepth = this.getDepth();
        if((depth<0)||(depth>MaxDepth))
            return null;
        else
        {
            Item parent = this.getParent();
            while (--depth!=0)
                parent = parent.getParent();
            return parent;
        }
    }

    /**
    * 名称，指在父节点下的名称
    * @return String
    * @todo
    */
   public String getName(){
       return this.definition.getID();
   }
   /**
    * 属性路径
    * @return String
    */
   public String getPath(){
       return this.node.getPath()+"/"+this.getName();
   }


}
