clear all;clc
%!java -cp ./wfdbapp.jar org.physionet.wfdb.physiobank.PhysioNetDB
libs='/afs/ecg.mit.edu/user/ikaro/home/common_linux/workspace/wfdblibs/';
javaaddpath([pwd '/wfdbapp.jar'])
javaaddpath([libs 'commons-math3-3.0/commons-math3-3.0.jar'])
javaaddpath([libs 'jcommon-1.0.18/jcommon-1.0.18.jar'])
javaaddpath([libs 'jfreechart-1.0.14/lib/jfreechart-1.0.14.jar'])

lower(char(java.lang.System.getProperty('java.version')))

clear java
import('org.physionet.wfdb.*')
methods('PhysioNetDB')
h=javaObject('org.physionet.wfdb.PhysioNetDB')

L= import