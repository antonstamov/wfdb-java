function [desc] = wfdb_desc(recordName, readable)
%
% [desc] = wfdb_desc(recordName, readable)
%
% WFDB_DESC return specifications for signals in WFDB records
% The return value desc is a struct with the following fields:
%
% groups: a single-column struct array describing the groups which
%    comprise the record (described in more detail below)
% length: the length of the record, as a time-format string (may be empty)
% lengthSpecified: a boolean indicating whether the length could be determined
% multisegmentType: a string indicating the multi-segment type of
%    the record; may be either 'variable-layout', 'fixed-layout',
%    or 'not multi-segment'
% notes: a (line-based) list of misc. information about the record
% numSampleIntervals: the number of samples in the record
% numSegmentSampleIntervals: the number of samples in the first
%    segment, if the record is multi-segment; otherwise, zero
% samplingFrequency: the sampling frequency, in Hz, of the record
% segmentLength: the length, as a time-format string, of the first
%    segment, if the record is multi-segment; otherwise, null ([])
% startingTime: a time-format string indicating the time of day of
%    the beginning of the record, if available; otherwise,
%    'not specified'
%
% Each member of the 'groups' struct array has the following
% fields:
%
% filename: a string giving the file name which stores this
%    group of signals
% storageFormat: a number indicating the storage format of the
%    signals in this group
% blockSize: the size of the blocks in which this group's signals
%    must be read, or 0 if they may be read unbuffered
% signals: a single-column struct array describing the signals in
%    this group (described below)
%
% Finally, each member of the 'signals' struct array, in each of
% the 'groups' structs, has the following fields:
%
% groupNumber: the number of this signal's group (redundant)
% signalNumber: the number given to this signal
% description: a string describing this signal
% gain: the numerical value of the gain; combined with the value of
%    field 'units' to give a physical value
% units: a string describing the physical dimensions that, when
%    attached to the number in field 'gain', gives the gain of the
%    signal
% initVal: the initial value of the signal (the value of the first
%    sample)
% samplesPerFrame: the number of samples per frame
% adcResolution: the resolution of the ADC, in bits (i.e. the
%    number of significant bits per sample)
% adcZero: the value produced by the ADC at zero volts
% baseline: the value corresponding to the baseline (physical-zero)
%    level
% checksum: a 16-bit checksum of the signal's samples
%
% Two required arguments:
% recordName: the name of the WFDB record
% readable: a boolean; if true, WFDB_DESC() will attempt to open
%    every signal file for the record, and report only on those
%    that are readable.
% 
% See also WFDB_LICENSE, wfdb_desc(1) man page,
%    http://physionet.org/physiotools/wpg/wpg_36.htm#SEC160 (for
%    information on groups and signals within groups),
%    http://physionet.org/physiotools/wpg/wpg_40.htm#SEC164 (for
%    information on block sizes)
%
% Copyright (c) 2009 by Michael Craig, All Rights Reserved
% Contact M. Craig (mic@mit.edu)

% Modified by Ikaro Silva, 7/2012 

% FIXME: is the lengthSpecified field really necessary?
%

% FIXME: make the notes into separate rows? may not really be
%        possible in MATLAB, though, without wasting space...
%

%
LoadWFDBJava.load;
wfdb.wfdb.wfdbquit;

%
desc = deepconvert(wfdbdescMatlab.wfdbdesc(recordName, readable));




