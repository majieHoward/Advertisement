package www.howard.com.advertisement.data.transfer.dto;

public interface IPersistantObject {
	public void evaluateSqlStatement(String sqlStatement);

	public void evaluatePrimitiveSqlStatement(String primitiveSqlStatement);
	
	public void evaluateEntityName(String paramOfEntityName);

	public void evaluatePrimitiveSqlName(String sqlName);

	public String obtainSqlStatement();

	public String obtainPrimitiveSqlStatement();

	public String obtainPrimitiveSqlName();
	
	public String obtainEntityName();
	
	public IDataTransferObject evaluateResultSet(Object resultSet);
	
	public Object obtainResultSet();
	
}
