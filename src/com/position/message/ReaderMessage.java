package com.position.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReaderMessage {

	public byte[] checkCode = new byte[2]; // 校验码
	public final static String PROTOCOL_CODE = "C102";
	public int dataLength; // 数据长度
	public byte commandCode; // 命令代码
	public byte tagCount;
	public String tagId;
	public int newTrigger;
	public int oriTrigger;
	public byte batteryValue; // 状态字节的BIT7位表示电池电压 ，0 电量充足 1电量不足 。
	public int scanTimes ; //扫描次数
	
	public Map<String  ,Map> tagsMap = new HashMap<String , Map>() ;
	
	public void putTag(String tagId ,int newTrigger)
	{
		Map map = new HashMap();
		map.put("triggerid",newTrigger) ;
		map.put("physicalid",tagId) ;
		tagsMap.put(tagId, map) ;
	}
	
	public Map<String ,Map> getTagsMap()
	{
		return tagsMap ;
	}
	
	public void setScanTimes(int scanTimes) {
		this.scanTimes = scanTimes;
	}

	public byte[] getReaderAddress() {
		return readerAddress;
	}

	public void setReaderAddress(byte[] readerAddress) {
		this.readerAddress = readerAddress;
	}

	public byte frameType; // 帧选项
	public byte[] readerAddress;

	public byte[] getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(byte[] checkCode) {
		this.checkCode = checkCode;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public byte getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(byte commandCode) {
		this.commandCode = commandCode;
	}

	public byte getFrameType() {
		return frameType;
	}

	public void setFrameType(byte frameType) {
		this.frameType = frameType;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public int getNewTrigger() {
		return newTrigger;
	}

	public void setNewTrigger(int newTrigger) {
		this.newTrigger = newTrigger;
	}

	public int getOriTrigger() {
		return oriTrigger;
	}

	public void setOriTrigger(int oriTrigger) {
		this.oriTrigger = oriTrigger;
	}

	public byte getBatteryValue() {
		return batteryValue;
	}

	public void setBatteryValue(byte stateByte) {
		this.batteryValue = stateByte;
	}


	public byte getTagCount() {
		return tagCount;
	}

	public void setTagCount(byte tagCount) {
		this.tagCount = tagCount;
	}
	

	public int getScanTimes() {
		return scanTimes;
	}

}
