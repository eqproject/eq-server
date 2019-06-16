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
|status   |int    |返回结果状态。200：正常；302：错误。   |
|errMsg   |string    |错误描述   |
|data   |object    |  数据 |
|-userId |long|用户ID|

###### 接口示例

> 地址： [/api/user/register](/api/user/register)
```
{
	"status": 200,
	"errMsg": "",
	"data":{
		"userId": 1
	}
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
|pwd      |true    |string  | 密码(AES128) |
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。200：正常；302：错误。   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址： [/api/user/reset](/api/user/reset)
```
{
	"status": 200,
	"errMsg": ""
}
```

##### 1-03\.用户信息维护接口

###### 接口功能
>  用户信息维护接口

###### URL
> [/api/user/modify](/api/user/info/modify)

###### 支持格式
> JSON

###### HTTP请求方式
> POST

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----|
|id      |true    |long  | 用户id|
|name      |false    |string  | 用户名|
|nickname   |false    |string   |昵称|
|birthday   |false    |string   |生日|
|photoHead   |false    |string   |头像|
|intro   |false    |string   |简介|
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。200：正常；302:错误   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址： [/api/user/modify](/api/user/reset)
```
{
	"status": 200,
	"errMsg": ""
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
|mobile      |true    |string  | 用户id|
|pwd      |true    |string  | 密码(AES128) |
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。200：正常，302：错误   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址： [/api/user/login](/api/user/reset)
```
{
	"status": 200,
	"errMsg": ""
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
|identityCard       |true    |string  | 身份证号（AES128） |
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。200：正常；302：错误。   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址： [/api/user/identity/verify](/api/user/identity/verify)
```
{
	"status": 0,
	"errMsg": ""
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
|identityNo       |true    |string  | 绑定支付账号对应证件号（AES128） |
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。200：正常；302：错误。   |
|errMsg   |string    |错误描述   |

###### 接口示例

> 地址：[/api/user/pay/bind](/api/user/pay/bind)
```
{
	"status": 200,
	"errMsg": ""
}
```

##### 1-07\.用户信息查询

###### 接口功能

> 获取用户信息

###### URL

> [/api/user/getInfo](/api/user/getInfo)

###### 支持格式

> JSON

###### HTTP请求方式

> GET

###### 请求参数

> | 参数   | 必选 | 类型   | 说明   |
> | :----- | :--- | :----- | ------ |
> | userId | true | long   | 用户id |
> | sign   | true | string | 签名   |

###### 返回字段

> | 返回字段 | 字段类型 | 说明                                 |
> | :------- | :------- | :----------------------------------- |
> | status   | int      | 返回结果状态。200：正常；302：错误。 |
> | errMsg   | string   | 错误描述                             |
> | data     | Object   | user                                 |

###### 接口示例

> 地址：[/api/user/getInfo](/api/user/getInfo)

```json
{
    "status": "200",
    "errMsg": "",
    "data": {
        "id": 2,
        "name": "张木子",
        "nickname": "今晚吃鸡",
        "password": null,
        "txPassword": null,
        "sex": null,
        "level": null,
        "mobile": "13345895682",
        "authStatus": 1,
        "birthday": null,
        "photoHead": null,
        "intro": null,
        "createDate": null,
        "updateDate": "2019-06-09",
        "remarks": "更改禁用状态:启用此用户",
        "delFlag": 1,
        "address": null
    },
    "datas": null
}
```





#### 2\.商品接口

##### 2-00 导入商品信息（L）

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



##### 2-01 获取平台当前可交易商品信息（L）

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
>| total |int | 总共多少商品 |
>| list |Object[]    |商品详情   |
>productDatas 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|id|true|主键 |
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
      	    "id":"1",
    		"productName":"京东E卡",
     		"unitPrice":12,
     		"img":"htttp://pic.ka.png",
     		"desc":"仅限京东平台使用",
     		"receive":"提货说明",
     		"expirationStart":"2019-05-31",
     		"expiration_end":"2022-05-31"
   		}
   ]
 }
}
```



##### 2-02 获取用户可售卖商品

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
>| total |int    | 总共个数 |
>| list   |Object[]    |商品详情   |
>list 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> |id|true|商品id |
> | productName     | true | 商品名称 |
> | unitPrice       | true | 单价 |
> | img             | true | 图片地址 |
> | desc            | true | s商品描述 |
> | receive         | true | 提货说明 |
> | expirationStart | true | 有效期 |
> | expirationEnd   | true | 过期时间 |
> 
###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "total":1,
   "list":[
      	{
    		"id":1,
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



##### 2-03 获取商品详情（L）

###### 接口功能

>  获取商品信息，不包含敏感信息

###### URL

> /api/product/platform/details

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | userId    | true  | 整型 | 用户ID                          |
> | id | true | 整型   | 商品id |
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
> | id      | 整型 | 商品id   |
> | productName     | 字符串 | 商品名称     |
> | unitPrice       | 整形   | 面值         |
> | img             | 字符串 | 商品图片     |
> | acceptName           | 字符串 |承兑商名称       |
> | acceptImg         | 字符串 | 承兑商图片     |
> | acceptAddress        | 字符串 | 承兑商地址 |
> | acceptIntro         | 字符串 | 承兑商简介|
> | issuerName           | 字符串 |发行商名称     |
> | issuerImg         | 字符串 | 发行商图片     |
> | issuerAddress        | 字符串 | 发行商地址 |
> | issuerIntro         | 字符串 | 发行商简介|
> | desc            | 字符串 | 商品介绍     |
> | receive          | 字符串 | 商品提货说明 |
> | expirationStart | 字符串 | 生效时间     |
> | expirationEnd    | 字符串 | 过期时间     |
> | number    | 字符串 | 持有量     |

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
         "id":"1111",
    		"productName":"京东E卡",
     		"unitPrice":12,
     		"img":"htttp://pic.ka.png",
            "acceptName":"京东",
            "acceptImg":"http://jindong.png",
            "acceptAddress":"北京",
            "acceptIntro":"京东简介",
            "issuerName":"发行商京东",
            "issuerImg":"http://jindong.png",
            "issuerAddress":"北京",
            "issuerIntro":"京东简介",
     		"desc":"仅限京东平台使用",
     		"receive":"提货说明",
     		"expirationStart":"有效期开始时间",
     		"expiration_end":"有效期结束时间",
     		"number":100
   		
     }
}
```



