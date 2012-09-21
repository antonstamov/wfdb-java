function [varargout]=wfdb_install(varargin)
%
% WFDB_INSTALL(mode)
%
%
% Script of installation and removal of the WFDB Toolbox for MATLAB. 
% Input options are:
% mode - String with the following options:
%      - 'install' Installs the WFDB Toolbox in the current version of
%        MATLAB (default option when function is called).
%      - 'remove' Removes currently installed version of the Toolbox
%      - 'setup' Get configuration parameters for the System and version of
%         the WFDB Toolbox currently (or to be) installed. Output is a
%         structure 'config' containing all parameters related to this
%         installation of the Toolbox.
%      - 'pconfig' Prints the configuration parameters for the Toolbox.
% 
%
% This scripts automates the most of the steps described in:
%
% http://www.physionet.org//physiotools/matlab/wfdb-swig-matlab/README.manual-inst
%   
%   See also WFDBLICENCSE, WFDB
% 	Copyright (c) 2012 by Ikaro Silva, All Rights Reserved
%
% Written by Ikaro Silva, 2012
%
% Version 1.0

%Currently the WFDB Toolbox requires the following JAR files:
%
% wfdb-nativelibs-{COMP}-{ARCH}.jar     Native C library
% wfdb-swig-matlab-{COMP}.jar           MATLAB Loadable Java Classes
% wfdb-swig.jar                         SWIG Generated Interface between C/Java
% wsm-matlab-code.jar                   MATLAB Files

%The intaller should take into consideration each version of these JAR
%files when performing an installation.
%
% Note: WSM is an abbreviation for WFDB-SWIG-MATLAB.
clc
mode='install';
arg1=[];
inputs={'mode','arg1'};
for n=1:nargin
    if(~isempty(varargin{n}))
        eval([inputs{n} '=varargin{n};'])
    end
end
switch mode
    case 'install'
        wfdb_installation();
    case 'remove'
        wfdb_remove();
    case 'setup'
        config=wfdb_setup();
        varargout{1}=config;
    case 'jarquery'
        info=wfdb_jarquery(arg1);
        varargout{1}=info;
    case 'pconfig'
        wfdb_printconfig;
    otherwise
        error('Unknow option for WFDB_INSTALL. Type ''HELP WFDB_INSTALL''');
end

function wfdb_installation
%Get configuration parameters
config=wfdb_setup();
%Creat line and file separators locally in order to make some statements more concise
FSEP=config.FSEP;
LSEP=config.LSEP;
diary(config.INSTALL_LOG)
display('Installing WFDB Toolbox for MATLAB ....')
CUR_DIR=pwd;
msgid='WFDBToolbox:wfdb_install:wfdb_installation';

%Get Toolbox version from JAR FILE and set default installation directory
jar_info=wfdb_install('jarquery',config.JARML);
ver_ind=find(strcmp(jar_info,'Specification-Version')==1);
config.JARML_VER=jar_info{ver_ind,2};
config.DEFAULTINSTALLDIR=[config.DEFAULTWSMROOT config.FSEP config.JARML_VER];


