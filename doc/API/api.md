目录

[TOC]

---

#### 1\.用户接口

##### 1-01\.用户注册接口

###### 接口功能

>  用户注册接口

###### URL
> [/api/user/register](/api/user/register)

###### 支持格式
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|mobile      |true    |string  | 手机号|
|captcha     |true    |string  | 验证码|
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |
|userId   |long    |用户id   |

###### 接口示例

> 地址： [/api/user/register](/api/user/register)
```
{
	"errMsg": "",
	"status": 0,
	"userId": 0
}
```
##### 1-02\.用户重置密码接口

###### 接口功能
>  用户重置密码接口

###### URL
> [/api/user/reset](/api/user/reset)

###### 支持格式
> JSON

###### HTTP请求方式
> POST

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|userId      |true    |long  | 用户id|
|pwd      |true    |string  | 密码(待定加密算法)|
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址： [/api/user/reset](/api/user/reset)
```
{
	"errMsg": "",
	"status": 0
}
```

##### 1-03\.用户信息维护接口

###### 接口功能
>  用户信息维护接口

###### URL
> [/api/user/info/modify](/api/user/info/modify)

###### 支持格式
> JSON

###### HTTP请求方式
> POST

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|userId      |true    |long  | 用户id|
|name      |false    |string  | 用户名|
|nickname   |false    |string   |昵称|
|birthday   |false    |string   |生日|
|photoHead   |false    |string   |头像|
|intro   |false    |string   |简介|
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址： [/api/user/reset](/api/user/reset)
```
{
	"errMsg": "",
	"status": 0
}
```

##### 1-04\.用户登录接口

###### 接口功能
>  用户登录接口

###### URL
> [/api/user/login](/api/user/login)

###### 支持格式
> JSON

###### HTTP请求方式
> POST

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|userId      |true    |long  | 用户id|
|pwd      |true    |string  | 密码(待定加密算法)|
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址： [/api/user/reset](/api/user/reset)
```
{
	"errMsg": "",
	"status": 0
}
```
##### 1-05\.用户实名认证接口

###### 接口功能
>  用户实名认证接口

###### URL
> [/api/user/identity/verify](/api/user/identity/verify)

###### 支持格式
> JSON

###### HTTP请求方式
> POST

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|userId      |true    |long  | 用户id|
|identityName     |true    |string  | 身份证名称|
|identityNo       |true    |string  | 身份证号（待定加密算法）|
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址： [/api/user/identity/verify](/api/user/identity/verify)
```
{
	"errMsg": "",
	"status": 0
}
```
##### 1-06\.用户绑定支付账号接口

###### 接口功能
>  用户实名认证接口

###### URL
> [/api/user/pay/bind](/api/user/pay/bind)

###### 支持格式
> JSON

###### HTTP请求方式
> POST

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|userId      |true    |long  | 用户id|
|type     |true    |int  | 支付账号类型：1：支付宝；2：微信|
|payLoginId       |true    |string  | 绑定支付账号|
|identityNo       |true    |string  | 绑定支付账号对应证件号（待定加密算法）|
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址：[/api/user/pay/bind](/api/user/pay/bind)
```
{
	"errMsg": "",
	"status": 0
}
```

#### 2\.商品接口

##### 2-01 导入商品信息

###### 接口功能

> 利用接口导入用户商品信息

###### URL

> /api/product/user/import

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选 | 类型     | 说明     |
> | ----------- | ---- | -------- | -------- |
> | userId      | true | 整形     | 用户ID   |
> | productData | true | JSON数组 | 参考     |
> | sign        | true | 字符串   | 平台签名 |
>
> **productData**
>
> | 参数            | 必选 | 类型   | 说明         |
> | --------------- | ---- | ------ | ------------ |
> | productCode     | true | 字符串 | 商品编号     |
> | productName     | true | 字符串 | 商品名称     |
> | unitPrice       | true | 整形   | 面值         |
> | serialNumber    | true | 字符串 | 序列号       |
> | img             | true | 字符串 | 商品图片     |
> | brand           | true | 字符串 | 商家         |
> | brandImg        | true | 字符串 | 商家图片     |
> | brandDesc       | true | 字符串 | 商家描述信息 |
> | brandTel        | true | 字符串 | 商家电话号码 |
> | desc            | true | 字符串 | 商品介绍     |
> | receive         | true | 字符串 | 商品提货说明 |
> | expirationStart | true | 字符串 | 生效时间     |
> | expirationEnd   | true | 字符串 | 过期时间     |

###### 响应参数

