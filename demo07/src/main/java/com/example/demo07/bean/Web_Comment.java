package com.example.demo07.bean;

public class Web_Comment {
    TaComment taComment;
    TaUser taUser;
    TaFood taFood;

    public TaFood getTaFood() {
        return taFood;
    }

    public void setTaFood(TaFood taFood) {
        this.taFood = taFood;
    }

    public Web_Comment(TaComment taComment, TaUser taUser, TaFood taFood) {
        this.taComment = taComment;
        this.taUser = taUser;
        this.taFood = taFood;
    }

    public TaComment getTaComment() {
        return taComment;
    }

    public void setTaComment(TaComment taComment) {
        this.taComment = taComment;
    }

    public TaUser getTaUser() {
        return taUser;
    }

    public void setTaUser(TaUser taUser) {
        this.taUser = taUser;
    }

    public Web_Comment(TaComment taComment, TaUser taUser) {
        this.taComment = taComment;
        this.taUser = taUser;
    }
}
