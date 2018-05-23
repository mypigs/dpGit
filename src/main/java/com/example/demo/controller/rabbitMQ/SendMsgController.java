package com.example.demo.controller.rabbitMQ;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.rabbitMQ.p2p.Customer;
import com.example.demo.controller.rabbitMQ.p2p.Producer;

/**
 * RabbitMQ
 * 
 * @author wb-dp263974
 * @version $Id: SendMsgController.java, v 0.1 2018年5月22日 下午3:21:31 wb-dp263974 Exp $
 */
@RestController
@RequestMapping("/rabbit")
public class SendMsgController {
    
    
    /**
     * 点对点-生产者
     * 
     * @return
     * @throws Exception 
     */
    /*@RequestMapping("/p2p_product")
    public String sendMsg() throws Exception{
        return "p2p生产者发送消息："+Producer.sendMsg();
    }*/
    
    
    /**
     * 点对点-消费者1
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/p2p_customer1")
    public String getMsg1() throws Exception{
        Customer.getMsg1();
        return "p2p消费者1接收消息：";
    }
    
    /**
     * 点对点-消费者2
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping("/p2p_customer2")
    public String getMsg2() throws Exception{
        Customer.getMsg2();
        return "p2p消费者2接收消息：";
    }

}
