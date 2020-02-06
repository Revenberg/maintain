package com.javainuse.step;

import java.net.URLEncoder;

import com.javainuse.objects.DataObject;

import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<DataObject , DataObject > {

	@Override
	public DataObject process(final DataObject data) throws Exception {
		return data;
	}
}