try
    %Check if system is supported
    wfdb_support(config);
    if(~isempty(which('wfdb_config')))
        error(msgid,'WFDB Toolbox is already installed!! \n Run wfdb_install(''remove'') to remove the currently installed version.')
    end
    
    %Install WFDB in the usual default directory
    WSMROOT=config.DEFAULTINSTALLDIR;
    answer=[];
    while(~( strcmpi(answer,'y') || strcmpi(answer,'n') || strcmpi(answer,'c')))
        fprintf('The WFDB Toolbox will be installed at the recommended location: \n\t%s\n',...
            WSMROOT);
        answer=input('Select y (yes-continue), n (no-quit), or e (edit location): ','s');    
    end
    if(strcmpi(answer,'n'))
        error(msgid,'Installation cancelled by user.');
    elseif(strcmpi(answer,'c'))
        WSMROOT=uigetdir([],'Select the directory to install WFDB Toolbox:');
        if(isempty(WSMROOT))
            error(msgid,'No directory selected for installation of the toolbox.')
        end
        answer=questdlg(['WFDB will be installed at:' WSMROOT],'WFDB Install Confirmation',...
            'Continue','Exit','Exit');
        if(strcmpi(answer,'Exit'))
            error(msgid,'Installation cancelled by user.');
        end
    elseif(strcmpi(answer,'y'))
        display('Continuing installation...')
    else
        %Should never happen, but putting in here just in case...
        error('Unknown option selected...aborting installation')
    end
    if (~exist(WSMROOT,'dir'))
        mkdir(WSMROOT);
    end
    %set configuration WSMROOT to the one in which it will be installed 
    %for reporting error purposes
    config.WSMROOT=WSMROOT;
    config.CUR_DIR=CUR_DIR;
    [stat,mess]=fileattrib(WSMROOT);
    if(stat && ~mess.UserWrite)
        error(msgid,['Please make sure you have write permission to :' WSMROOT]);
    end
    %Needed to get back from the UI window in a timely  manner.
    pause(0.1)
    config.LOCAL_ML_LIB_PATH=[config.WSMROOT FSEP 'librarypath.txt'];
    config.LOCAL_ML_CL_PATH= [config.WSMROOT FSEP 'classpath.txt'];

    copyfile([CUR_DIR FSEP config.JARML],WSMROOT);
    copyfile([CUR_DIR FSEP config.JARSWIG],WSMROOT);
    copyfile([CUR_DIR FSEP config.JARSWIG_ML],WSMROOT);
    cd(WSMROOT)
    display(['Unpacking JAR file: ' config.JARML])
    unzip([CUR_DIR FSEP config.JARML],WSMROOT);
    movefile([WSMROOT FSEP 'mcode' FSEP '*'],WSMROOT)
    rmdir([WSMROOT FSEP 'mcode'])
    display(['Unpacking JAR file: ' config.JARSWIG])
    unzip([CUR_DIR FSEP config.JARSWIG],WSMROOT);
    display(['Unpacking JAR file: ' config.JARSWIG_ML])
    unzip([CUR_DIR FSEP config.JARSWIG_ML],WSMROOT);
    
    if(~exist([WSMROOT FSEP config.LIB_ARCH],'dir'))
        mkdir(WSMROOT,config.LIB_ARCH);
    end
    cd(config.LIB_ARCH)
    copyfile([CUR_DIR FSEP config.JARNATIVEWFDB]);
    display(['Unpacking JAR file: ' config.LIB_ARCH])
    destDir=[WSMROOT FSEP config.LIB_ARCH];
    unzip([CUR_DIR FSEP config.JARNATIVEWFDB],destDir);
    
    
    %Remove any files in ${WSMROOT}/lib-${ARCH} whose names do not end in -${ARCH}.
    %and strip the -${ARCH} suffixes from those remaining
    display('Installing platform specific libraries...')
    dir_fl=dir;
    arch_fl=dir(['*' config.ARCH]);
    arch_name=cell(size(arch_fl));
    [arch_name{:}]=arch_fl(:).name;
    ARCH_SUF_LEN=length(config.OSARCH)-length(config.OS);
    for i=1:length(dir_fl)
        fname=dir_fl(i).name;
        if(sum(strcmpi(fname,arch_name)))
            %Extract -${ARCH} suffixes
            movefile(fname,fname(1:end-ARCH_SUF_LEN))
        else
            if(~strcmp(fname,'.') && ~strcmp(fname,'..'))
                if(exist(fname,'dir'))
                    rmdir(fname,'s')
                else
                    delete(fname)
                end
            end
        end
    end
    
    %Add Toolbox path
    display('Adding Toobox to MATLAB path...')
    %addpath function from matlab will only add unique paths to the list
    %so there should not be a problem if the install is re-run (there 
    % will still only be one line per path).
    addpath(WSMROOT);
    addpath([WSMROOT FSEP config.UTIL]);
    suc=savepath([config.userpath config.FSEP 'pathdef.m']);
    %suc=savepath;
    if(suc)
        error(msgid,'Unable to modify MATLAB''s pathdef.m. Aborting installation');
    end
    if(~strcmpi(config.userpath,CUR_DIR))
        fprintf('Warning: pathdef.m saved to the userpath directory: \n%s\n instead of current directory: \n/%s\n' ...
            ,config.userpath,CUR_DIR)
        warning(msgid,['Current directory differs from startup userpath directory. You will need to ' ...
            'start MATLAB from the userpath directory printed above in order for pathdef.m to be properly loaded.']);
    end
   
    %Set MATLAB library and class path
    display('Generating files for MATLAB''s new static path configuration.')
    [stat,mess]=fileattrib(config.ML_PATH);
    if(stat && ~mess.UserWrite)
        fprintf('Aborting installation script due to user insufficient privileges')
        error(msgid,['User does not have permission to modify MATLAB''s PATH at:' config.ML_PATH]);
    end
    
    display('Adding WFDB Native Libraries to MATLAB''s path.')
    wfdb_set_path(config.ML_LIB_PATH,config.LOCAL_ML_LIB_PATH,[LSEP '%s'],...
        {[WSMROOT FSEP config.LIB_ARCH]},config);
    display('Adding WFDB Java Classes to MATLAB''s path.')
    wfdb_set_path(config.ML_CL_PATH,config.LOCAL_ML_CL_PATH,[LSEP '%s'],...
        {[WSMROOT FSEP config.JARWFDB],[WSMROOT FSEP config.JARWSM]},config);
    
    %Make directory for querying of Physionet's databases
    DB_CACHED_DIR=[WSMROOT config.FSEP config.DB_DIR config.FSEP];
    if(~exist(DB_CACHED_DIR,'dir'))
       [suc,mes,msid]=mkdir(DB_CACHED_DIR);
       if(~suc)
           warning(msid,['Could not make database cache directory: ' mes])
       end
    end
    %CD Back to original directory
    cd(CUR_DIR)
