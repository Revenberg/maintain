package info.revenberg.maintain.step;

import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<String , String > {

	@Override
	public String process(final String data) {
		return data;
	}
}