##### 2-04  查询用户非持有券详情（L）

###### 接口功能

>  获取用户非持有券，一定是无效的券，如果有效则返回错误信息，此接口更偏向于无效列表点击详情

###### URL

> /api/product/user/noHold

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数        | 必选  | 类型   | 说明                               |
> | ----------- | ----- | ------ | ---------------------------------- |
> | id | true | 整型 | 商品ID |
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
>| id      | 整型| 商品id    |
>| productName     | 字符串 | 商品名称     |
>| unitPrice       | 整形   | 面值         |
>| img             | 字符串 | 商品图片     |
>| acceptName | 字符串 | 承兑商  |
>| acceptImg | 字符串 | 承兑商图片  |
>| acceptAddress | 字符串 | 承兑商地址 |
>| acceptIntro | 字符串 | 商家简介 |
>| acceptMobile | 字符串 | 承兑商电话 |
>| issuerName | 字符串 | 发行商 |
>| issuerImg | 字符串 | 发行商图片 |
>| issuerAddress | 字符串 | 发行商地址 |
>| issuerIntro | 字符串 | 发行商简介 |
>| desc            | 字符串 | 商品介绍     |
>| receive          | 字符串 | 商品提货说明 |
>| expirationStart | 字符串 | 生效时间     |
>| expirationEnd    | 字符串 | 过期时间     |

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   
        "id": 5,
        "productName": "下线的卡",
        "unitPrice": 12,
        "img": "b66a.jpg",
        "desc": null,
        "receive": null,
        "expirationStart": "2019-05-31",
        "expirationEnd": "2022-05-31",
        "sort": 5,
        "acceptName": "承兑商京东",
        "acceptImg": "承兑商京东图片",
        "acceptAddress": "承兑商京东地址",
        "acceptIntro": "承兑商京东简介",
        "acceptMobile": "承兑商热线电话",
        "issuerName": "发行商京东",
        "issuerImg": "发行商京东图片",
        "issuerAddress": "发行商京东地址",
        "issuerIntro": "发行商简介",
        "number": 0,
        "lockedNum": 0
 }
}
```



##### 2-06\. 查询自己持有券详情接口（L）

###### 接口功能

> 查询自己持有券详情接口,一定是用户持有的，并且有效的

###### URL
> [/api/product/user/detail](/api/user/product/detail)

###### 支持格式

> JSON

###### HTTP请求方式

> GET

###### 请求参数
> | 参数        | 必选 | 类型   | 说明     |
> | :---------- | :--- | :----- | -------- |
> | id | true | long | 商品id |
> | userId      | true | string | 用户ID   |
> | sign        | true | string | 签名     |

###### 返回字段
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|status   |int    |返回结果状态。0：正常；1：错误。   |
>|errMsg   |string    |错误描述   |
>|data  |Object | 参考对象           |
>data 对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| id      | 整型| 商品id    |
>| productName     | 字符串 | 商品名称     |
>| unitPrice       | 整形   | 面值         |
>| img             | 字符串 | 商品图片     |
>| acceptName | 字符串 | 承兑商  |
>| acceptImg | 字符串 | 承兑商图片  |
>| acceptAddress | 字符串 | 承兑商地址 |
>| acceptIntro | 字符串 | 商家简介 |
>| acceptMobile | 字符串 | 承兑商电话 |
>| issuerName | 字符串 | 发行商 |
>| issuerImg | 字符串 | 发行商图片 |
>| issuerAddress | 字符串 | 发行商地址 |
>| issuerIntro | 字符串 | 发行商简介 |
>| desc            | 字符串 | 商品介绍     |
>| receive          | 字符串 | 商品提货说明 |
>| expirationStart | 字符串 | 生效时间     |
>| expirationEnd    | 字符串 | 过期时间     |
>
###### 接口示例

> 地址： [/api/user/product/detail](/api/user/product/detail)

```
{
	"errMsg": "",
	"status": 0,
	"data": {
			 "id": 1,
        "productName": "京东E卡",
        "unitPrice": 10000,
        "img": "d65c1038b66a.jpg",
        "desc": "E卡说明",
        "receive": "京东E卡提货说明",
        "expirationStart": "2019-05-31",
        "expirationEnd": "2022-05-31",
        "sort": 2,
        "acceptName": "承兑商京东",
        "acceptImg": "承兑商京东图片",
        "acceptAddress": "承兑商京东地址",
        "acceptIntro": "承兑商京东简介",
        "acceptMobile": "承兑商热线电话",
        "issuerName": "发行商京东",
        "issuerImg": "发行商京东图片",
        "issuerAddress": "发行商京东地址",
        "issuerIntro": "发行商简介",
        "number": 1000,
        "lockedNum": 132

    }
}
```
#### 3、广告订单

##### 3-01 创建广告订单接口（L）

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
> | productId | true | 整型 | 商品id |
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



##### 3-02  关闭订单（L）

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



##### 3-03  获取集市订单数据（L）

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
> | pageSize    | true  | 整形   | 每页大小                           |
> | pageNum | true | 整形 | 当前页 |
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
>|total|int|总共多少个 |
>| list  |Object[]  |广告订单集合  |
>Object对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| orderCode    | 字符串 | 订单号    |
>| productName     | 字符串 | 商品名称     |
>| img             | 字符串 | 商品图片     |
>| price | 整形   | 销售单价         |
>| orderNumber | 整形   | 订单总量     |
>|saleedNumber | 整形   |已售卖量    |
>|saleNumber | 整形   |可交易量   |
>| title | 字符串  | 订单标题       |
>| userId            | 字符串 | 用户Id     |
>| userImg         | 字符串 | 用户头像|
>| userBoundState  |整型 | 1:仅支付宝 2:微信 3:支付宝微信均绑定|
>

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "total":20,
   "list":[
     {
         "orderCode": "AD20190602171926308704",
         "productId": "1",
         "productName": "京东E卡",
        "img": "https://gss0.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=6d06d91632a85edffad9f6257964251b/37d3d539b6003af3ebb06c26332ac65c1038b66a.jpg",
        "price": 10,
        "orderNumber": 10,
        "saleedNumber": 3,
       "saleNumber": 7,
        "title": "便宜售卖1",
        "userId": 1,
        "userImg": null,
         "nickName": "你好呀",
         "userBoundState": 0
      }
   ]
 }
}
```



