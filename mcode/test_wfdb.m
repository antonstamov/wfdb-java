%TEST_WFDB test WFD installation
%
% [success] = test_wfdb;
%
% This is a simple function to test that the WFDB-SWIG-MATLAB package was
% properly installed. Returns 1 on success, 0 on failure.
%
% See also WFDB_LICENSE
% 
% Copyright (c) 2009 by Michael Craig, All Rights Reserved
% Contact M. Craig (mic@mit.edu)
%

% Modified by Ikaro Silva, 7/2012 

%
success = 0;

try

  %
  data = rdsamp('mitdb/101', 'begin', '00:00:20', 'maxt', ...
      '00:00:10');
  
  %
  crc32 = java.util.zip.CRC32;
  for i=1:size(data,1)
    crc32.update(data(i,1));
    crc32.update(data(i,2));
    crc32.update(data(i,3));
  end
  
  if uint32(crc32.getValue) == 1801886120
    success = 1;
  else
    success = 0;
  end

catch
  
  success = 0;
  
end

% fprintf(1, '%d\n', success);



