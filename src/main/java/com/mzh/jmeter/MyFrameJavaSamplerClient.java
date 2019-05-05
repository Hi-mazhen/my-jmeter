/**
 * Copyright (C), 2016-2019, 码农团
 * ClassName: MyFrameJavaSamplerClient
 * Author:   hthn
 * Date:     2019-05-05 09:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mzh.jmeter;

import org.apache.commons.io.IOUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.protocol.tcp.sampler.ReadException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.util.JOrphanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author hthn
 * @create 2019-05-05
 * @since 1.0.0
 */
public class MyFrameJavaSamplerClient extends AbstractJavaSamplerClient {
    private Logger log = LoggerFactory.getLogger(MyFrameJavaSamplerClient.class);

    private static String host = null;
    private static int port;
    private static int frameType = 0x91;
    private static String frameContent;

    private InputStream in = null;
    private OutputStream out = null;
    private static ThreadLocal<OutputStream> outHolder = new ThreadLocal<>();
    private static ThreadLocal<InputStream> inHolder = new ThreadLocal<>();

    private static ThreadLocal<Socket> socketHolder= new ThreadLocal<Socket>() {
        protected Socket initialValue() {

            Socket socket = null;
            try {
                socket = new Socket();
                socket.setKeepAlive(true);
                socket.connect(new InetSocketAddress(host, port));
                if(socket.isConnected()){
                    OutputStream out = socket.getOutputStream();
                    outHolder.set(out);
                    InputStream in = socket.getInputStream();
                    inHolder.set(in);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return socket;

        }
    };

    @Override
    public Arguments getDefaultParameters(){
        //TODO: Right here to define/set parameters from GUI.
        log.info("execute getDefaultParameters...");
        Arguments params = new Arguments();
        params.addArgument("host", "");
        params.addArgument("port", "");
        params.addArgument("frameType", "");
        params.addArgument("frameContent", "");
        return params;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        log.info("execute setupTest...");
        host = context.getParameter("host");
        port = context.getIntParameter("port");
        frameType = context.getIntParameter("frameType");
        frameContent = context.getParameter("frameContent");
        socketHolder.get();
        out = outHolder.get();
        in = inHolder.get();
        log.info("setupTest:" + Thread.currentThread().getId());
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        log.info("execute runTest...");
        // TODO Auto-generated method stub
        SampleResult result = getSampleResult();
        result.sampleStart();

        try {
            out.write(hexStringToByteArray(frameContent));
            out.flush();

            byte[] buffer = new byte[128];
            int len = in.read(buffer);
            byte[] recv = new byte[len];
            System.arraycopy(buffer, 0, recv, 0, len);
            String hexString = JOrphanUtils.baToHexString(recv);
            log.info("Read: " + len + "\n" + hexString);
            result.setResponseData(recv);
            result.setSuccessful(true);

            // switch (frameType) {
            //     case 0:
            //         break;
            //     case 1:
            //         break;
            //     case 2:
            //         break;
            //     default:
            //         break;
            // }
        } catch (IOException e) {
            result.setResponseData((Thread.currentThread().getId() + ":fail!").getBytes());

            result.setSuccessful(true);
            e.printStackTrace();
        }
        result.sampleEnd();
        return result;
    }

    public SampleResult getSampleResult()
    {
        SampleResult result = new SampleResult();
        result.setSampleLabel(getLabel());
        return result;
    }

    public String getLabel()
    {
        return "TCPSampler" + Thread.currentThread().getId();
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        log.info("execute teardownTest...");
        Socket socket = socketHolder.get();
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("teardownTest:" + Thread.currentThread().getId());

    }

    public static byte[] hexStringToByteArray(String hexEncodedBinary) {
        if (hexEncodedBinary.length() % 2 == 0) {
            char[] sc = hexEncodedBinary.toCharArray();
            byte[] ba = new byte[sc.length / 2];

            for (int i = 0; i < ba.length; i++) {
                int nibble0 = Character.digit(sc[i * 2], 16);
                int nibble1 = Character.digit(sc[i * 2 + 1], 16);
                if (nibble0 == -1 || nibble1 == -1){
                    throw new IllegalArgumentException(
                            "Hex-encoded binary string contains an invalid hex digit in '"+sc[i * 2]+sc[i * 2 + 1]+"'");
                }
                ba[i] = (byte) ((nibble0 << 4) | nibble1);
            }

            return ba;
        } else {
            throw new IllegalArgumentException(
                    "Hex-encoded binary string contains an uneven no. of digits");
        }
    }
}