catch exception
    %Error occured during installation, perform cleanup and exit with error
    %message
    wfdb_install_cleanup(config,CUR_DIR)
    diary off
    error(['A log file has been created at the current directory: ' ...
        config.INSTALL_LOG '. Exiting WFDB install: ' exception.getReport]);
end

%Move LOG INSTALL file to toobox directory for future reference and
%possible troubleshooting
diary off
movefile(config.INSTALL_LOG,[WSMROOT FSEP config.INSTALL_LOG]);

fprintf('\n\n\n****WFDB installed successfully at: \n\t%s\nTo complete installation follow these steps:\n',WSMROOT)
fprintf('1) Copy the file from \n\t %s \n\t to \n\t%s\n\t(or to your MATLAB startup directory for a local installation)\n',...
    config.LOCAL_ML_LIB_PATH,config.ML_LIB_PATH);
fprintf(['\n\tYou can try copying (if you have permission) by using the following MATLAB command:' ...
    '\n\tcopyfile(''%s'',''%s'',''f'')\n\n'],config.LOCAL_ML_LIB_PATH,config.ML_LIB_PATH);
fprintf('2) Copy the file from \n\t %s \n\t to \n\t%s\n\t(or to your MATLAB startup directory for a local installation)\n',...
    config.LOCAL_ML_CL_PATH,config.ML_CL_PATH);
fprintf(['\n\tYou can try copying (if you have permission) by using the following MATLAB command:' ...
    '\n\tcopyfile(''%s'',''%s'',''f'')\n\n'],config.LOCAL_ML_CL_PATH,config.ML_CL_PATH);
fprintf('3) Restart MATLAB.\n')
fprintf('4) (Optional) To demo the toolbox type: wfdb_demo.\n\tFor help about the WFDB Toolbox type: wfdb\n')



function wfdb_install_cleanup(config,CUR_DIR)
cd(CUR_DIR)
display('An error occured during in the WFDB Tooblox (see below)')
display(['For assistance, please send all the information below to: ' config.MAILTO])
wfdb_printconfig(config);
%Called from another function, the caller should report the error after the
%cleanup (ie, using exception.getReport)

function wfdb_remove()
%Removes WFDB Toolbox from this version of MATLAB

config=wfdb_setup();
CUR_DIR=pwd;
msgid='WFDBToolbox:wfdb_install:wfdb_remove';

