# DcmManageSystem_v4
dcm4che开源库的支持下做的dcm文件管理平台
<!-- toc -->
目录
影像数据渲染平台操作手册	1
1.	系统启动步骤	3
1.1	Vmware虚拟机启动	3
1.2	医疗影像平台系统启动	3
2.	登录操作	3
3.	整体菜单功能	4
4.	用户管理	4
4.1	用户添加	4
4.2	用户列表	5
4.3	用户删除	5
5.	个人信息管理模块	5
6.	项目管理模块	6
9.1	项目列表	6
9.2	项目创建	6
9.3	项目删除	7
9.4	项目成员列表	7
9.5	项目成员添加和删除	8
7.	文件上传管理	8
10.1	dicom文件上传	8
10.2	标注文件上传	9
8.	远程桌面	9
11.1	远程桌面登录	9
11.2	新用户登录系统设置	10
11.3	Dicom文件和标注文件查看	11
<!-- tocstop -->

2.	登录操作
网址：http://192.168.1.202（由现场主机ip地址决定）
超级管理员 账号：superAdmin 密码：123456
普通管理员 帐号：admin  密码：123456
普通用户   账号：boy 密码：123456
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234630.png)
3.	整体菜单功能
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234618.png)
4.	用户管理
4.1	用户添加
管理员可以添加普通用户，超级管理员（最高权限角色）可以添加管理员和普通用户，但不能够添加用户名为root的账号（Linux系统账户）
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234726.png)
4.2	用户列表
普通用户无权查看其他用户的信息，管理员可以查看普通用户信息，只有超级管理员可以查看所有用户信息。为了安全，统一对所有密码在数据库进行加密，管理员也无法看到真实的密码。
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234742.png)
4.3	用户删除
管理员和超级管理员有权删除用户，同时也将在服务器操作系统上删除该用户

5.	个人信息管理模块
修改用户名和密码，上传个人头像
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234812.png)

6.	项目管理模块
9.1	项目列表
普通用户只能看到自己所在的项目，管理员可以看到所有项目
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234828.png)
9.2	项目创建
   任何用户都有权创建项目
 ![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234851.png)
9.3	项目删除
只有管理员有权限删除项目
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234905.png)
9.4	项目成员列表
查看项目的项目成员
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234920.png)
9.5	项目成员添加和删除
项目创建人和管理员有权限进行项目成员的添加和删除
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723234957.png)
7.	文件上传管理
10.1	dicom文件上传
dicom文件以文件夹或者是文件的形式上传，文件上传个数为200个
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723235215.png)
10.2	标注文件上传
上传标注文件以文件夹或者是文件的形式上传，文件上传个数为200个
 ![](https://raw.githubusercontent.com/weiyangtang/images/master/20190719163517.png)
8.	远程桌面
11.1	远程桌面登录
点击左侧菜单栏的远程桌面，输入医疗影像平台的账号和密码进行登录
 ![](https://raw.githubusercontent.com/weiyangtang/images/master/20190719165439.png)
11.2	新用户登录系统设置
Ubuntu系统可能会弹出一些提示选项，直接点取消即可
  ![](https://raw.githubusercontent.com/weiyangtang/images/master/20190719165716.png)
 
11.3	Dicom文件和标注文件查看
点击桌面的DATA文件
 ![](https://raw.githubusercontent.com/weiyangtang/images/master/20190719165617.png)

Dcm_Image对应影像文件，Other_File对应标注文件
 ![](https://raw.githubusercontent.com/weiyangtang/images/master/20190719165716.png)
点开.dcm文件，使用默认文件浏览器打开即可
![](https://raw.githubusercontent.com/weiyangtang/images/master/20190723235647.png)
 
