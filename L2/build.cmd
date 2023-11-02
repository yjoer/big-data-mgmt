@ECHO OFF

SET JAVA_HOME=C:\Progra~1\Eclips~1\jdk-8.0.392.8-hotspot

%JAVA_HOME%\bin\javac ^
  -cp D:\CLI\hadoop\3.3.6\etc\hadoop;D:\CLI\hadoop\3.3.6\share\hadoop\common;D:\CLI\hadoop\3.3.6\share\hadoop\common\lib\*;D:\CLI\hadoop\3.3.6\share\hadoop\common\*;D:\CLI\hadoop\3.3.6\share\hadoop\hdfs;D:\CLI\hadoop\3.3.6\share\hadoop\hdfs\lib\*;D:\CLI\hadoop\3.3.6\share\hadoop\hdfs\*;D:\CLI\hadoop\3.3.6\share\hadoop\yarn;D:\CLI\hadoop\3.3.6\share\hadoop\yarn\lib\*;D:\CLI\hadoop\3.3.6\share\hadoop\yarn\*;D:\CLI\hadoop\3.3.6\share\hadoop\mapreduce\* ^
  -d ./build ^
  MapReduceDeadlock.java

CD ./build

%JAVA_HOME%\bin\jar cvf ../MapReduceDeadlock.jar *
