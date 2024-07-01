package rj.training.rest.exceptions;

public class ErrorResponse {

	  private String message;
	  private int errorCode;
	  
	  public ErrorResponse(int errorCode, String message) {
		    this.message = message;
		    this.errorCode = errorCode;
		  }

	  public ErrorResponse(String message) {
	    this.message = message;
	  }

	  public String getMessage() {
	    return message;
	  }
	  
	  public int getErrorCode() {
		  return errorCode;
	  }
	}