>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |对象 | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| successNumber   |int    |  导入成功数量 |
>|number   |int    |本次请求量   |

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "successNumber":88,
   "number":88
 }
}
```



##### 2-02 获取平台当前可交易商品信息

###### 接口功能

>  用户发布求购订单时，需要获取平台中已上线的所有商品信息，方便用户进行选择发布求购信息

###### URL

> /api/product/platform/effect

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> |userId | true | 字符串 | 当前查询用户 |
> | pageSize    | true  | 整形   | 每页大小                           |
> | pageNum     | true  | 整形   | 页号                               |
> | sign        | true  | 字符串 | 平台签名                           |
> 

###### 响应参数

>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| pageNum   |int    |  当前页 |
>| totalNum |int | 总共多少商品 |
>| productDatas   |Object[]    |商品详情   |
>productDatas 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| productName     | true | 字符串 |
>| unitPrice       | true | 整形   |
>| img             | true | 字符串 |
>| desc            | true | 字符串 |
>| receive         | true | 字符串 |
>| expirationStart | true | 字符串 |
>| expirationEnd   | true | 字符串 |
>
###### 响应实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "pageNum":1,
   "productDatas":[
      	{
    		"productCode":"1111",
    		"productName":"京东E卡",
     		"unitPrice":12,
     		"img":"htttp://pic.ka.png",
     		"desc":"仅限京东平台使用",
     		"receive":"提货说明",
     		"expirationStart":"有效期开始时间",
     		"expiration_end":"有效期结束时间"
   		}
   ]
 }
}
```



##### 2-03 获取用户可售卖商品

###### 接口功能

>  用户发布出售订单时，需要获取平台中自己拥有的商品券，方便进行二次交易

###### URL

> /api/product/user/effect

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | productCode | false | 字符串 | 如果为空，查询全部，不为空查询此商品编码下的所有券 |
> | userId    | true  | 整形   | 用户ID                          |
> | pageSize    | true  | 整形   | 每页大小                           |
> | pageNum     | true  | 整形   | 页号                               |
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| pageNum   |int    |  当前页 |
>| productDatas   |Object[]    |商品详情   |
>productDatas 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> | productCode     | true | 字符串 | 商品编号     |
> | productName     | true | 字符串 | 商品名称     |
> | unitPrice       | true | 整形   | 面值         |
> | img             | true | 字符串 | 商品图片     |
> | desc            | true | 字符串 | 商品介绍     |
> | receive         | true | 字符串 | 商品提货说明 |
> | expirationStart | true | 字符串 | 生效时间     |
> | expirationEnd   | true | 字符串 | 过期时间     |
> 
###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "pageNum":1,
   "productDatas":[
      	{
    		"productCode":"1111",
    		"productName":"京东E卡",
     		"unitPrice":12,
     		"img":"htttp://pic.ka.png",
     		"desc":"仅限京东平台使用",
     		"receive":"提货说明",
     		"expirationStart":"有效期开始时间",
     		"expiration_end":"有效期结束时间"
   		}
   ]
 }
}
```



##### 2-04 获取用户商品详情

###### 接口功能

>  获取用户商品信息，不包含敏感信息

###### URL

> /api/product/user/details

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId    | true  | 整形   | 用户ID                          |
> | productCode | true | 字符串 | 商品编号|
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数

>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| productData   |Object  |商品详情   |
>productData 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> | productCode      | 字符串 | 商品编号     |
> | productName     | 字符串 | 商品名称     |
> | unitPrice       | 整形   | 面值         |
> | img             | 字符串 | 商品图片     |
> | brand           | 字符串 | 商家         |
> | brandImg         | 字符串 | 商家图片     |
> | brandDesc        | 字符串 | 商家描述信息 |
> | brandTel         | 字符串 | 商家电话号码 |
> | desc            | 字符串 | 商品介绍     |
> | receive          | 字符串 | 商品提货说明 |
> | expirationStart | 字符串 | 生效时间     |
> | expirationEnd    | 字符串 | 过期时间     |

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "productData":
      	{
    		"productCode":"1111",
    		"productName":"京东E卡",
     		"unitPrice":12,
     		"img":"htttp://pic.ka.png",
            "brand":"京东",
            "brandImg":"http://jindong.png",
            "brandDesc":"互联网企业",
            "brandTel":"0931123422",
     		"desc":"仅限京东平台使用",
     		"receive":"提货说明",
     		"expirationStart":"有效期开始时间",
     		"expiration_end":"有效期结束时间"
   		}
 }
}
```



##### 2-05  查询用户非持有券

###### 接口功能

>  获取用户商品信息，不包含敏感信息

###### URL

