%
%DEMO_WFDB_TOOLS script to demo some of the WFDB Tools
%
% Copyright (c) 2009 by Michael Craig, All Rights Reserved
% Contact M. Craig (mic@mit.edu)
%
%    This program is free software; you can redistribute it and/or modify
%    it under the terms of the GNU General Public License as published by
%    the Free Software Foundation; either version 2 of the License, or
%    (at your option) any later version.
%
%    This program is distributed in the hope that it will be useful,
%    but WITHOUT ANY WARRANTY; without even the implied warranty of
%    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%    GNU General Public License for more details.
%
%    You should have received a copy of the GNU General Public License
%    along with this program; if not, write to the Free Software
%    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
%    02111-1307  USA
%
% demo_wfdb_tools.m and its dependencies are freely available from Physionet -
% http://www.physionet.org/ - please report any bugs to the authors
% above.

% this is a slight hack to make sure the 'util' dir. is included in
% the path before we proceed; this is necessary when this script is
% launched as the web-start installer finishes, but the matlab path
% wasn't set up properly -- in that case, we execute with the
% dir. holding this script as the current working dir., so
% that we can still run it; but we need the 'util' subdir., too.
try
  d = which('demo_wfdb_tools');
  i = strfind(d, 'demo_wfdb_tools');
  if size(i,2) == 0 || i(size(i,2)) == 1
    % couldn't find out the dir. holding this; not sure why;
    % just try to add the relative 'util' dir
    addpath('util');
  else
    addpath([d(1, 1:i(size(i,2))-1),'util']);
  end
catch
  try
    addpath('util');
  catch
  end
end

%
RECORD = 'mitdb/100'
ANNOTATOR = 'atr'

%
disp(['Loading 10 seconds of record ',RECORD,' ...']);
data10 = rdsamp(RECORD, 'begin', '00:00:00', 'stop', '00:00:10');

%
disp(['Loading 10 seconds of annotations from ',ANNOTATOR, ...
      ' for record ',RECORD,' ...']);
anns10 = rdann(RECORD, ANNOTATOR, 'start', '00:00:00', 'stop', ...
    '00:00:10', 'concise');

%
disp(['Finding the frequency of ',RECORD,' using wfdbdesc ...']);
desc = wfdbdesc(RECORD, false);
freq = desc.samplingFrequency;

% just a safety measure
if freq == 0
  freq = 1;
end

%
disp('Plotting the first signal in the record ...');
plot(data10(:,1)/freq, data10(:,2), 'b')

%
disp('Plotting the annotations (as magenta diamonds) ...');
hold on;
plot(anns10(:,2)/freq, data10(anns10(:,2)+1, 2), 'md', ...
    'MarkerSize', 8);
hold off;

%
xlabel('time (s)');
ylabel('ECG amplitude (mV)');
legend('ECG', 'atr');

