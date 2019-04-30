# my-jmeter 定制自己的jar包

## 环境准备
### 1.mac
安装，终端执行 `brew install jmeter`

安装文件路径查看 `brew list jmeter`

返回

`
/usr/local/Cellar/jmeter/5.1.1/bin/jmeter 

 /usr/local/Cellar/jmeter/5.1.1/libexec/backups/ (20 files) 
 
 /usr/local/Cellar/jmeter/5.1.1/libexec/bin/ (179 files) 
 
 /usr/local/Cellar/jmeter/5.1.1/libexec/docs/ (2019 files) 
 
 /usr/local/Cellar/jmeter/5.1.1/libexec/extras/ (20 files) 
 
 /usr/local/Cellar/jmeter/5.1.1/libexec/lib/ (141 files) 
 
 /usr/local/Cellar/jmeter/5.1.1/libexec/licenses/ (47 files) 
 
 /usr/local/Cellar/jmeter/5.1.1/libexec/printable_docs/ (71 files) 
 
 /usr/local/Cellar/jmeter/5.1.1/libexec/serveragent/ (13 files) 
 `

打开安装文件夹

finder窗口 -> 前往 -> 前往文件夹，输入路径`/usr/local/Cellar/jmeter/5.1.1`，即可打开安装路径文件夹（后面打开软件或是应用jar包用到）
### 2.win
PS: 极力推荐 [chocolatey](https://chocolatey.org)，有兴趣的可以关注，win下的"brew"，好用！

`choco install jmeter`

win下找到安装路径so ez， 大家自行操作吧

## 汉化
找到安装路径bin下的jmeter.properties文件中的`#language=en`，打开注释并修改为`language=zh_CN`

ok，现在运行中文版的 jmeter 吧
<escape>
<table>
  <tr>
    <th>系统</th>
    <th>命令行</th>
    <th>批处理文件执行</th>
  </tr>
  <tr>
    <td rowspan="2">mac</td>
    <td rowspan="2">终端输入 jmeter</td>
    <td>/usr/local/Cellar/jmeter/5.1.1/libexec/bin/jmeter</td>
  </tr>
  <tr>
      <td>/usr/local/Cellar/jmeter/5.1.1/bin/jmeter</td>
  </tr>
  <tr>
    <td>win</td>
    <td>cmd输入 jmeter</td>
    <td>安装文件夹bin下找到jmeter.bat执行</td>
  </tr>
</table>
</escape>

## 开发自己的jar包
新建gradle工程，引入必须的jar包

`
implementation group: 'org.apache.jmeter', name: 'ApacheJMeter_java', version: '5.1.1'
implementation group: 'org.apache.jmeter', name: 'ApacheJMeter_core', version: '5.1.1'
// TCP sample
implementation group: 'org.apache.jmeter', name: 'ApacheJMeter_tcp', version: '5.1.1'
`

## 应用自己的jar包

gradle工程 build -> 生成*。jar文件 -> copy该文件到

`mac： /usr/local/Cellar/jmeter/5.1.1/libexec/lib/ext
win：安装路径\bin\ext
`

重新启动jmeter生效