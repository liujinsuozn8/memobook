package com.ljs.test.session.shopping;

// 将step2.jsp的所有信息保存到类对象中
public class Customer {
    String name;
    String address;
    String cardType;
    String cardId;

    public Customer() {
    }

    public Customer(String name, String address, String cardType, String cardId) {
        this.name = name;
        this.address = address;
        this.cardType = cardType;
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