##### 3-04   获取我的订单(L)

###### 接口功能

>   获取用户个人发布中的广告订单

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
> | pageNum | true | 整形 | 当前页 |
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
>|total|int|总共多少 |
>| list  |Object[]  |广告订单集合  |
>Object对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>| orderCode    | 字符串 | 订单号    |
>| productName     | 字符串 | 商品名称     |
>| orderType     |整型 | 订单类型     |
>| img             | 字符串 | 商品图片     |
>| price | 整形   | 订单单价 |
>| orderNumber | 整形   | 订单总量     |
>|saleedNumber | 整形   |已完成量    |
>|saleNumber | 整形   |可交易量   |
>| title | 字符串  | 订单标题       |
>| userId            | 字符串 | 用户Id     |
>| userImg | 字符串 | 用户头像 |
>| nickName | 字符串 | 用户昵称 |
>| orderType | 整形 | d订单类型(1:出售广告2:求购广告) |

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "total":20
   "list":[
      {
         "orderCode": "AD20190602171926304504",
        "productId": "1",
         "productName": "京东E卡",
         "img": "b66a.jpg",
         "price": 100,
         "orderNumber": 10,
         "saleedNumber": 4,
         "saleNumber": 6,
        "title": "测试正在进行中订单",
          "userId": 1,
          "userImg": "用户头像地址",
           "nickName": "齐木卡卡西",
          "userBoundState": 0,
           "orderType": 1
            }
   ]
 }
}
```

##### 3-05  获取订单详情（L）

###### 接口功能

> 获取平台订单详情

###### URL

> /api/adOrder/plat/details

###### 支持格式

> JSON

###### HTTP请求方式

> POST

###### 请求参数

> | 参数      | 必选  | 类型   | 说明       |
> | --------- | ----- | ------ | ---------- |
> | orderCode | true  | 字符串 | 广告订单号 |
> | userId    | false | 字符串 | 用户ID     |
> | sign      | true  | 字符串 | 平台签名   |

###### 响应参数

> | 返回字段 | 字段类型 | 说明                             |
> | :------- | :------- | :------------------------------- |
> | status   | int      | 返回结果状态。0：正常；1：错误。 |
> | errMsg   | string   | 错误描述                         |
> | data     | Object   | 参考对象                         |
>
> Object对象
> |返回字段|字段类型|说明                              |
> |:-----   |:------|:-----------------------------   |
> | orderCode    | 字符串 | 订单号    |
> | productName     | 字符串 | 商品名称     |
> | orderType     |整型 | 订单类型     |
> | img             | 字符串 | 商品图片     |
> | price | 整形   | 订单单价 |
> | orderNumber | 整形   | 订单总量     |
> |saleedNumber | 整形   |已完成量    |
> |saleNumber | 整形   |可交易量   |
> | title | 字符串  | 订单标题       |
> | userId            | 字符串 | 用户Id     |
> | userImg | 字符串 | 用户头像 |
> | nickName | 字符串 | 用户昵称 |
> | orderType | 整形 | d订单类型(1:出售广告2:求购广告) |
> | createTime | 字符串 | 创建时间 |
> | userBoundState | 整形 | 2:已经认证 |
> | tradeNum | 整形 | 交易订单数 |
> | tradeRate | 小数 | 交易完成率 |
###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
     "orderCode": "AD20190602171926304504",
     "productId": "1",
     "productName": "京东E卡",
     "img": "b66a.jpg",
     "price": 100,
     "orderNumber": 10,
     "saleedNumber": 4,
     "saleNumber": 6,
     "title": "测试正在进行中订单",
     "userId": 1,
     "userImg": "用户头像地址",
     "nickName": "齐木卡卡西",
     "userBoundState": 0,
     "orderType": 1,
     "createTime": "2019-06-02 17:19:27",
     "tradeNum": 9,
     "tradeRate": 0
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
|buyUserId|true|long  |购买用户id|
|adNo|true|string  |广告订单号|
|productId|true| long |商品id|
|orderNum|true|int  |订单数量|
|salePrice |true|int  |商品售价(单位:分)|
|serviceFee|true|int  |服务费(单位:分)|
|type|true|int|订单类型:(1:出售;2:求购)|
|remarks|false|string|交易描述|
|sign|true|string|签名|

###### 返回字段
> |返回字段|字段类型|说明   |
|:-----   |:-----------|:-------------------------|
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string |错误描述|
|data |object|结果对象|
|-- tradeNo |string| 交易订单号|


###### 接口示例

> 地址： [/api/order/trade/create](/api/order/trade/create)

```
{
	"errMsg": "",
	"status": 0,
	data:{
	   "tradeNo":"woeuirjk1010038388"
	}
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
|tradeNo |true|string |交易订单编号|
|sign|true|string|签名|

###### 返回字段
> |返回字段|字段类型|说明   |
|:-----   |:-----------|:-------------------------|
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string |错误描述   |
|data |object|结果对象|
|-- tradeNo |string| 交易订单号|


###### 接口示例

> 地址： [/api/order/trade/cancel](/api/order/trade/cancel)

```
{
	"errMsg": "",
	"status": 0,
	 data:{
	   "tradeNo":"woeuirjk1010038388"
	}
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
> POST

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
|data |object |交易订单商品详情                        |
|-    product |object |商品信息 |
|--        img |string |图片url |
|--        productName |string |名称|
|--        unitPrice |int |面值(单位:分)|
|-    user |object |用户信息 |
|--        sellUserId |long |售卖用户id|
|--        sellUserNickName | string |卖家昵称|
|--        sellUserName | string |卖家名称|
|--        sellUserAccount |string|卖家收款账户|
|--        buyUserId |long |买家用户id|
|--        buyUserNickName | string |买家昵称|
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

```
{
	"errMsg": "",
	"status": 0,
	"data": {
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
			"buyUserId": 2,
			"buyUserNickName": "买家昵称",
			"sellUserNickName": "卖家昵称",
			"sellUserId": 1,
			"sellUserName":"苏大强",
			"sellUserAccount":"bitqtZ***ekjkd.com"
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
> POST

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

##### 4-05\.查询待付款交易订单列表接口

###### 接口功能
>  查询待付款交易订单列表接口

###### URL
> [/api/order/trade/paying/list](/api/order/trade/paying/list)

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
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |
|data   |Object|返回结果对象|
|- total  |int | 订单总记录数|
|- list |list |交易订单列表|
|--    product |object |商品信息 |
|---        productImg |string |图片url |
|---        name |string |名称|
|---        unitPrice |int |面值(单位:分)|
|--    user |object |用户信息 |
|---        sellUserId |long |售卖用户id|
|---        sellUserNickName | string |卖家昵称|
|---        buyUserId |long |买家用户id|
|---       buyUserNickName | string |买家昵称|
|--    trade |object |交易订单信息 |
|---        tradeNo |string |交易订单号|
|---        payNo |string |支付流水号|
|---        amount |string |商品售卖价格(单位:分)|
|---        orderNum |int |订单数量|
|---        salePrice |int |商品售价(单位:分)|
|---        serviceFee |int |服务费(单位:分)|
|---        status |int |状态:(1:待支付)|
|---        createTime |string |交易时间 |

###### 接口示例

> 地址：[/api/order/trade/paying/list)
```
{
	"errMsg": "",
	"status": 0,
	"data":{
		"total":1,
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
					"orderNum": 3,
					"salePrice": 100,
				   "serviceFee":1,
					"status":1
				},
				"user": {
				      "buyUserId": 2,
				      "buyUserNickName": "买家昵称",
				      "sellUserNickName": "卖家昵称",
				      "sellUserId": 1
				}
			}
		]
	}
}
```

##### 4-06\.查询进行中交易订单列表接口

###### 接口功能
>  查询进行中交易订单列表接口

###### URL
> [/api/order/trade/porcessing/list](/api/order/trade/porcessing/list)

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
|sign       |true    |string   |签名|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|errMsg   |string    |错误描述   |
|data   |object|结果对象 |
|- total  |int | 订单总记录数|
|- list |list |交易订单列表                        |
|--    product |object |商品信息 |
|---        productImg |string |图片url |
|---        name |string |名称|
|---        unitPrice |int |面值(单位:分)|
|--    user |object |用户信息 |
|---        sellUserId |long |售卖用户id|
|---        sellUserNickName | string |卖家昵称|
|---        buyUserId |long |买家用户id|
|---        buyUserNickName | string |买家昵称|
|--    trade |object |交易订单信息 |
|---        tradeNo |string |交易订单号|
|---        payNo |string |支付流水号|
|---        amount |int |商品售卖价格(单位:分)|
|---        orderNum |int |订单数量|
|---        salePrice |int |商品售价(单位:分)|
|---        serviceFee |int |服务费(单位:分)|
|---        status |int |状态:(4:支付成功;5:支付失败;6:区块链处理中;7:放款中;8:放款失败;)|
|---        createTime |string |交易时间 |
|---        payTime |string |支付完成时间 |

###### 接口示例

> 地址：[/api/order/trade/porcessing/list)
```
{
	"errMsg": "",
	"status": 0
	"data":{
	   "total":1,
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
						"status":4
					},
					"user": {
						"buyUserId": 2,
					    "buyUserNickName": "买家昵称",
					    "sellUserNickName": "卖家昵称",
					    "sellUserId": 1
					}
				}
	 	]
	}
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
|data   |object|结果对象 |
|- total  |int | 订单总记录数|
|- list |list |交易订单列表                        |

订单对象
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|productImg |string |图片url |
|productName |string |商品名称|
|unitPrice |int |面值(单位:分)|
|tradeNo |string |交易订单号|
|orderNo |string |订单号|
|amount |int |订单金额(单位:分)|
|orderNum |int |订单数量|
|salePrice |int |商品售价(单位:分)|
|serviceFee |int |服务费(单位:分)|
|status |int |状态:(1:已完成;)|
|createTime |string |交易时间 |
|finishTime |string |完成时间 |
|type |string |订单类型:(1:广告出售订单;2:广告求购订单;3:交易出售订单;4:交易求购订单)|

###### 接口示例

> 地址：[/api/order/trade/finish/list](/api/order/trade/finish/list)
```
{
	"errMsg": "",
	"data": {
		"total": 1,
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
					"serviceFee": 1,
					"status": 1,
					"type": 1
				}
			}
		]
	},
	"status": 0
}
```

#### 5 用户券包

##### 5-01 获取用户有效券(L)

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
>| total |int    | 总共多少 |
>| list   |Object[]    |商品详情   |
>Object对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
> | productId     | true | 商品ID |
> | productName     | true | 商品名称 |
> | unitPrice       | true | 单价 |
> | img             | true | t图片 |
> | number            | true | 持有数量 |
> | effectNumber            | true | 有效量 |
> | lockNumber            | true | 锁定量 |
> 

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "total":100,
   "list":[
     {
      "productId": 1,
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



##### 5-02  转出用户券(L)

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
> | productId | true | 字符串 |商品ID|
> | number          | true | 整型 |转让数量|
> | address          | true | 字符串 |目标用户地址|
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
>| transCode |String |转出订单号  |
>| userId |long |用户ID |
>| productId |long |商品ID |
>| transNumber |int |交易数量 |
>

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
    "userId": 1,
    "productId": 1,
     "transCode": "TS20190605012635468811",
     "transNumber": 1
 }
}
```