> /api/product/user/nonHoldList

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId    | true  | 整形   | 用户ID                          |
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| productData   |Object  |商品详情   |
>productData 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> | productCode      | 字符串 | 商品编号     |
> | productName     | 字符串 | 商品名称     |
> | unitPrice       | 整形   | 面值         |
> | img             | 字符串 | 商品图片     |
> | brand           | 字符串 | 商家         |
> | brandImg         | 字符串 | 商家图片     |
> | brandDesc        | 字符串 | 商家描述信息 |
> | brandTel         | 字符串 | 商家电话号码 |
> | desc            | 字符串 | 商品介绍     |
> | receive          | 字符串 | 商品提货说明 |
> | expirationStart | 字符串 | 生效时间     |
> | expirationEnd    | 字符串 | 过期时间     |

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "productData":
      	{
    		"productCode":"1111",
    		"productName":"京东E卡",
     		"unitPrice":12,
     		"img":"htttp://pic.ka.png",
          "brand":"京东",
          "brandImg":"http://jindong.png",
          "brandDesc":"互联网企业",
          "brandTel":"0931123422",
     		"desc":"仅限京东平台使用",
     		"receive":"提货说明",
     		"expirationStart":"有效期开始时间",
     		"expiration_end":"有效期结束时间"
   		}
 }
}
```



##### 2-06\. 查询自己持有券详情接口

###### 接口功能

> 查询自己持有券详情接口

###### URL
> [/api/user/product/detail](/api/user/product/detail)

###### 支持格式
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> | 参数        | 必选 | 类型   | 说明     |
> | :---------- | :--- | :----- | -------- |
> | productCode | true | string | 商品编码 |
> | userId      | true | string | 用户ID   |
> | sign        | true | string | 签名     |

###### 返回字段
> | 返回字段                        | 字段类型 | 说明                             |
> | :------------------------------ | :------- | :------------------------------- |
> | status                          | int      | 返回结果状态。0：正常；1：错误。 |
> | errMsg                          | string   | 错误描述                         |
> | info                            | object   | 用户持有券详情                   |
> | -    product                    | object   | 商品信息                         |
> | --        productImg            | string   | 商品图片url                      |
> | --        name                  | string   | 商品名称                         |
> | --        unitPrice             | int      | 商品面值(单位:分)                |
> | --        description           | string   | 商品描述                         |
> | -    brandInfo                  | object   | 品牌商信息                       |
> | --        brand                 | string   | 品牌商名称                       |
> | --        brandImg              | string   | 品牌商图片url                    |
> | --        brandDescription      | string   | 品牌商备注信息                   |
> | --        receive               | string   | 承兑说明                         |
> | --        expirationStart       | string   | 券有效期开始时间                 |
> | --        expirationEnd         | string   | 券有效期结束时间                 |
> | -    stockInfo                  | object   | 持有商品库存信息                 |
> | --        num                   | int      | 可用量                           |
> | --        lockedNum             | int      | 锁定量                           |
> | -    couponChainInfo            | object   | 券链信息                         |
> | --        serviceProviderName   | string   | 数字券技术服务商名称             |
> | --        publisherImg          | string   | 发行商图片url                    |
> | --        publisherIntroduction | string   | 发行商简介                       |

###### 接口示例

> 地址： [/api/user/product/detail](/api/user/product/detail)

```
{
	"errMsg": "",
	"status": 0,
	"info": {
		"product": {
			"productImg": "http://product.png",
			"unitPrice": 600,
			"name": "Nick Sportswear"
		},
		"brandInfo": {
			"brand": "耐克官方旗舰店",
			"brandImg":"http://brand.png",
			"brandDescription": "提供承兑",
			"receive": "
			  提货有效期为：2019.03.23-2049.12.31
			  此卡不兑换现金，只可提取Nick自产产品
			",
			"expirationStart": "2019.03.23",
			"expirationEnd": "2049.12.31"
		},
		"stockInfo": {
			"num":5,
			"lockedNum":5
		},
		"couponChainInfo": {
			"serviceProviderName":"BUMO",
			"publisherImg":"http://publisher.png",
			publisherIntroduction:"NICK公司总部位于美国俄勒冈州波特兰市。公司生产的体育用品包罗万象公司生产的体育用品。"
		}

    }
}
```
#### 3、广告订单

##### 3-01 创建广告订单接口

###### 接口功能

>  创建广告订单调用接口

###### URL

> /api/adOrder/user/create

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | productCode | true | 字符串 | 商品编号|
> | adTitle | true | 字符串 |广告标题|
> | price | true | 整型 |单个商品价格|
> | number | true | 整型 |商品售卖价格|
> | userId    | true  | 整形   | 用户ID                          |
> | orderType    | true  | 整形   | 广告类型(1:出售 2:求购)                 |
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| orderCode  |String  |广告订单号   |
>

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "orderCode":"NJ1234567321"
 }
}
```



##### 3-02  关闭订单

###### 接口功能

>  用户自定义取消正在展示中的订单

###### URL

> /api/adOrder/user/cancel

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | orderCode | true | 字符串 | 广告订单号|
> | userId | true | 字符串 |创建人|
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| orderCode  |String  |广告订单号   |

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "orderCode":"NJ1234567321"
 }
}
```



##### 3-03  获取集市订单数据

###### 接口功能

>  用户点击集市，会展示我要买和我要卖。获取当前用户可以进行查看的订单数据

###### URL

> /api/adOrder/plat/list

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId | true | 字符串 |当前用户ID|
> | type | true | 字符串 |当前用户ID|
> | pageSize    | true  | 整形   | 每页大小                           |
> | orderType    | true  | 整形   | 广告类型(1:我要卖 2:我要买)                 |
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| orderDatas  |Objet[]  |广告订单集合  |
> orderData 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> | orderCode    | 字符串 | 订单号    |
> | productName     | 字符串 | 商品名称     |
> | img             | 字符串 | 商品图片     |
> | price | 整形   | 销售单价         |
> | orderNumber | 整形   | 订单总量     |
> |saleedNumber | 整形   |已售卖量    |
> |saleNumber | 整形   |可交易量   |
> | title | 字符串  | 订单标题       |
> | brand           | 字符串 | 商家         |
> | brandImg         | 字符串 | 商家图片     |
> | brandTel         | 字符串 | 商家电话号码 |
> | desc            | 字符串 | 商品介绍     |
> | userId            | 字符串 | 用户Id     |
> | userImg         | 字符串 | 用户头像|
> | userBoundState  |整型 | 1:仅支付宝 2:微信 3:支付宝微信均绑定|
> | expirationStart | 字符串 | 生效时间     |
> | expirationEnd    | 字符串 | 过期时间     |
> 

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "orderDatas":[
     {
      "orderCode":"NG12323456",
       "productName":"京东E卡",
       "img":"http://ek.png",
       "price":12,
       "orderNumber":100,
       "saleedNumber":10,
       "saleNumber":90,
       "title":"便宜的E卡出售",
       "brand":"京东",
       "brandImg":"http://jd.png",
       "brandTel":"0931-43173213",
       "des":"E卡描述",
       "userId":"123",
       "userImg":"http://touxiang.png",
       "userBoundState":"3",
       "expirationStart":"生效时间",
       "expirationEnd":"失效时间"
     }
   ]
 }
}
```



