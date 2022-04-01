package com.example.demo07.bean;


public class TaFood {

  private long foodId;
  private long price;//!!!!!!!!!
  private String title;
  private String description;
  private long sales;
  private long dinerId;
  private String extra;
  private String imgUrl;
  private String category;


  public long getFoodId() {
    return foodId;
  }

  public void setFoodId(long foodId) {
    this.foodId = foodId;
  }


  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public long getSales() {
    return sales;
  }

  public void setSales(long sales) {
    this.sales = sales;
  }


  public long getDinerId() {
    return dinerId;
  }

  public void setDinerId(long dinerId) {
    this.dinerId = dinerId;
  }


  public String getExtra() {
    return extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }


  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

}