##### 5-03  兑换用户商品(L)

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
> | productId | true | 整型 |商品ID|
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
>| acceptCode |String |承兑订单号  |
>| productName     | 字符串 | 商品名称     |
>| img             | 字符串 | 商品图片     |
>| number | 整形   |承兑数量     |
>| consignee        | true | 字符串 |
>| consigneePhone          | true | 字符串 |
>| consigneeAddress         | true | 字符串 |

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



##### 5-04  兑换列表（L）

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
> | pageSize | true | 整型 | 每页大小 |
> | pageNum | true | 整型 | 页号 |
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
>|total|int|总共条数 |
>| list  |Object[]  |承兑订单集合  |
>Object对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|acceptCode    | 字符串 | 订单号    |
>| productName     | 字符串 | 商品名称     |
>| img             | 字符串 | 商品图片     |
>| unitPrice | 整形   | 商品面值      |
>| number | 整形   | 承兑数量     |
>

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
    "total":200
   "list"：[
     {
   	 	"acceptCode":"M212313411",
   		"productName":"E卡",
   	   "img":"http://e.png",
   	   "unitPrice":10,
   		"number":1
     }
   ]
 }
}
```



##### 5-05   转出商品列表（L）

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
> | pageSize | true | 整型 | 每页大小 |
> | pageNum | true | 整型 | 当前页 |
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
>|total|int|总共多少条 |
>| list  |Object[]  |承兑订单集合  |
>Object对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|turnoutCode    | 字符串 | 订单号    |
>| productName     | 字符串 | 商品名称     |
>| img             | 字符串 | 商品图片     |
>| unitPrice | 整形   | 商品面值      |
>| transNumber | 整形   | 转出数量     |
>| stateRemark | 字符串 | 状态描述 |
>
###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "total":200,
   "list";[
     {
   	 	"transCode":"M212313411",
   		"productName":"E卡",
   	   "img":"http://e.png",
   	   "unitPrice":10,
   		"transNumber":1,
   		"stateRemark": "等待确认",
     }
   ]
  
 }
}
```



