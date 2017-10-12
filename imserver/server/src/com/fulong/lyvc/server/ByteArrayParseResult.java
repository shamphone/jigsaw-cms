package com.fulong.lyvc.server;

/**
 * This class serves as the parse result for the byte array
 * Used in the TCP Channel findMessage method
 */

public class ByteArrayParseResult {
    public String messageBody;    // The message
    public int messageSize;  // Current message size(including header line)
}
