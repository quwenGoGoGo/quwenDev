#!/bin/bash
#设置云ip
REMOTE = root@111.230.21.216

#设置本地ssh私钥文件id_rsa路径
ID_RSA = C:/id_rsa

if test -z "$REMOTE"
then
  echo "请设置云主机登录IP地址和账户"
  exit -1
fi

if test -z "$ID_RSA"
then
  echo "请设置云主机登录IP地址和账户"
  exit -1
fi