try
    if(isempty(config.WSMROOT))
        MSG='No WFDB Toolbox directory detected in this version of MATLAB.';
        warning(msgid,MSG)
    end
    P=path;
    if(isempty(strfind(P,config.WSMROOT)))
        display('No WFDB strings found in MATLAB pathdef.m.')
    else
        %Remove Toolbox path
        rmpath([config.WSMROOT config.JARML_VER config.FSEP config.UTIL]);
        rmpath([config.WSMROOT config.JARML_VER]);
        eval(['savepath ' config.userpath config.FSEP 'pathdef.m']);
    end
    
    if(~isempty(config.WSMROOT))
        display('Removing WFDB native libraries from MATLAB path...')
        wfdb_clean_path(config.ML_LIB_PATH,config.LOCAL_ML_LIB_PATH,config);
        P=javaclasspath('-static');
        if(isempty(strfind(P,config.WSMROOT)))
            display('No WFDB Java Classes found in MATLAB path.')
        else
            display('Removing WFDB Java Classes from MATLAB path...')
            wfdb_clean_path(config.ML_CL_PATH,config.LOCAL_ML_CL_PATH,config);
        end
    end
    
    fprintf('\n\n\n****WFDB removed successfully. To complete toolbox removal follow these steps:\n')
    stp=1;
    if(~isempty(config.WSMROOT))
        fprintf('1) Copy the file from \n\t %s \n\t to \n\t%s\n\t(or to your MATLAB startup directory if you installed WFDB locally)\n',...
            config.LOCAL_ML_LIB_PATH,config.ML_LIB_PATH);
        fprintf('2) Copy the file from \n\t %s \n\t to \n\t%s\n\t(or to your MATLAB startup directory if you installed WFDB locally)\n',...
            config.LOCAL_ML_CL_PATH,config.ML_CL_PATH);
        fprintf('3) (Optional) Delete the folder:\n\t%s\n',...
            [config.WSMROOT config.JARML_VER]);
        stp=4;
    end
    fprintf('%s) Restart MATLAB.\n',num2str(stp))
        

catch exception
    %Error occured during installation, perform cleanup and exit with error
    %message
    wfdb_install_cleanup(config,CUR_DIR)
    error(['Exiting WFDB: ' exception.getReport]);
end


function wfdb_support(config)
msgid='WFDBToolbox:wfdb_install:wfdb_support';
if(~sum(strcmpi(config.OSARCH,config.SUPPORTED_OSARCH)))
    error(msgid,['Unsupported CPU architecture= ' config.OSARCH]);
end
ml_ver=version;
if(str2double(ml_ver(1)) < str2double(config.SUPPORTED_ML(1)))
    warning(msgid,['Unsupported MATLAB version= ' ml_ver '. Continuing installation...']);
end
if(str2double(config.JRE(1:2)) < str2double(config.SUPPORTED_JRE(1:2)))
    warning(msgid,['Unsupported JVM version= ' config.JRE '. Continuing installation...']);
end




function wfdb_set_path(ORIG_PATH, BK_PATH,STR_FORMAT,STR_PATH,config)
%
%
%Modifies MATLAB Path files (including library and class)
%in order to add the WDFBD Toolbox location to it
%Paramereters are:
% ORIG_PATH - Full path to the original file
% BK_PATH - Full path to the backup file (where the original 
% file will be stored prior to any modification)
% STR_FORMAT -The string format to write to the file 
% STR_PATH - The string to add to the file (ie, the full path that will be
% written into the configuration file). In the case of the CLASS path there
% are two path locations, which are send as cell array and looped through.
exception=[];
msgid='WFDBToolbox:wfdb_install:wfdb_set_path';   
usr=''; %This variable is required to change file permissions
if(isunix)
    usr='u';
end

if( ~exist(ORIG_PATH,'file') )
    MSG=['Missing Library PATH information in your MATLAB installation. Expected file: \n'...
        ORIG_PATH '\nPlease check your MATLAB installation.'];
    error(msgid,MSG)
end

%Check if patter is already presen in the original file
fid=fopen(ORIG_PATH,'r');
str_tmp=textscan(fid,'%s','Delimiter','\n');
fclose(fid);
check=0;
N=length(STR_PATH);
for n=1:N
    tmp_check=strfind(str_tmp{:},STR_PATH{n});
    check=check+~isempty(cell2mat(tmp_check));
end
display(['Copying current path file to: ' BK_PATH])
%Using 'trash' for backwards compatibility
[suc,mes,trash]=copyfile(ORIG_PATH,BK_PATH);
if(check == N)
    warning(msgid,'Toolbox already present in path file. The new path file will be exactly the same as the old one ...')
    return;