##### 3-04   获取我的订单

###### 接口功能

>   获取用户个人我的订单

###### URL

> /api/adOrder/user/list

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId | true | 字符串 |当前用户ID|
> | pageSize    | true  | 整形   | 每页大小                           |
> | orderType    | true  | 整形   | 广告类型(1:我要卖 2:我要买)                 |
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| orderDatas  |Objet[]  |广告订单集合  |
> orderData 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> | orderCode    | 字符串 | 订单号    |
> | productName     | 字符串 | 商品名称     |
> | orderType     |整型 | 订单类型     |
> | img             | 字符串 | 商品图片     |
> | unit_price | 整形   | 商品单价      |
> | price | 整形   | 销售单价         |
> | orderNumber | 整形   | 订单总量     |
> |saleedNumber | 整形   |已完成量    |
> |saleNumber | 整形   |可交易量   |
> | title | 字符串  | 订单标题       |
> | desc            | 字符串 | 商品介绍     |
> | userId            | 字符串 | 用户Id     |
> | expirationStart | 字符串 | 生效时间     |
> | expirationEnd    | 字符串 | 过期时间     |

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "orderDatas":[
     {
      "orderCode":"NG12323456",
       "productName":"京东E卡",
       "orderType":":(1:出售;2:求购)"，
       "img":"http://ek.png",
       "price":12,
       "orderNumber":100,
       "saleedNumber":10,
       "saleNumber":90,
       "title":"便宜的E卡出售",
       "brand":"京东",
       "brandImg":"http://jd.png",
       "brandTel":"0931-43173213",
       "des":"E卡描述",
       "userId":"123",
       "userImg":"http://touxiang.png",
       "userBoundState":"3",
       "expirationStart":"生效时间",
       "expirationEnd":"失效时间"
     }
   ]
 }
}
```



#### 4、交易订单

##### 4-01\. 创建交易订单接口

###### 接口功能

> 创建交易订单接口

###### URL
> [/api/order/trade/create](/api/order/trade/create)

###### 支持格式
> JSON

###### HTTP请求方式
> POST

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|----- |
|uuid       |true    |string  | 防止重复提交|
|toUserId|true|long  |买家id|
|orderNo|true|string  |广告订单编号|
|productCode|true|string|购买商品编码|
|productNum|true|int  |购买商品数量|
|salePrice |true|int  |购买商品价格|
|serviceFee|true|int  |服务费(单位:分)|
|type|true|int|订单类型:(1:出售;2:求购)|
|remarks|false|string|交易描述|
|sign|true|string|签名|

###### 返回字段
> |返回字段|字段类型|说明   |
|:-----   |:-----------|:-------------------------|
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string |错误描述   |
|tradeNo |string| 交易订单号|


###### 接口示例

> 地址： [/api/order/trade/create](/api/order/trade/create)

```
{
	"errMsg": "",
	"status": 0,
	"tradeNo": 100
}

```

##### 4-02\. 取消交易订单接口

###### 接口功能

> 取消交易订单接口

###### URL
> [/api/order/trade/cancel](/api/order/trade/cancel)

###### 支持格式
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|----- |
|uuid       |true    |string  | 防止重复提交|
|tradeNo |true|string |交易订单编号|
|sign|true|string|签名|

###### 返回字段
> |返回字段|字段类型|说明   |
|:-----   |:-----------|:-------------------------|
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string |错误描述   |
|tradeNo |string| 交易订单号|


###### 接口示例

> 地址： [/api/order/trade/cancel](/api/order/trade/cancel)

```
{
	"errMsg": "",
	"status": 0,
	"tradeNo":"10000000323jk200023994893"
}

