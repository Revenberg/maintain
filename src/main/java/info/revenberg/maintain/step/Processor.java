package info.revenberg.maintain.step;

import java.net.URLEncoder;

import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<String , String > {

	@Override
	public String process(final String data) throws Exception {
		return data;
	}
}