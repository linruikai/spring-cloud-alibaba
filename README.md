# 微服务多环境灰度
多分支协同开发模式下，每一个feature分支对应一批开发同学，协同开发面临最严重的就是环境稳定性问题。经常因为环境问题而导致开发测试协调困难，影响项目进度的情况常常发生，因此解决环境冲突问题迫在眉睫。

![git分支管理](https://p6-xtjj-sign.byteimg.com/tos-cn-i-73owjymdk6/64c5bddc164b4698ae592654188f12df~tplv-73owjymdk6-watermark.image?rk3s=f64ab15b&x-expires=1722157155&x-signature=%2FZOVeuwQtQ02aavfN1I7tdNN2PM%3D)
# 解决方案
## 物理环境隔离
通过增加机器搭建流量隔离环境，部署服务的灰度版本。这种方式适用于测试、预发开发环境，但成本较高，灵活性不足
![物理环境隔离](https://p6-xtjj-sign.byteimg.com/tos-cn-i-73owjymdk6/25341ab88625406ba218a1d030fce93f~tplv-73owjymdk6-watermark.image?rk3s=f64ab15b&x-expires=1722157155&x-signature=lShS%2BaxsKghj0U9ZJfgKJfNECY0%3D)
## 逻辑环境隔离
![逻辑环境隔离](https://p6-xtjj-sign.byteimg.com/tos-cn-i-73owjymdk6/3f3975b1e3044350b1af5b7fbf6c0d2d~tplv-73owjymdk6-watermark.image?rk3s=f64ab15b&x-expires=1722157155&x-signature=ESls4BOGsRgevTH%2Bhu%2Fbr2dr%2FUI%3D)
## 理想状态
![image3.png](https://p6-xtjj-sign.byteimg.com/tos-cn-i-73owjymdk6/cc06c1dc34ba4c2cb45fb6c62614123c~tplv-73owjymdk6-watermark.image?rk3s=f64ab15b&x-expires=1722157155&x-signature=8vuSK2RWYY2iXzH1KPdJSk1cQc0%3D)
# 面临挑战
- 实例打标
- 流量染色
- 服务路由
- 数据库，redis，消息中间件流量路由
- DevOps
- 全链路追踪，监控

针对以上问题，每一个点都是值得探讨和研究的，涉及到的技术细节跟需要权衡对比。

# 实现文章链接
[微服务多环境灰度](https://juejin.cn/post/7393533296241754150)
