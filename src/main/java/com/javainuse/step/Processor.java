package com.javainuse.step;

import java.net.URLEncoder;

import com.javainuse.objects.DataObject;

import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<DataObject , DataObject > {

	@Override
	public DataObject process(final DataObject data) throws Exception {
		if (data == null) {
			return data;
		}
		if (data.getFilename() == null) {
			return data;		
		}
		if (data.getFilename().toLowerCase().contains(".pptx")) {

			String[] s = data.getFilename().replace(Reader.location, "").split("/");
			String bundleName = "";
			for (int i=0;i<s.length - 1;i++)
				if (bundleName == "")  {
					bundleName = s[i];
				} else {
					bundleName += " - " + s[i];
				}
			String songName = s[s.length - 1].replace(".pptx", "");

			data.setBundleName(URLEncoder.encode(bundleName.trim(), "UTF-8"));
			data.setSongName(URLEncoder.encode(songName, "UTF-8"));
			return data;
		}
		data.setFilename("");
		return data;
	}
}