package hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.usecasesboundary.requestresponse.response;

public interface Response {
	
	public static final EmptyResponse EMPTY_RESPONSE = new EmptyResponse();
	public static final class EmptyResponse implements Response {
	}
}