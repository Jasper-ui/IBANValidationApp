package com.bank.ibanvalidator.controllers.responses;

public class ValidationErrorResponse {
  private int responseCode;
  private String fieldName;
  private String errorMessage;

  public ValidationErrorResponse(int responseCode, String fieldName, String errorMessage) {
    this.responseCode = responseCode;
    this.fieldName = fieldName;
    this.errorMessage = errorMessage;
  }

  public int getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(int responseCode) {
    this.responseCode = responseCode;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