end
if(~suc)
    fprintf('You do not have permission to write to :\n %s \n',BK_PATH);
    reply=[];
    while(~( strcmpi(reply,'y') || strcmpi(reply,'n') ))
        reply=input('Do you want to attempt to change permissions in order to install toolbox? Y/N [Y]:','s');    
    end
    if(strcmpi(reply,'y'))
        fprintf('Giving write permission to user: %s for file: \n %s \n',config.USER,BK_PATH);
        fileattrib(BK_PATH,'+w',usr)
        %Attempt to copy again
        [suc,mes,trash]=copyfile(ORIG_PATH,BK_PATH);
    end
end
if(~suc)
    MSG=['Could not copy MATLAB Library path file. Check folder permissions! Error: ' mes];
    error(msgid,MSG)
end
fid=fopen(BK_PATH,'a');
try
    if(fid == -1)
        fprintf('You do not have permission to modify :\n %s \n',BK_PATH);
        reply=[];
        while(~( strcmpi(reply,'y') || strcmpi(reply,'n') ))
            reply=input('Do you want to attempt to change permissions in order to install toolbox? Y/N [Y]:','s');
        end
        if(strcmpi(reply,'y'))
            fprintf('Giving write permission to user: %s for file: \n %s \n',config.USER,BK_PATH);
            fileattrib(BK_PATH,'+w',usr)
            %Attempt to copy again
            fid=fopen(BK_PATH,'a');
        end
    end
    if(fid == -1)
        fprintf('User does not have write permission to modify file :  \n %s \n',BK_PATH);
        MSG=['Could not create new file : ',BK_PATH];
        error(msgid,MSG)
    end
    for n=1:N
        fprintf(fid,STR_FORMAT,STR_PATH{n});
    end
    fprintf('Sucessfully generated new path file :  \n %s \n',BK_PATH);
catch exception
    %Continue with setup, exception will be thrown after clean-up
    
end
if(fid ~= -1)
    fclose(fid);
end
if(~isempty(exception))
    error(['Error in writing to MATLAB path file: ' exception.getReport])
end

function wfdb_clean_path(ORIG_PATH,BK_PATH,config)
msgid='WFDBToolbox:wfdb_install:wfdb_clean_path';
usr='';
if(isunix)
    usr='u';
end
fidw=fopen(BK_PATH,'w'); %Create blank backup file
if(fidw == -1)
    fprintf('You do not have permission to write to :\n %s \n',BK_PATH);
    reply=[];
    while(~( strcmpi(reply,'y') || strcmpi(reply,'n') ))
        reply=input('Do you want to attempt to change permissions in order to remove toolbox? Y/N [Y]:','s');    
    end
    if(strcmpi(reply,'y'))
        fprintf('Giving write permission to user: %s for file: \n %s \n',config.USER,BK_PATH);
        fileattrib(BK_PATH,'+w',usr)
        %Attempt to write again
        fidw=fopen(BK_PATH,'w'); 
    end
end
if(fidw==-1)
    error(msgid,['Could not create back-up path file: ' BK_PATH]);
end

fclose(fidw); %Close file in order to overwrite with original one
%Find lines in the library path that has the Toolbox name an remove them
fprintf('***Generating new path (removing WFDB strings from path file): \n %s \n from old path:\n %s \n',BK_PATH,ORIG_PATH);
%Store path before processing
copyfile(ORIG_PATH,BK_PATH);
fidr=fopen(ORIG_PATH,'r');
fidw=fopen(BK_PATH,'w');
if(fidw == -1)
    fprintf('You do not have permission to write to :\n %s \n',BK_PATH);
    reply=[];
    while(~( strcmpi(reply,'y') || strcmpi(reply,'n') ))
        reply=input('Do you want to attempt to change permissions in order to remove toolbox? Y/N [Y]:','s');    
    end
    if(strcmpi(reply,'y'))
        fprintf('Giving write permission to user: %s for file: \n %s \n',config.USER,BK_PATH);
        fileattrib(BK_PATH,'+w',usr)
        %Attempt to write again
        fidw=fopen(BK_PATH,'w'); 
    end