```


##### 4-03\. 查询交易订单详情接口

###### 接口功能
> 查询交易订单详情接口

###### URL
> [/api/order/trade/detail](/api/order/trade/detail)

###### 支持格式
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|tradeNo   |true   |string  |交易订单号|
|sign |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |
|info |object |交易订单商品详情                        |
|-    product |object |商品信息 |
|--        productImg |string |图片url |
|--        name |string |名称|
|--        unitPrice |int |面值(单位:分)|
|-    user |object |用户信息 |
|--        fromUserId |long |卖家用户id|
|--        fromUserNickName | string |卖家昵称|
|--        fromUserName | string |卖家名称|
|--        fromUserAccount |string|卖家收款账户|
|--        toUserId |long |买家用户id|
|--        toUserNickName | string |买家昵称|
|-    trade |object |交易订单信息 |
|--        tradeNo|string |交易订单号|
|--        payNo |string |支付流水号|
|--        amount | int |订单总金额(单位:分)|
|--        orderNum |int |订单数量|
|--        salePrice |int |商品售价(单位:分)|
|--        serviceFee |int |服务费(单位:分)|
|--        remindPay |int |是否已催款:(0:否;1:是)|
|--        status |int |状态:(1:创建;2:待支付;3:取消;4:下线取消;5:支付成功;6:支付失败)|
|--        createTime |string |交易时间 |
|--        payTime |string |支付完成时间|

###### 接口示例

> 地址： [/api/order/trade/info](/api/order/trade/info)

```
{
	"errMsg": "",
	"status": 0,
	"info": {
		"product": {
			"productImg": "http://product.png",
			"unitPrice": 200,
			"name": "东券-001"
		},
		"trade": {
			"tradeNo": "10000000323jk200023994893",
			"payNo": "10000000323jk200023994893",
			"amount": 300,
			"createTime": "2019-05-16 12:44:25",
			"payTime": "2019-05-16 12:44:25",
			"orderNum": 3,
			"salePrice": 100,
			"serviceFee":1,
			"remindPay":1,
			"status": 1
		},
		"user": {
			"toUserId": 2,
			"toUserNickName": "买家昵称",
			"fromUserNickName": "卖家昵称",
			"fromUserId": 1,
			"fromUserName":"苏大强",
			"fromUserAccount":"bitqtZ***ekjkd.com"
		}
	}
}
```


##### 4-04\. 支付结果回调接口

###### 接口功能
> 支付完成接口

###### URL
> [/api/order/trade/pay/notify](/api/order/trade/pay/notify)

###### 支持格式
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|----- |
|tradeNo    |true    |string  |交易订单号|
|payNo    |true    |string  | 支付流水号|
|payAmout   |true    |int  | 支付金额(单位:分)|
|payType   |true    |int  | 支付方式1：支付宝；2：微信|
|payStatus   |true    |int  | 支付状态1：成功；2：失败|
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string |错误描述   |


###### 接口示例

> 地址： > [/api/order/trade/pay/notify](/api/order/trade/pay/notify)

```
{
	"errMsg": "",
	"status": 0
}

