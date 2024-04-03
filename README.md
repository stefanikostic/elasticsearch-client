# Elasticsearch client library - Shop compare

Table of contents
---
- [General info](#general-info)
- [How to use](#how-to-use)
- [Local setup](#local-setup)
  <br/>

General info
---
**Shop compare** is a web application that provides prices comparison for different appliances and electronic
devices. The main goal of this application is to enable customers find their required product at the best price available at the
market. <br/>
In order to provide fast and accurate search of products and categories, we will utilize Elasticsearch to store 
the products and categories and then use the Elasticsearch client to search over those entities. 

**Elasticsearch client library**
- is a service library that provides:
    - Indexing of as products and categories 
      - Index where categories are stored: categories
      - Categories are stored in a tree format such as
      
      ```
       "_index": "categories",
        "_id": "ТВ / Аудио / Видео",
        "_score": 1.0,
        "_source": {
        "id": 1,
        "name": "ТВ / Аудио / Видео",
        "subCategories": [
        {
        "id": 43,
        "name": "Телевизори",
        "subCategories": [
        {
        "id": 44,
        "name": "LED Телевизори",
        "subCategories": []
        },
        {
        "id": 45,
        "name": "QLED Телевизори",
        "subCategories": []
        }
        ]}]}
      ```
    - Searching of products and entities
      - Index where products are stored is called products
      - Products are stored in a tree format such as
      
      ```
        "_index": "products",
        "_id": "CANDY PCT25CBCH64CCB",
        "_score": 0.0,
        "_source": {
        "name": "CANDY PCT25CBCH64CCB",
        "productList": [
        {
        "name": "CANDY PCT25CBCH64CCB",
        "shopName": "Setec",
        "category": "Вградни плотни и фурни (Комплети)",
        "link": "https://setec.mk/index.php?route=product/product&path=10090_10104_10107&product_id=76298&limit=100",
        "image": "https://setec.mk/image/cache/catalog/Product/76298_0-228x228.jpg",
        "isAvailable": false,
        "originalPrice": 24999.0,
        "promotionalPrice": 22999.0,
        "timestamp": 1711484038894
        },
        {
        "name": "Вграден шпорет CANDY PCT25CBCH64CCB, 65L, A+",
        "shopName": "Neptun",
        "category": "Вградни плотни и фурни (Комплети)",
        "link": "https://www.neptun.mk/categories/sporeti_v/CANDY-PCT25CBCH64CCB",
        "image": "https://www.neptun.mk/2022/04/06/23_e47dba84-3f94-4f1c-af97-834591aa6bcd.JPG?width=192",
        "isAvailable": false,
        "originalPrice": 26999.0,
        "promotionalPrice": 24999.0,
        "timestamp": 1711484038894
        }
        ]}
      ```
  
How to use
---
- If you want to use some of the services provided in this library, scan the marker interface of the corresponding module. 
For instance, if you want to use Elasticsearch client search module in your application, 
scan ElasticsearchClientSearchMarker in the application configuration accordingly.

Local Setup
---
Elasticsearch can be started locally using Docker, usually it's configured to be accessed under https://localhost:9200
Here are some example requests for searching through the products:
Get request to URL https://localhost:9200/products/_search
where body is:
```
{
"query" : {
"match_all" : {}
}
}
```
or if we want to filter by regex
```
{
    "query": {
        "regexp": {
            "productList.category.keyword": ".*Шпорети.*"   
        }
    }
}
```

