package com.example.restapi;

public class Board {
    private int id;
    private String name;
    private String brand;
    private int price;

    public Board() {
    }

   public Board(int id, String name, String brand, int price){
    this.id = id;
    this.name = name;
    this.brand = brand;
    this.price = price;
   } 

   public int getId() { return id; }
   public String getName() { return name; }
   public String getBrand() { return brand; }
   public int getPrice() { return price; }

   public void setId(int id) { this.id = id; }
   public void setName(String name) { this.name = name; }
   public void setBrand(String brand) { this.brand = brand; }
   public void setPrice(int price) { this.price = price; }
}