end
tline = fgets(fidr);
while (tline ~= -1)
    if(isempty(strfind(tline,config.TOOLBXNAME)))
        %Line does not include toolbox name so copy to new file
        count=fprintf(fidw,'%s',tline);
        if(~count)
            MSG='Could not generate new library path file.';
            warning(msgid,MSG)
        end
    end
    tline = fgets(fidr);
end
if(fidr ~= -1)
    fclose(fidr);
end
if(fidw ~= -1)
    fclose(fidw);
end





function config=wfdb_setup
%CONFIGURATION Parameters for the WFDB Toolbox

% The following external dependencies are used by the WFDB Toolbox for MATLAB.
% The version numbers listed are those of the shared libraries distributed with
% the WFDB Toolbox; using a newer minor version of any of these should be safe,
% but is not necessarily guaranteed to work.
%
% cURL 	7.19.3 	http://curl.haxx.se
% GnuTLS 	2.6.4 	http://www.gnu.org/software/gnutls
% GnuPG 	1.4.4 	http://www.gnupg.org
% Libgpg-error 	1.7 	http://www.gnupg.org/download/#libgpg-error
% Libidn 	1.12 	http://www.gnu.org/software/libidn
% WFDB 	10.4.21 	http://physionet.org/physiotools/wfdb.shtml
% WFDB SWIG Wrappers 	10.4.1 	http://www.physionet.org/physiotools/wfdb-swig.shtml
%Supported configurations
config.ROOT_URL='www.physionet.org/physiotools/matlab/wfdb-swig-matlab/';
config.MAILTO='wfdb-matlab-support@physionet.org';
config.SUPPORTED_OSARCH={'linux-amd64','linux-i386',...
    'windows-amd64','windows-x86',...
    'maxosx-i386','macosx-x86_64','macosx-ppc'};
config.SUPPORTED_OS={'windows','linux','macosx'};
config.SUPPORTED_ML='7'; %or later
config.SUPPORTED_JRE='1.4'; %or later
config.TOOLBXNAME='wfdb-swig-matlab'; %Default name for the toolbox (important in order to parse its location)
%Required Java Libraries
config.JARML='wsm-matlab-code.jar';
config.JARSWIG='wfdb-swig.jar';
config.JARWFDB='wfdb.jar';
config.JARWSM='wsm-classes.jar';
msgid='WFDBToolbox:wfdb_install:wfdb_setup';
exception=[];
%System specific parameters
if(ispc)
    config.OS='windows';
    upath=regexp(userpath,';','split');
    config.userpath=upath{1};
elseif(isunix && ~ismac)
    config.OS='linux';
    upath=regexp(userpath,':','split');
    config.userpath=upath{1};
elseif(ismac)
    config.OS='macosx';
    upath=regexp(userpath,':','split');
    config.userpath=upath{1};
else
    config.OS=lower(char(java.lang.System.getProperty('os.name')));
end
config.ARCH=lower(char(java.lang.System.getProperty('os.arch')));
config.JRE=lower(char(java.lang.System.getProperty('java.version')));
config.FSEP=char(java.lang.System.getProperty('file.separator'));
config.LSEP=char(java.lang.System.getProperty('line.separator'));
config.OSARCH=[config.OS '-' config.ARCH];
config.USER=char(java.lang.System.getProperty('user.name'));
config.INSTALL_LOG='wfdb_install.log';
%Default root installation for Toolbox
%Compatible with version 0.0.1
config.DEFAULTWSMROOT=[char(java.lang.System.getProperty('user.home')) ...
    config.FSEP '.' config.TOOLBXNAME];


%Required Native Java Library
config.JARNATIVEWFDB=['wfdb-nativelibs-' config.OS '-' config.ARCH '.jar'];
config.JARSWIG_ML=['wfdb-swig-matlab-' config.OS '.jar'];
%Required Native Library
config.LIB_ARCH=['lib-' config.ARCH];

config.ML_PATH=[matlabroot config.FSEP 'toolbox' config.FSEP 'local' config.FSEP];
config.ML_LIB_PATH=[config.ML_PATH 'librarypath.txt'];
config.ML_CL_PATH= [config.ML_PATH 'classpath.txt'];
config.UTIL='util';

