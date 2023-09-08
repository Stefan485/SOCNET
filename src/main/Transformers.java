package main;

import org.apache.commons.collections15.Transformer;

public class Transformers {
	public static Transformer<Integer, Integer> getVertexTransformerTesting() {
		Transformer<Integer, Integer> transformer = new Transformer<Integer, Integer>() {
			
			@Override
			public Integer transform(Integer arg0) {
				return arg0;
			}
		};
		
		return transformer;
	}
	
	public static Transformer<String, String> getEdgeTransformerTesting() {
		Transformer<String, String> transformer = new Transformer<String, String>() {
			
			@Override
			public String transform(String arg0) {
				return arg0;
			}
		};
		
		return transformer;
	}
}