##### 5-06 失效商品列表(L)

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
> | pageNum | true | 整型 | 当前页 |
> | pageSize | true | 整型 | 每页大小 |
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
>|total|int|总数 |
>| list  |Object[]  |承兑订单集合  |
>Object对象
>|返回字段|字段类型|说明                              |
>|:-----   |:------|:-----------------------------   |
>|productId|整型|商品ID |
>| productName     | 字符串 | 商品名称     |
>| img             | 字符串 | 商品图片     |
>| unitPrice | 整形   | 商品面值      |
>| number | 整形   | 商品数量     |
>| overdueReason | 字符串 | 失效原因 |
>

###### 返回实例

```
{
 "status":1,
 "errMsg":"",
 "data":{ 
   "total":300
   "list"：[
     {
   		 "userId": 1,
          "productId": 5,
          "productName": "下线的卡",
           "img": "038b66a.jpg",
           "number": 24,
           "unitPrice": 12,
           "overdueReason": "已失效",
           "sort": 5
     }
   ]
  
 }
}
```



##### 5-07   承兑订单详情（L）

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
> | unitPrice | 整形   | 商品面值      |
> | number | 整形   | 商品数量     |
> | stateRemak | 字符串 | 状态描述  |
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
     "userId": 1,
        "productId": 1,
        "acceptCode": "AS20190605020737853317",
        "productName": "京东E卡",
        "img": "http://539b6003af3ebb06c26332ac65c1038b66a.jpg",
        "number": 10,
        "consignee": "齐木卡卡西",
        "consigneePhone": "110",
        "consigneeAddress": "承兑人地址",
        "unitPrice": 10000,
        "acceptModile": "承兑商热线电话",
        "stateRemak": "承兑中"
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
> | type	 | true	| int 	| 短信类型：1,登陆注册验证码2,发布广告成功通知3, 数字券已转到券包通知"4, 数字券已成功转出通知5,订单确认通知|
> | sign       	 | true	| string   | 签名 |

