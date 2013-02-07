package org.physionet.wfdb.physiobank;

import java.util.ArrayList;

import org.physionet.wfdb.Rdsamp;


public class PhysioNetSignal {

	private final String recName;
	private final String dbName;
	private Integer recordIndex=null;
	private String signalIndex=null;
	private String startTime=null;
	private String lengthTime=null;
	private String lengthSample=null;
	private String samplingFrequency=null;
	private String Group=null;
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
	private Double data=null; //data in physical units
	
	public PhysioNetSignal(Integer mrecordIndex, String mrecName, String mdbName){
		setRecordIndex(mrecordIndex);
		recName=mrecName;
		dbName=mdbName;
	}
	
	public PhysioNetSignal(String mrecName, String mdbName){
		recName=mrecName;
		dbName=mdbName;
	}
	
	public void printSignalInfo(){
		System.out.println("DB/Record Name: " + dbName + "/" + recName);
		System.out.println("Record/Signal Index: " + recordIndex + "/" + signalIndex);
		System.out.println("Group: " + Group);
		System.out.println("\tStart Time:\t\t" + startTime);
		System.out.println("\tLength Time:\t\t" + lengthTime);
		System.out.println("\tNumber of Samples:\t" + lengthSample);
		System.out.println("\tSampling Frequency:\t" + samplingFrequency);
		System.out.println("\tFile:\t\t\t" + file);
		System.out.println("\tDescription:\t\t" + description);
		System.out.println("\tInitial Value:\t\t" + initialValue);
		System.out.println("\tGain:\t\t\t" + gain);
		System.out.println("\tFormat:\t\t\t" + format);
		System.out.println("\tI\\O:\t\t\t" + io);
		System.out.println("\tADC Resolution:\t\t" + adcResolution);
		System.out.println("\tADC Zero:\t\t" + adcZero);
		System.out.println("\tBaseline:\t\t" + baseline);
		System.out.println("\tChecksum:\t\t" + checksum);
		
	}
	
	public void loadPhysicalData(){
		//Calls RDSAMP to get data for this signal and converts it to Short
		//Data is return
		Rdsamp rdsampexec = new Rdsamp();
		rdsampexec.setArgumentValue(Rdsamp.Arguments.stopTime, "s10");
		rdsampexec.setArgumentValue(Rdsamp.PrintTimeFormatLabel.p);
		rdsampexec.setArgumentValue(Rdsamp.Arguments.recordName,recName);
		System.out.println(rdsampexec.execToStringList());
		
		//ArrayList<String>[] tmpData= rdsampexec.execTo2DString();
		/*
		ArrayList<String> ;
		for(int i=0;i<tmpData.length;i++){
			rowData=data[0];	
		}
		
		System.out.println(tmp);
		*/
		
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

	public String getSignalIndex() {
		return signalIndex;
	}

	public void setSignalIndex(String string) {
		this.signalIndex = string;
	}
	
}
