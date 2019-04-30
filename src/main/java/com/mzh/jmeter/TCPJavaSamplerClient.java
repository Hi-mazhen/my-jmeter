/**
 * Copyright (C), 2016-2019, 码农团
 * ClassName: TCPJavaSamplerClient
 * Author:   hthn
 * Date:     2019-04-29 20:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mzh.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 〈一句话功能简述〉<br> 
 * 〈测试tcp，参考大神〉
 *
 * @author hthn
 * @create 2019-04-29
 * @since 1.0.0
 */
public class TCPJavaSamplerClient extends AbstractJavaSamplerClient {
    private Logger log = LoggerFactory.getLogger(TestStringLengthJavaSamplerClient.class);

    private static String host = null;
    private static int port;

    private PrintWriter out = null;
    private static ThreadLocal<PrintWriter> outHolder = new ThreadLocal<PrintWriter>();

    private static ThreadLocal<Socket> socketHolder= new ThreadLocal<Socket>() {
        protected Socket initialValue() {

            Socket socket = null;
            try {
                socket = new Socket();
                socket.setKeepAlive(true);
                socket.connect(new InetSocketAddress(host, port));
                if(socket.isConnected()){
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    outHolder.set(out);
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
        return params;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        host = context.getParameter("host");
        port = context.getIntParameter("port");
        socketHolder.get();
        out = outHolder.get();
        System.out.println("setupTest:" + Thread.currentThread().getId());
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        // TODO Auto-generated method stub
        SampleResult result = getSampleResult();
        result.sampleStart();

        out.write(Thread.currentThread().getId()+ ":Hello JavaSamplerClient!");
        out.flush();
        result.setResponseData((Thread.currentThread().getId() + ":success!").getBytes());
        result.sampleEnd();
        result.setSuccessful(true);
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

        Socket socket = socketHolder.get();
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("teardownTest:" + Thread.currentThread().getId());

    }
}
