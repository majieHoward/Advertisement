package www.howard.com.advertisement.data.transfer.dto;

public interface IDataTransferObject extends IBusinessObject,
		IPersistantObject, IViewObject {
	public IDataTransferObject generateDataTransferObject(
            IDataTransferObject queryParameters);
}
