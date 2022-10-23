
# centos 8
cd /etc/yum.repos.d
rm -rf /etc/yum.repos.d/*
wget -O /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-vault-8.5.2111.repo
yum makecache

# docker

# commons
sudo yum -y update
sudo yum -y install gcc tar make zip glibc-devel gcc-c++
sudo yum -y install gcc glibc-devel make ncurses-devel openssl-devel autoconf unixODBC unixODBC-devel socat

# docker
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
sudo yum install -y docker-ce docker-ce-cli containerd.io

mkdir /etc/docker

cat <<EOF | tee /etc/docker/daemon.json
{
  "registry-mirrors": ["https://j5lij4el.mirror.aliyuncs.com","https://docker.mirrors.ustc.edu.cn","http://hub-mirror.c.163.com"],
  "dns":["114.114.114.114"],
  "exec-opts": ["native.cgroupdriver=systemd"],
  "log-driver":"json-file",
  "log-opts": {"max-size":"500m", "max-file":"3"}
}
EOF

# 管理多个需添加2375端口
firewall-cmd --zone=public --add-port=2375/tcp --permanent
firewall-cmd --reload

vi /usr/lib/systemd/system/docker.service
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock

sudo systemctl daemon-reload
sudo systemctl restart docker

# docker-compose
sudo curl -L "https://get.daocloud.io/docker/compose/releases/download/v2.2.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
docker-compose --version

# 查看docker中的应用
docker ps -a

# 参考网站
# docker镜像网站 https://hub.docker.com 

# 更换源
mv /etc/apt/sources.list /etc/apt/sources.list.bak
cat <<EOF >/etc/apt/sources.list
deb http://mirrors.ustc.edu.cn/debian stable main contrib non-free
deb http://mirrors.ustc.edu.cn/debian stable-updates main contrib non-free
EOF
apt-get update
apt-get install vim

# 停止删除无用的容器
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)


