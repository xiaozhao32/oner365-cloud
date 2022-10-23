1.构建jdk
	拷贝jdk到当前目录. （jdk-8u131-linux-x64.tar.gz）
	修改dockerfile中对应jdk解压后的名称. （jdk1.8.0_131）
	docker build -t jdk:1.8 .

2.构建工具 mysql nginx redis rabbitmq elasticsearch minio nacos
	分开构建是可以多项目共同使用 安装可参考 doc/部署文档 或k8s部署
		
3.构建项目
    运行deploy项目中单元测试 生成到本地目录和服务器
	docker-compose up -d