```

##### 4-05\.查询待付款/进行中交易订单列表接口

###### 接口功能
>  查询待付款/进行中交易订单列表接口

###### URL
> [/api/order/trade/list](/api/order/trade/list)

###### 支持格式
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|userId      |true    |long  | 用户Id|
|pageNum     |true    |int   |页数             |
|pageSize    |true    |int   |每页显示数量。 |
|status   |true    |int  |订单状态:(1:进行中;2:待支付)  |
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |
|total  |int | 订单总记录数|
|list |list |交易订单列表                        |
|-    product |object |商品信息 |
|--        productImg |string |图片url |
|--        name |string |名称|
|--        unitPrice |int |面值(单位:分)|
|-    user |object |用户信息 |
|--        fromUserId |long |卖家用户id|
|--        fromUserNickName | string |卖家昵称|
|--        toUserId |long |买家用户id|
|--        toUserNickName | string |买家昵称|
|-    trade |object |交易订单信息 |
|--        tradeNo |string |交易订单号|
|--        payNo |string |支付流水号|
|--        amount |string |订单金额(单位:分)|
|--        orderNum |int |订单数量|
|--        salePrice |int |商品售价(单位:分)|
|--        serviceFee |int |服务费(单位:分)|
|--        status |int |状态:(1:创建;2:待支付;3:取消;4:下线取消;5:支付成功;6:支付失败)|
|--        createTime |string |交易时间 |
|--        payTime |string |支付完成时间 |

###### 接口示例

> 地址：[/api/order/trade/list](/api/order/trade/list)
```
{
	"errMsg": "",
	"list": [
		{
			"product": {
				"productImg": "http://product.png",
				"unitPrice": 100,
				"name": "东券-001"
			},
			"trade": {
				"tradeNo": "100000001",
				"payNo": "200000001",
				"amount": 300,
				"createTime": "2019-05-16 12:30:28",
				"payTime": "2019-05-16 12:30:28",
				"orderNum": 3,
				"salePrice": 100,
			   "serviceFee":1,
				"status": 2
			},
			"user": {
			      "toUserId": 2,
			      "toUserNickName": "买家昵称",
			      "fromUserNickName": "卖家昵称",
			      "fromUserId": 1
			}
		}
	],
	"status": 0
}
```

##### 4-06\.查询已完成交易订单列表接口

###### 接口功能
>  查询已完成交易订单列表接口

###### URL
> [/api/order/trade/finish/list](/api/order/trade/finish/list)

###### 支持格式
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|userId      |true    |long  | 用户Id|
|pageNum     |true    |int   |页数             |
|pageSize    |true    |int   |每页显示数量。 |
|status   |true    |int   |订单状态:(1:已完成)  |
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |
|total  |int | 订单总记录数|
|list |list |交易订单列表                        |
|-    product |object |商品信息 |
|--        productImg |string |图片url |
|--        name |string |名称|
|--        unitPrice |int |面值(单位:分)|
|-    user |object |用户信息 |
|--        fromUserId |long |卖家用户id|
|--        fromUserNickName | string |卖家昵称|
|--        toUserId |long |买家用户id|
|--        toUserNickName | string |买家昵称|
|-    trade |object |交易订单信息 |
|--        tradeNo |string |交易订单号|
|--        payNo |string |支付流水号|
|--        amount |int |订单金额(单位:分)|
|--        orderNum |int |订单数量|
|--        salePrice |int |商品售价(单位:分)|
|--        serviceFee |int |服务费(单位:分)|
|--        status |int |状态:(1:已完成;)|
|--        createTime |string |交易时间 |
|--        payTime |string |支付完成时间 |
|--        type |string |订单类型:(1:广告出售订单;2:广告求购订单;3:交易出售订单;4:交易求购订单)|

###### 接口示例

> 地址：[/api/order/trade/finish/list](/api/order/trade/finish/list)
```
{
	"errMsg": "",
	"list": [
		{
			"product": {
				"productImg": "http://product.png",
				"unitPrice": 100,
				"name": "东券-001"
			},
			"trade": {
				"tradeNo": "100000001",
				"payNo": "200000001",
				"amount": 300,
				"createTime": "2019-05-16 12:30:28",
				"payTime": "2019-05-16 12:30:28",
				"orderNum": 3,
				"salePrice": 100,
			   "serviceFee":1,
				"status":1,
				"type":1
			},
			"user": {
				"toUserId": 2,
			    "toUserNickName": "买家昵称",
			    "fromUserNickName": "卖家昵称",
			    "fromUserId": 1
			}
		}
	],
	"status": 0
}
```
##### 4-07\.查询已完成（广告+交易）订单列表接口

###### 接口功能
>  查询已完成（广告+交易）订单列表接口

###### URL
> [/api/order/finish/list](/api/order/finish/list)

###### 支持格式
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|userId      |true    |long  | 用户Id|
|pageNum     |true    |int   |页数             |
|pageSize    |true    |int   |每页显示数量。 |
|status   |true    |int   |订单状态:(1:已完成)  |
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |
|total  |int | 订单总记录数|
|list |list |交易订单列表                        |
|-    product |object |商品信息 |
|--        productImg |string |图片url |
|--        name |string |名称|
|--        unitPrice |int |面值(单位:分)|
|-    trade |object |交易订单信息 |
|--        tradeNo |string |交易订单号|
|--        payNo |string |支付流水号|
|--        amount |int |订单金额(单位:分)|
|--        orderNum |int |订单数量|
|--        salePrice |int |商品售价(单位:分)|
|--        serviceFee |int |服务费(单位:分)|
|--        status |int |状态:(1:已完成;)|
|--        createTime |string |交易时间 |
|--        finishTime |string |完成时间 |
|--        type |string |订单类型:(1:广告出售订单;2:广告求购订单;3:交易出售订单;4:交易求购订单)|

###### 接口示例

> 地址：[/api/order/trade/finish/list](/api/order/trade/finish/list)
```
{
	"errMsg": "",
	"list": [
		{
			"product": {
				"productImg": "http://product.png",
				"unitPrice": 100,
				"name": "东券-001"
			},
			"trade": {
				"tradeNo": "100000001",
				"payNo": "200000001",
				"amount": 300,
				"createTime": "2019-05-16 12:30:28",
				"finishTime": "2019-05-16 12:30:28",
				"orderNum": 3,
				"salePrice": 100,
			    "serviceFee":1,
				"status":1,
				"type":1
			}
		}
	],
	"status": 0
}
```

#### 5 用户券包

##### 5-01 获取用户有效券

###### 接口功能

>  券包中获取用户可用券

###### URL

> /api/voucher/user/effectList

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> |userId | true | 字符串 | 当前查询用户 |
> | pageSize    | true  | 整形   | 每页大小                           |
> | pageNum     | true  | 整形   | 页号                               |
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| pageNum   |int    |  当前页 |
>| productDatas   |Object[]    |商品详情   |
>productDatas 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> | productCode     | true | 字符串 | 商品编号     |
> | productName     | true | 字符串 | 商品名称     |
> | unitPrice       | true | 整形   | 面值         |
> | img             | true | 字符串 | 商品图片     |
> | number            | true | 整型 | 持有数量    |
> | effectNumber            | true | 整型 | 可交易量   |
> | lockNumber            | true | 整型 | 锁定量   |
> 

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "pageNum":1,
   "productDatas":[
     {
      "productCode":"1234211",
       "productName":"京东E卡",
       "unitPrice":12,
       "img":"http://e.png",
       "number":100,
       "effectNumber":10,
       "lockNumber":90
   
     }
   ]
 }
}
```



##### 5-02  转出用户券

###### 接口功能

>  主要用于券的转出

###### URL

