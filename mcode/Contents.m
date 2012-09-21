%  WaveForm DataBase (WFDB) Toolbox 
%  Version Beta NA 
% 
%This is a set of MATLAB functions and wrappers for reading, writing, and processing
%files in the formats used by PhysioBank databases (among others). 
%The WFDB Toolbox has support for reading public PhysioNet databases directly from 
%web. This feature allows your code to analyze a wide range of physiological 
%signals available from PhysioBank without the need to download entire 
%records and to store them locally. For more information please go to
% http://www.physionet.org 
% http://physioforge.csail.mit.edu/wfdbtools/wfdb-swig-matlab/. 
%This source code for the library is distributed under the GPL license.
%
% 
%
%  Table of Contents (T0C)
% -----------------------
%   wfdb            - Prints this help information of the Toolbox
%   demo_wfdb_tools - Script to demo some of the WFDB Tools
%   mat2wfdb        - Writes a MATLAB variable into a WDFB record file
%   rdann           - Read annotation files for WFDB records
%   rdsamp          - Read signal files of WFDB records
%   test_wfdb       - Test WFDB installation
%   wrann           - Write annotations for WFDB records into annotation files
%   wfdb_install    - Installs, uinstalls Toolbox
%   wfdb_lincense   - Lincense information about this toolbox
%   wfdb_query      - Get information about all of PhysioNet's available databases and signals
%   wfdb_desc       - Return specifications for signals in WFDB records
%   wrsamp          - Write signal data into WFDB-compatible records
%
%
%
%   Contact: wfdb-matlab-supprt@physionet.org
%
%   Contributors:
%
%   Ikaro Silva
%   Daniel J. Scott
%   George Moody


%Created by Ikaro Silva 2012