%Post-installation parameters (useful for uninstalling and querying)
config.WSMROOT=[];
config.JARML_VER=[];
config.JARSWIG_VER=[];
config.JARWSM_VER=[];
config.JARNATIVEWFDB_VER=[];
config.JARSWIG_ML_VER=[];
config.cURL_VER=[];
config.GnuTLS_VER=[];
config.GnuPG_VER=[];
config.Libgpg_VER=[];
config.Libidn_VER=[];

%Versions 0.0.2 or older would install in a hidden folder by default, so
%check that syntax as well
tmp_path=regexp(which('wfdb_config'),config.FSEP,'split');
tmp_ind=strmatch(config.TOOLBXNAME,tmp_path,'exact');
if(isempty(tmp_ind))
    tmp_ind=strmatch(['.' config.TOOLBXNAME],tmp_path,'exact');
end
if(~isempty(tmp_path))
    config.INSTALLDIR=regexprep(which('wfdb_config'),['\' config.FSEP 'wfdb_config.m'],'');
end
if(~isempty(tmp_ind))
    %Attempt to parse the path to get parameters from the installation
    for i=1:tmp_ind
        config.WSMROOT=[config.WSMROOT tmp_path{i} config.FSEP];
    end
    if(~isempty(tmp_ind))
        %Directory following root should be toolbox version number --- legacy
        config.JARML_VER=tmp_path{tmp_ind+1};
        %Using 'trash' instead of '~' for backwards compatability!
        try
            [config.JARNATIVEWFDB_VER,trash,trash]=wfdb_config;
        catch exception
            %Exception will be used below to display warning
        end
    end
end
%The location of these 
% needs also to be set in the installation function!!
config.LOCAL_ML_LIB_PATH=[config.WSMROOT config.FSEP 'librarypath.txt'];
config.LOCAL_ML_CL_PATH= [config.WSMROOT config.FSEP 'classpath.txt'];
if(~isempty(exception))
    warn_str=['Could not verify native library jar version. Try copying path files: \n\t\t' ...
        config.LOCAL_ML_LIB_PATH '\n\tand\n\t\t'...
        config.LOCAL_ML_CL_PATH '\n\tto\n\t\t'...
        config.ML_PATH '\n\tand then restart MATLAB.\n'];
    warning(msgid,warn_str)
end
%The Default installation directory shoud be a subdirectory with the
%appropiate Toolbox Version Number under the WSMROOT directory

config.DEFAULTINSTALLDIR=[config.DEFAULTWSMROOT config.FSEP config.JARML_VER];
    

%PhysioNet Database INFO Specific configurations
config.DB_LIST='DBS';
config.DB_DIR='pn_server';
config.DB_LIST_URL='http://physionet.org/physiobank/database/';
config.DB_URL='http://physionet.org/physiobank/database/pbi/';
config.DB_CACHED_DIR=[config.INSTALLDIR config.FSEP config.DB_DIR config.FSEP];

function info=wfdb_jarquery(jar_name)

if(~strcmp(jar_name(end-3:end),'.jar'))
    %Not a jar file, return
    return;
end
%Use this function instead of the JAR utility which might not be installed
%in some systems.
jar=java.util.jar.JarFile(jar_name);
manifest=jar.getManifest();
map=manifest.getMainAttributes();
it= map.keySet.iterator();
info=[];
while(it.hasNext)
    ob=it.next();
    %display([char(ob.toString) ': ' char(map.getValue(ob))])
    info=[info ; {char(ob.toString),char(map.getValue(ob))}];
end


function wfdb_printconfig(config)

fnames=fieldnames(config);
fprintf('\n****WFDB Configuration Parameters: \n\n')
for f=1:length(fnames)
    val=getfield(config,fnames{f});
    if isempty(val)
        fprintf('\t\t%s:\t(EMPTY)\n',fnames{f});
    elseif isnumeric(val) 
        fprintf('\t\t%s:\t%f\n',fnames{f},val);
    elseif iscellstr(val) 
        fprintf('\t\t%s:\t\n',fnames{f});
        for i=1:length(val)
            fprintf('\t\t  \t%s\n',val{i});
        end
    else
        fprintf('\t\t%s:\t%s\n',fnames{f},val);
    end
end
