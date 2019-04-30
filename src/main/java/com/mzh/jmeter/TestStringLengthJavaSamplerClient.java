/**
 * Copyright (C), 2016-2019, 码农团
 * ClassName: TestStringLengthJavaSamplerClient
 * Author:   hthn
 * Date:     2019-04-22 16:33
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

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author hthn
 * @create 2019-04-22
 * @since 1.0.0
 */
public class TestStringLengthJavaSamplerClient extends AbstractJavaSamplerClient {

    private Logger log = LoggerFactory.getLogger(TestStringLengthJavaSamplerClient.class);

    private SampleResult results;

    private String testStr;

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        log.info("execute runTest...");
        if(testStr.length() < 5) {
            log.info("fail...");
            results.setSuccessful(false);
        } else {
            log.info("Success...");
            results.setSuccessful(true);
        }
        return results;
    }

    @Override
    public Arguments getDefaultParameters(){
        //TODO: Right here to define/set parameters from GUI.
        log.info("execute getDefaultParameters...");
        Arguments params = new Arguments();
        params.addArgument("testStr", "");
        return params;
    }

    @Override
    public void setupTest(JavaSamplerContext arg0) {
        //TODO: to init the test case, something like JUnit's setUp
        log.info("execute setupTest...");
        results = new SampleResult();
        testStr = arg0.getParameter("testStr", "");
        if (testStr != null && testStr.length() > 0) {
            results.setSamplerData(testStr);
        }
    }


}
