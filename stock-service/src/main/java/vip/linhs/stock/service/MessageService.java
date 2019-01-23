package vip.linhs.stock.service;

import vip.linhs.stock.model.po.Message;

public interface MessageService {

    void sendDingding(Message message);

}
