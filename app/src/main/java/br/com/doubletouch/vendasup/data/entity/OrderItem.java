package br.com.doubletouch.vendasup.data.entity;

/**
 * Created by LADAIR on 06/04/2015.
 */
public class OrderItem {

    private Product product;

    private Double quantity;

    private Double price;

    private PriceTable priceTable;

    public void addQuantity(Double quantity){

        this.quantity += quantity;
    }

    public void removeQuantity(Double quantity){

        this.quantity -= quantity;
        if(this.quantity < 0 ) {
            this.quantity = 0.0;
        }
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PriceTable getPriceTable() {
        return priceTable;
    }

    public void setPriceTable(PriceTable priceTable) {
        this.priceTable = priceTable;
    }
}
