package com.mahesh.ws.example;

import com.mahesh.ws.trainings.*;
import org.apache.cxf.feature.Features;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Features(features="org.apache.cxf.feature.LoggingFeature")
public class CustomerOrderWsImpl  implements CustomerOrdersPortType {

    public CustomerOrderWsImpl() {
        init();
    }

    Map<BigInteger, List<Order>>  customerOrders = new HashMap<>();
    int current;

    public void init(){
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(BigInteger.valueOf(1));
        Product product  = new Product();
        product.setId("1");
        product.setDescription("IPhone");
        product.setQuantity(BigInteger.valueOf(3));
        order.getProduct().add(product);
        orders.add(order);
        customerOrders.put(BigInteger.valueOf(++current),orders);

    }

    @Override
    public GetOrdersResponse getOrders(GetOrdersRequest request) {
        BigInteger customerId = request.getCustomerId();

        List<Order> orders = customerOrders.get(customerId);

        GetOrdersResponse getOrdersResponse = new GetOrdersResponse();

        getOrdersResponse.getOrder().addAll(orders);

        return getOrdersResponse;
    }

    @Override
    public CreateOrdersResponse createOrders(CreateOrdersRequest request) {

        BigInteger customerId = request.getCustomerId();
        Order order = request.getOrder();
        List<Order> orders = customerOrders.get(customerId);
        orders.add(order);
        CreateOrdersResponse createOrdersResponse = new CreateOrdersResponse();
        createOrdersResponse.setResult(true);
        return createOrdersResponse;
    }
}
