package www.howard.com.advertisement.data.transfer.dto;

import java.util.concurrent.ConcurrentHashMap;

public interface IViewObject {
	public void evaluateInteractiveData(Object paramOfInteractiveData);
	
	public Object obtainInteractiveData();

	public ConcurrentHashMap<String, Object> obtainRequestParamsMap();

	public void evaluateRequestParamsMap(ConcurrentHashMap<String, Object> requestParamsMap);

}
