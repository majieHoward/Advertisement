package www.howard.com.advertisement.data.transfer.dto;

import java.util.concurrent.ConcurrentHashMap;

public interface IBusinessObject {
	public ConcurrentHashMap<String, Object> evaluteRequiredParameter(
            ConcurrentHashMap<String, Object> mapOfRequiredParameter);

	public ConcurrentHashMap<String, Object> evaluteRequiredParameter(String keyOfParameter,
                                                                      Object valueOfParameter);
	
	public ConcurrentHashMap<String, Object> obtainMapOfRequiredParameter();
	
}
