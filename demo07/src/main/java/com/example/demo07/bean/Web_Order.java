package com.example.demo07.bean;

public class Web_Order {
    TaFood taFood;
    TaOrder taOrder;
    TaDiner taDiner;

    public TaFood getTaFood() {
        return taFood;
    }

    public Web_Order(TaFood taFood, TaOrder taOrder) {
        this.taFood = taFood;
        this.taOrder = taOrder;
    }

    public Web_Order(TaFood taFood, TaOrder taOrder, TaDiner taDiner) {
        this.taFood = taFood;
        this.taOrder = taOrder;
        this.taDiner = taDiner;
    }

    public TaDiner getTaDiner() {
        return taDiner;
    }

    public void setTaDiner(TaDiner taDiner) {
        this.taDiner = taDiner;
    }

    public void setTaFood(TaFood taFood) {
        this.taFood = taFood;
    }

    public TaOrder getTaOrder() {
        return taOrder;
    }

    public void setTaOrder(TaOrder taOrder) {
        this.taOrder = taOrder;
    }
}