###### 响应参数
>| 返回字段 | 字段类型 | 说明                             |
>| :------- | :------- | :------------------------------- |
>| status   | int      | 返回结果状态。0：正常；1：错误。 |
>| errMsg   | string   | 错误描述|

###### 接口示例
>地址： /api/user/send
```json
{
    "status": "200",
    "errMsg": "",
    "data": null,
    "datas": null
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
>| status   | int      | 返回结果状态。200：正常；302：错误。 |
>| errMsg   | string   | 错误描述|
>|data|object|条款信息|
>|-content|stirng|内容|
>

###### 接口示例
>地址： /api/support/terms
```json
{
	
    "status": 200,
    "errMsg": "",
	"data": {
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
>| status   | int      | 返回结果状态。200：正常；302：错误。 |
>| errMsg   | string   | 错误描述|
>|data|object|法务信息|
>|-content|stirng|内容|
>
###### 接口示例
>地址： /api/support/legal
```json
{
	"status": 0,
    "errMsg": "",
	"data": {
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
>| status   | int      | 返回结果状态。200：正常；302：错误。 |
>| errMsg   | string   | 错误描述|
>| data|object|文案信息|
>|-content|stirng|内容|
>
###### 接口示例
>地址： /api/support/buydoc
```json
{
	"status": 0,
    "errMsg": "",
	"data": {
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
|key|追加参数1,   密钥|
sha256(请求参数&key)

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
        //追加key
        sorted += "&6ca71c636f5475e8bf2b5860267ef0e9";

        String sign = Hashing.sha256().newHasher()
                .putString(sorted, Charsets.UTF_8)
                .hash().toString();
        System.out.println(sign);
    }
```