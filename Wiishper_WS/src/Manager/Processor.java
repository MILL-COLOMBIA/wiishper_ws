package Manager;

import java.util.Map;

import org.json.JSONObject;

public interface Processor 
{
	public abstract Map<String, Object> process(Integer operation, Map<String, Object> data) throws Exception;
}
