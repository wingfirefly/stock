package vip.linhs.stock.service;

public interface MessageService {

    void sendDingding(String body);

    void sendDingdingMd(String title, String body);

}
