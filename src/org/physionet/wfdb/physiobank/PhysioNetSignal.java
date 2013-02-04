package org.physionet.wfdb.physiobank;

import java.util.ArrayList;

import org.physionet.graphics.Plot;
import org.physionet.wfdb.Rdsamp;
import org.physionet.wfdb.Wfdbdesc;

public class PhysioNetSignal {

	private final String recName;
	private final String dbName;
	private Integer recordIndex=null;
	private String startTime=null;
	private String lengthTime=null;
	private String lengthSample=null;
	private String samplingFrequency=null;
	private String  Group=null;
	private String file=null;
	private String description=null;
	private String initialValue=null;
	private String gain=null;
	private String format=null;
	private String io=null;
	private String adcResolution=null;
	private String adcZero=null;
	private String baseline=null;
	private String checksum=null;
	
	public PhysioNetSignal(Integer mrecordIndex, String mrecName, String mdbName){
		setRecordIndex(mrecordIndex);
		recName=mrecName;
		dbName=mdbName;
		//initializeSignal();
	}

	public static void main(String[] args) {
		PhysioNetSignal sig = new PhysioNetSignal(0,"101","mitdb");
		
	}

	public String getRecName() {
		return recName;
	}

	public String getDbName() {
		return dbName;
	}

	public Integer getRecordIndex() {
		return recordIndex;
	}

	public void setRecordIndex(Integer recordIndex) {
		this.recordIndex = recordIndex;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getLengthTime() {
		return lengthTime;
	}

	public void setLengthTime(String lengthTime) {
		this.lengthTime = lengthTime;
	}

	public String getLengthSample() {
		return lengthSample;
	}

	public void setLengthSample(String lengthSample) {
		this.lengthSample = lengthSample;
	}

	public String getSamplingFrequency() {
		return samplingFrequency;
	}

	public void setSamplingFrequency(String samplingFrequency) {
		this.samplingFrequency = samplingFrequency;
	}

	public String getGroup() {
		return Group;
	}

	public void setGroup(String group) {
		Group = group;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getGain() {
		return gain;
	}

	public void setGain(String gain) {
		this.gain = gain;
	}

	public String getAdcResolution() {
		return adcResolution;
	}

	public void setAdcResolution(String adcResolution) {
		this.adcResolution = adcResolution;
	}

	public String getIo() {
		return io;
	}

	public void setIo(String io) {
		this.io = io;
	}

	public String getAdcZero() {
		return adcZero;
	}

	public void setAdcZero(String adcZero) {
		this.adcZero = adcZero;
	}

	public String getBaseline() {
		return baseline;
	}

	public void setBaseline(String baseline) {
		this.baseline = baseline;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
}
