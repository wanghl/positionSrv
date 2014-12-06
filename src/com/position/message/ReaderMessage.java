package com.position.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReaderMessage {

	public byte[] checkCode = new byte[2]; // У����
	public final static String PROTOCOL_CODE = "C102";
	public int dataLength; // ���ݳ���
	public byte commandCode; // �������
	public byte tagCount;
	public String tagId;
	public int newTrigger;
	public int oriTrigger;
	public byte batteryValue; // ״̬�ֽڵ�BIT7λ��ʾ��ص�ѹ ��0 �������� 1�������� ��
	public int scanTimes ; //ɨ�����
	
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

	public byte frameType; // ֡ѡ��
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
