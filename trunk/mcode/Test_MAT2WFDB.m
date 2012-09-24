clear all;close all
sig_name='aftdb/test-set-a/a05.hea';
x=rdsamp(sig_name,'sigs',1,'phys',true,'begin','00:00:00','stop','00:02:30','hires',true);
info=wfdbdesc(sig_name,1);
[xbit]=mat2wfdb(x(:,2),'test',info.samplingFrequency,16,info.groups.signals(1).units,...
sig_name,1,[],info.groups.signals(1).description);
xrecon=rdsamp('test','phys',true,'hires',true);
info2=wfdbdesc('test',1);

plot(x(:,2))
hold on;grid on
plot(xrecon(:,2),'r--')
title(['Err= ' num2str(sqrt(mean((x(:,2)-xrecon(:,2)).^2)))])


info.groups.signals(1)
info2.groups.signals(1)