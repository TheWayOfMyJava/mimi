package com.company.pojo.vo;

public class ProductInfoVo {
    //商品名字
    private String pname;
    //商品类型
    private Integer typeId;
    //最低价格
    private Integer lowPrice;
    //最高价格
    private Integer highPrice;
    //每页记录数
    private Integer Page = 1;

    public ProductInfoVo(String pname, Integer typeId, Integer lowPrice, Integer highPrice, Integer page) {
        this.pname = pname;
        this.typeId = typeId;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        Page = page;
    }

    public ProductInfoVo() {
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Integer lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Integer getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Integer highPrice) {
        this.highPrice = highPrice;
    }

    public Integer getPage() {
        return Page;
    }

    public void setPage(Integer page) {
        Page = page;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "pname='" + pname + '\'' +
                ", typeId=" + typeId +
                ", lowPrice=" + lowPrice +
                ", highPrice=" + highPrice +
                ", Page=" + Page +
                '}';
    }
}