> /api/voucher/user/turnout

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId             | true | 整型|当前用户ID|
> | productCode | true | 字符串 |券码|
> | number          | true | 整型 |转让数量|
> | targetId          | true | 整型 |目标用户|
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| orderCode  |String |转出订单号  |
>

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "orderCode":"TNG2313411"
 }
}
```



##### 5-03  兑换用户商品

###### 接口功能

>  主要用于券包的承兑

###### URL

> /api/voucher/user/accept

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId             | true | 整型|当前用户ID|
> | productCode | true | 字符串 |券码|
> | number          | true | 整型 |转让数量|
> | consignee          | true | 字符串 |联系人|
> | consigneePhone          | true | 字符串 |联系人电话号码|
> | consigneeAddress         | true | 字符串 |联系人地址|
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| orderCode  |String |承兑订单号  |
>| productName     | 字符串 | 商品名称     |
>| img             | 字符串 | 商品图片     |
>| number | 整形   |承兑数量     |
>| consignee        | true | 字符串 |联系人|
>| consigneePhone          | true | 字符串 |联系人电话号码|
>| consigneeAddress         | true | 字符串 |联系人地址|

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "orderCode":"TNG2313411",
   "productName":"E卡",
   "img":"http://e.png",
   "number":10,
   "consignee":"王五",
   "consigneePhone":"13834567532",
   "consigneeAddress":"北京朝阳"
 }
}
```



##### 5-04  兑换列表

###### 接口功能

>  主要用于券包的承兑列表展示

###### URL

> /api/voucher/user/acceptList

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId             | true | 整型|当前用户ID|
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| acceptDatas  |Objet[]  |承兑订单集合  |
> acceptData 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> |acceptCode    | 字符串 | 订单号    |
> | productName     | 字符串 | 商品名称     |
> | img             | 字符串 | 商品图片     |
> | unit_price | 整形   | 商品面值      |
> | acceptNumber | 整形   | 承兑数量     |
> 

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "acceptDatas";[
     {
   	 	"acceptCode":"M212313411",
   		"productName":"E卡",
   	   "img":"http://e.png",
   	   "unit_price":10,
   		"acceptNumber":1
     }
   ]
 }
}
```



##### 5-05   转出商品列表

###### 接口功能

>  主要用于转出商品列表

###### URL

> /api/voucher/user/turnoutList

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId             | true | 整型|当前用户ID|
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| turnoutDatas  |Objet[]  |承兑订单集合  |
> turnoutData 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> |turnoutCode    | 字符串 | 订单号    |
> | productName     | 字符串 | 商品名称     |
> | img             | 字符串 | 商品图片     |
> | unit_price | 整形   | 商品面值      |
> | turnoutNumber | 整形   | 承兑数量     |
>
###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "turnoutDatas";[
     {
   	 	"turnoutCode":"M212313411",
   		"productName":"E卡",
   	   "img":"http://e.png",
   	   "unit_price":10,
   		"turnoutNumber":1
     }
   ]
  
 }
}
```



##### 5-06 失效商品列表

###### 接口功能

>  主要用于失效商品

###### URL

> /api/voucher/user/overdueList

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId             | true | 整型|当前用户ID|
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| productDatas  |Objet[]  |承兑订单集合  |
> productData 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> | productName     | 字符串 | 商品名称     |
> | img             | 字符串 | 商品图片     |
> | unit_price | 整形   | 商品面值      |
> | number | 整形   | 商品数量     |
> 

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "productDatas";[
     {
   		"productName":"E卡",
   	   "img":"http://e.png",
   	   "unit_price":10,
   		"number":1
     }
   ]
  
 }
}
```



##### 5-07   承兑订单详情

###### 接口功能

>  主要用于查询承兑订单详情

###### URL

> /api/voucher/user/acceptDetail

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId             | true | 整型|当前用户ID|
> | acceptCode             | true | 字符串|承兑订单编号|
> | sign        | true  | 字符串 | 平台签名                           |
>

###### 响应参数
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> |acceptCode    | 字符串 | 订单号    |
> | productName     | 字符串 | 商品名称     |
> | img             | 字符串 | 商品图片     |
> | unit_price | 整形   | 商品面值      |
> | acceptNumber | 整形   | 商品数量     |
> | state | 整形   | 状态    |
> | consignee          | 字符串 | 联系人 |
> | consigneePhone          | 字符串   | 电话号码 |
> | consigneeAddress         | 字符串   | 地址 |
> 

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
     "acceptCode":"TY123134",
     "productName":"E卡",
   	  "img":"http://e.png",
     "unit_price":10,
     "acceptNumber":1,
     "state":"1(1:等待承兑;2:承兑完成;3:取消承兑;4:承兑失败)",
     "consignee":"王五",
     "consigneePhone":"1234531",
     "consigneeAddress":"北京朝阳"  
     }
}
```



#### 6、区块链

##### 6-01\.区块链同用户商品库存接口

###### 接口功能

>  查询待付款/进行中交易订单列表接口

###### URL

> [/api/stock/sync](/api/stock/sync)

###### 支持格式
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|userId      |true    |long  | 用户Id|
|productCode     |true    |int   |商品编码             |
|serialNumber    |true    |int   |券码 |
|syncType  |true    |int  |库存同步类型:(1:增加库存;2:减少库存)  |
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址：[/api/stock/sync](/api/stock/sync)
```
{
	"errMsg": "",
	"status": 0
}
```

#### 7、平台相关

##### 7-01 发送短信接口

###### 接口功能

> 发送短信

###### URL

> /api/sms/send

###### 支持格式

> JSON

###### HTTP请求方式

