package com.zhifeng.springmvcshoppingcart.dao;
 
import java.util.List;

import com.zhifeng.springmvcshoppingcart.model.CartInfo;
import com.zhifeng.springmvcshoppingcart.model.OrderDetailInfo;
import com.zhifeng.springmvcshoppingcart.model.OrderInfo;
import com.zhifeng.springmvcshoppingcart.model.PaginationResult;
 
public interface OrderDAO {
 
    public void saveOrder(CartInfo cartInfo);
 
    public PaginationResult<OrderInfo> listOrderInfo(int page,
            int maxResult, int maxNavigationPage);
    
    public OrderInfo getOrderInfo(String orderId);
    
    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);
 
}
