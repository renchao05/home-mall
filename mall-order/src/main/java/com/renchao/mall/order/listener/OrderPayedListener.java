package com.renchao.mall.order.listener;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.renchao.mall.order.config.AlipayTemplate;
import com.renchao.mall.order.service.OrderService;
import com.renchao.mall.order.vo.PayAsyncVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderPayedListener {

    @Autowired
    private AlipayTemplate alipayTemplate;

    @Autowired
    private OrderService orderService;

    @PostMapping("/payed/notify")
    public String payedNotify(PayAsyncVo payAsyncVo, HttpServletRequest request) throws AlipayApiException {
        //验签
        Map<String, String[]> map = request.getParameterMap();
        Map<String,String> params = new HashMap<>();
        for (String name : map.keySet()) {
            String[] values = map.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(), alipayTemplate.getCharset(), alipayTemplate.getSign_type()); //调用SDK验证签名
        if (!signVerified) {
            return "验签失败！";
        }
        System.out.println("验签成功！");
        orderService.successfulPayment(payAsyncVo);
        return "success";
    }
}