> GET

###### 请求参数

> | 参数        | 必选 | 类型     | 说明     |
> | ----------- | ---- | -------- | -------- |
> | mobile      | true 	| string     | 手机号   |
> | type	 | true	| int 	| 短信类型：0:重置密码1:注册验证码（需要返回有效时间）,2:发布求购广告成功通知,3:发布出售广告成功通知,4:购买成功收货通知,5:转出成功通知,6:通知买家付款|
> | sign       	 | true	| string   | 签名 |
> |text |false|string|其他信息，例如订单号|
>

###### 响应参数
>| 返回字段 | 字段类型 | 说明                             |
>| :------- | :------- | :------------------------------- |
>| status   | int      | 返回结果状态。0：正常；1：错误。 |
>| errMsg   | string   | 错误描述|
>|captcha|string|验证码 ,type为1、2时返回|
>|validity|int|有效期秒数，type为1、2时返回|

###### 接口示例
>地址： /api/user/send
```json
{
	"errMsg": "",
	"status": 0,
	"captcha": "0356",
	"validity": 90
}
```

#### 8、文案相关

##### 8-01获取服务条款

###### 接口功能

> 获取服务条款

###### URL

> /api/support/terms

###### 支持格式

> JSON

###### HTTP请求方式

> GET

###### 请求参数

> | 参数        | 必选 | 类型     | 说明     |
> | ----------- | ---- | -------- | -------- |
> | sign       	 | true	| string   | 签名 |
>

###### 响应参数
>| 返回字段 | 字段类型 | 说明                             |
>| :------- | :------- | :------------------------------- |
>| status   | int      | 返回结果状态。0：正常；1：错误。 |
>| errMsg   | string   | 错误描述|
>|info|object|条款信息|
>|-termsNo|string|条款编号|
>|-title|string|标题|
>|-content|stirng|内容|
>

###### 接口示例
>地址： /api/support/terms
```json
{
	"errMsg": "",
	"status": 0,
	"info": {
		"termsNo":"",
		"title":"",
		"content":""
	}
}
```

##### 8-02 获取法务支持

###### 接口功能

> 获取法务支持

###### URL

> /api/support/legal

###### 支持格式

> JSON

###### HTTP请求方式

> GET

###### 请求参数

> | 参数        | 必选 | 类型     | 说明     |
> | ----------- | ---- | -------- | -------- |
> | sign       	 | true	| string   | 签名 |
>

###### 响应参数
>| 返回字段 | 字段类型 | 说明                             |
>| :------- | :------- | :------------------------------- |
>| status   | int      | 返回结果状态。0：正常；1：错误。 |
>| errMsg   | string   | 错误描述|
>|info|object|条款信息|
>|--legalNo|string|条款编号|
>|--title|string|标题|
>|--content|stirng|内容|
>
###### 接口示例
>地址： /api/support/legal
```json
{
	"errMsg": "",
	"status": 0,
	"info": {
		"legalNo":"",
		"title":"",
		"content":""
	}
}
```

##### 8-03 获取求购文案

###### 接口功能

> 获取求购文案

###### URL

> /api/support/buydoc

###### 支持格式

> JSON

###### HTTP请求方式

> GET

###### 请求参数

> | 参数        | 必选 | 类型     | 说明     |
> | ----------- | ---- | -------- | -------- |
> | sign       	 | true	| string   | 签名 |
>

###### 响应参数
>| 返回字段 | 字段类型 | 说明                             |
>| :------- | :------- | :------------------------------- |
>| status   | int      | 返回结果状态。0：正常；1：错误。 |
>| errMsg   | string   | 错误描述|
>| info|object|条款信息|
>|-docNo|string|文案编号|
>|-title|string|标题|
>|-content|stirng|内容|
>
###### 接口示例
>地址： /api/support/buydoc
```json
{
	"errMsg": "",
	"status": 0,
	"info": {
		"docNo":"",
		"title":"",
		"content":""
	}
}
```

#### 签名

###### 签名方式

###### 参数

> |参数|说明|
|:----- |-----|
|请求参数|除去sign，参数按ASCII表顺序排序后，使用拼接“&”|
|timestamp|追加参数1，时间戳|
|key|追加参数2,   密钥|
>  sha256(请求参数&timestamp&key)

###### 签名示例

```
    private static void sign() {
        //|参数名|必选|类型|描述|
        //|userId    |true    |long     |用户id|
        //|name      |false   |string   |用户名|
        //|nickname  |false   |string   |昵称|
        //|birthday  |false   |string   |生日|
        //|photoHead |false   |string   |头像|
        //|intro     |false   |string   |简介|
        //|sign      |true    |string   |签名|
        String[] params = {
                "userId=1",
                "name=test",
                "nickname=测试",
                "birthday=",
                "photoHead=",
                "intro=nick day"
        };

        Arrays.sort(params);

        String sorted = StringUtils.join(params,"&");
        //追加时间戳
        sorted += "&"+System.currentTimeMillis();
        //追加key
        sorted += "&6ca71c636f5475e8bf2b5860267ef0e9";

        String sign = Hashing.sha256().newHasher()
                .putString(sorted, Charsets.UTF_8)
                .hash().toString();
        System.out.println(sign);
    }
```