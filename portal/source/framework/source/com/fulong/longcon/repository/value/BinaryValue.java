package com.fulong.longcon.repository.value;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.fulong.longcon.repository.PropertyType;
import java.io.DataInputStream;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.IOException;

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
public class BinaryValue
    extends BasicValue{
    private InputStream value;
    public BinaryValue() {
        this.value = null;
    }

    /**
     * getType
     *
     * @return int
     */
    public int getType() {
        return PropertyType.BINARY;
    }


    public void setValue(String value) {
        if(value!=null){
            this.value = new ByteArrayInputStream(value.getBytes());
        }
    }


    public void setValue(InputStream value) {
        this.value = value;
    }


    public InputStream getStream() {
        return this.value;
    }

    public String getString()
    {
        try {
            InputStream is = this.getStream();
            DataInputStream dis = new DataInputStream(is);
            byte[] imageBuffer = new byte[dis.available()];

            dis.read(imageBuffer);
            String textString = Base64.encode(imageBuffer);
            is.close();
            return textString;
        }   catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int compareTo(Object o) {
        return 0;
    }

}
