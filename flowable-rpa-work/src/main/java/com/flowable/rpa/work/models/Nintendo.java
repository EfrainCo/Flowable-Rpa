package com.flowable.rpa.work.models;

public class Nintendo {

    private String name;

    private String url;

    private String price;

    private String store;

    private String image;

    public Nintendo() {

    }

    public Nintendo(String name, String url, String price, String store, String image) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.store = store;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static final class NintendoBuilder {
        private String name;
        private String url;
        private String price;
        private String store;
        private String image;

        private NintendoBuilder() {
        }

        public static NintendoBuilder Nintendo() {
            return new NintendoBuilder();
        }

        public NintendoBuilder name(String name) {
            this.name = name.replace("UNID", "\"");
            return this;
        }

        public NintendoBuilder url(String url) {
            this.url = url;
            return this;
        }

        public NintendoBuilder price(String price) {
            this.price = price;
            return this;
        }

        public NintendoBuilder store(String store) {
            this.store = store;
            return this;
        }

        public NintendoBuilder image(String image) {
            this.image = image;
            return this;
        }

        public Nintendo build() {
            Nintendo nintendo = new Nintendo();
            nintendo.setName(name);
            nintendo.setUrl(url);
            nintendo.setPrice(price);
            nintendo.setStore(store);
            nintendo.setImage(image);
            return nintendo;
        }
    }
}
