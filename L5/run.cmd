@ECHO OFF

SET HADOOP_USER_NAME=hadoop

mapred streaming ^
  -input /fao_data_crops_data.csv ^
  -output /crops ^
  -mapper "py -3 %~dp0\mapper.py" ^
  -reducer "py -3 %~dp0\reducer.py"
