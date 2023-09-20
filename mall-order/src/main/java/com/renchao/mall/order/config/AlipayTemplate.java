package com.renchao.mall.order.config;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.renchao.mall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private String app_id = "2021000121650947";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCekXz5SYAKdpl1UcF2oKLYu2AyMQuAB0X9+bHVPocbz1hnj/Yc+KntLOckE90tgyx/s+Rj8Daz7mMbJDpC0zsgXHne7GiQi7l+5yE3J127Wl7Z6GXGequvspUrvSSb0Ccn7xsODx0SHz0ajN4WcQ3r6qOW/ggGp0WVstMTYCJucHV72sSdMO2MHqCdjDPqzqnafyHYNWYBwOWnEgkWLv8GPsNyrD+vDVIO72fMn9mG8gQfY9CQzd/r+ekdSV2UYk//CmYIiqGbjnEHIg4W+Cfgw91Nb4MyEN1362dBtj9fWbLN9HaCeZf7jTbtE1wPyUZgadJpGPUAPfT4ioUcp/CNAgMBAAECggEAeeQIl3Bilqh4anDAMLpmtAfFeI+u8Wml6gepJez82unWYTWO0NJtfT4JJ/iFCtlMuZXFOPWMd2AGmvSMPJY3Wzb7D9vWvVZK7zwokkcWjFB5nTRCGAJwO40hkg/BNm0ZzF7HA1Wm2SleRSyIA+Q/+VvTCNyZutOWb+6YxsodbZqW/hRhXpQF+Qhc7qQE2ey2S2cwYtmp9XZp31kRdqGPqKufwWlYCMqMjtAsfZ5VwFOgFVcYfgleJiQuUGTxT8YneJ/1zgdQVKEuSsf2ovAaOhdQ4HQq/4/kgXiUounX18V2p0PFWleBqkrOIjxvMi+c6k1b7LbvO5ivnSlKRPZIwQKBgQDuHgVd2ejO5HAv6sdyTHW0lwc/oFadwDpSfbdKKTcJuOTm43MpFCb0G6q05DFCrPFovfVyS21cWqYehOImk3vxcofyPZJNFao/kGy0IKzPX1urfPwy8JJyQ3PBgxoxUs7sht8X6FvQ3/uIceHt6S5NjfX9sXXqLvBfFay1V2HRVQKBgQCqehSJrbfz0nJBDvbIMcQ1drQUx6DDMvr05uSLRylW7aiFtb74c0VN77UgmZzIuAey4hIMASw2nVvbzw3nBqJ2f96W4uYLeHAVoxwnoSdaHtY3UujiQEemLMSxoLzTxn2sCvdYKwyCsLYNqgWmdjWWukXcnR3nsOSlB7QN1fWCWQKBgQCGeCmrsG2KuPKp/LoZUBiAnXGYeuGvQGkvQJOLq+7K7MFHqlbUce71l9I1SVCQSL4j2nKL0xC1QPPi9EQKaOtrlLQ1FpRSN9PnBZZ9up4clctc8r4xvi3VnUX3mCPois0UQa4nAC8pq/OOQFHIWxCs92hdliPy1gMkj0bzOZ+h6QKBgC+Y6kcOg7/F0DSt8uUuT/QFcn4ixpX5k+vw03q3oaktrdG3TtybgowiE1ZIzWeVSkD9scA8S5XsFYunoeARECc5yHIkY8psFA9WfTPrYMtetLy5n2AcRmT1Nu3JTS9rygbRAQJS8qlCoXHrOKyjcI7m7z6Ld6AK2aviHLmINYXxAoGAMPH1LcQVpK5LtXzxANjf7NUWiQd6XL0/vIdclI7zvOxAsOd7dsWkJz8B+mVwqj0yP/ve6+uLBXHozs+5bb3n4xFp97UxmOSnSJBMvfRVZFGLijP0vmhZAHS77W+baCfvQ3S3/dOqwv389VyDi2+SrGcZlbCAluEPAzdHgFE8NeE=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmi+zN4vEh8HP/ucz2YHy4CogpQbH4dri+4py0XETAtyY2bdx0os9ZVxs7KqZGP2Cg4nNwOhDYG3XP7IdMp0W7SMsto7Df/4L5+17bo2gQHBOUjmXXG/h7TUB/cm56Khg+I7FvG1LgR1BDfAx/En5swnywJOhm/mpp8hI2F5JIRfv4OPOsCcCBoCB+H6bA19vvUDbRmmCN2tAhK4raKRtd7oj/R0cbXIQL9fKxj98vvGfDGTF8xjZMejHpExnwCznGK24ay9cb17EhNVqqKxrbUcrfmjsi93bbFpQfyWLDOFtJRNNLw7A1b0R1ynVppxHAw+90+iiVpZJ1TOMQJRIOQIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
//    private String notify_url = "http://115.29.43.125:9000/payed/notify";
    private String notify_url = "http://order.renchao05.top/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private String return_url = "http://order.renchao05.top/orderList.html";

    // 签名方式
    private String sign_type = "RSA2";

    // 字符编码格式
    private String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 支付
     * @param vo
     * @return
     * @throws AlipayApiException
     */
    public String pay(PayVo vo) throws AlipayApiException {

        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        JSONObject bizContent = new JSONObject();
        //商户订单号，商户网站订单系统中唯一订单号，必填
        bizContent.put("out_trade_no", vo.getOutTradeNo());
        //付款金额，必填
        bizContent.put("total_amount", vo.getTotalAmount());
        //订单名称，必填
        bizContent.put("subject", vo.getSubject());
        //商品描述，可空
        bizContent.put("body", vo.getBody());
        bizContent.put("timeout_expres", "1m"); // 超时时间1分钟
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        alipayRequest.setBizContent(bizContent.toString());
        alipayRequest.setReturnUrl(return_url);//同步通知页面
        alipayRequest.setNotifyUrl(notify_url);//异步通知页面

        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应：" + result);
        return result;
    }

    /**
     * 关闭订单
     * @param out_trade_no
     * @return
     * @throws AlipayApiException
     */
    public Boolean close(String out_trade_no) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);
        //设置请求参数
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", out_trade_no);
        //商户订单号，商户网站订单系统中唯一订单号
        request.setBizContent(bizContent.toString());
        //请求
        AlipayTradeCloseResponse response = alipayClient.execute(request);
        System.out.println(response.isSuccess()?"收单成功！":"收单失败！");
        return response.isSuccess();
    }
}
