package com.fulong.longcon.repository.value;

import java.io.InputStream;
import java.util.Calendar;

import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.core.InternalValue;

/**
 *
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
public abstract  class BasicValue implements InternalValue{
    public BasicValue(){
    }
    public int getType() {
          return PropertyType.UNDEFINED;
    }
    public String getString() {
        return null;
//        throw new ValueFormatException ();
    }


    public void setValue(String value) {
        throw new ValueFormatException ();
    }



    public void setValue(InputStream value) {
        throw new ValueFormatException ();
    }

    public void setValue(long value) {
        throw new ValueFormatException ();
    }

    public void setValue(double value) {
        throw new ValueFormatException ();
    }

    public void setValue(Calendar value) {
        throw new ValueFormatException ();
    }

    public void setValue(boolean value) {
        throw new ValueFormatException ();
    }

    public void setValue(Node value) {
        throw new ValueFormatException ();
    }


    public InputStream getStream() {
        throw new ValueFormatException ();
    }

    public long getLong() {
        throw new ValueFormatException ();
    }

    public double getDouble() {
        throw new ValueFormatException ();
    }

    public Calendar getDate() {
        throw new ValueFormatException ();
    }

    public boolean getBoolean() {
        throw new ValueFormatException ();
    }

    public Node getReference() {
        throw new ValueFormatException ();
    }
    public void setValue(Value value) {
          switch (value.getType()) {
          case PropertyType.BINARY:
              this.setValue(value.getStream());
              break;
          case PropertyType.BOOLEAN:
              this.setValue(value.getBoolean());
              break;
          case PropertyType.DATE:
              this.setValue(value.getDate());
              break;
          case PropertyType.DOUBLE:
              this.setValue(value.getDouble());
              break;
          case PropertyType.LONG:
              this.setValue(value.getLong());
              break;
          case PropertyType.NAME:
              this.setValue(value.getString());
              break;
          default:
              this.setValue(value.getString());
              break;
          }
    }
    
    public String toString(){
    	return this.getString();
    }


}
