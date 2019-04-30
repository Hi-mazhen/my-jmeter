/**
 * Copyright (C), 2016-2019, 码农团
 * ClassName: MyFrameTCPClientImpl
 * Author:   hthn
 * Date:     2019-04-29 20:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mzh.jmeter;

import org.apache.commons.io.IOUtils;
import org.apache.jmeter.protocol.tcp.sampler.AbstractTCPClient;
import org.apache.jmeter.protocol.tcp.sampler.BinaryTCPClientImpl;
import org.apache.jmeter.protocol.tcp.sampler.ReadException;
import org.apache.jmeter.protocol.tcp.sampler.TCPClient;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.util.JOrphanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author hthn
 * @create 2019-04-29
 * @since 1.0.0
 */
// TCPClientImpl
// BinaryTCPClientImpl
// LengthPrefixedBinaryTCPClientImpl
public class MyFrameTCPClientImpl implements TCPClient {
    private static final Logger log = LoggerFactory.getLogger(BinaryTCPClientImpl.class);


    @Override
    public void setupTest() {

    }

    @Override
    public void teardownTest() {

    }

    @Override
    public void write(OutputStream os, InputStream is) throws IOException {

    }

    @Override
    public void write(OutputStream os, String s) throws IOException {

    }

    @Override
    public String read(InputStream is) throws ReadException {
        return null;
    }

    @Override
    public String read(InputStream is, SampleResult sampleResult) throws ReadException {
        return null;
    }

    @Override
    public byte getEolByte() {
        return 0;
    }

    @Override
    public String getCharset() {
        return null;
    }

    @Override
    public void setEolByte(int eolInt) {

    }
}
