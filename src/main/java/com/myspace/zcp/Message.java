package com.myspace.zcp;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Message implements java.io.Serializable {


    // This Number needs to be looked up from the database - !create method!
    static final int BIN_NUMBER = 020016;
    static final String versionNumber = "D0";
    static final String transactionCode = "B1";
    static final String transactionCount = "1";
    static final String processorControlNumber1 = "01" ;
    static final String ServiceProviderIDQualifier1 = "01";
    static final String[] ServiceProviderID1 = new String[] {"5812905", "5819656", "5824063"};
    static final String[] ServiceProviderID2 = new String[] {"1598158149", "1487181012", "1609375930"};

    static final long serialVersionUID = 1L;

    private java.lang.Long id;

    @org.kie.api.definition.type.Label(value = "Version Number RH")
    private java.lang.String versionNumberRH;

    @org.kie.api.definition.type.Label(value = "Transaction Code RH")
    private java.lang.String transactionCodeRH;

    @org.kie.api.definition.type.Label(value = "Transaction Count RH")
    private java.lang.String transactionCountRH;

    @org.kie.api.definition.type.Label(value = "Processor Control Number RH")
    private java.lang.String processorControlNumberRH;

    @org.kie.api.definition.type.Label(value = "Date Of Service RH")
    private java.lang.String dateOfServiceRH;

    @org.kie.api.definition.type.Label(value = "Vendor Certification RH")
    private java.lang.String vendorCertificationRH;

    @org.kie.api.definition.type.Label(value = "Business Message")
    private java.lang.String message;

    @org.kie.api.definition.type.Label(value = "Claim Status")
    private java.lang.Integer claimStatus;

    public Message() {
    }

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.String getVersionNumberRH() {
        return this.versionNumberRH;
    }

    public void setVersionNumberRH(java.lang.String versionNumberRH) {
        this.versionNumberRH = versionNumberRH;
    }

    public java.lang.String getTransactionCodeRH() {
        return this.transactionCodeRH;
    }

    public void setTransactionCodeRH(java.lang.String transactionCodeRH) {
        this.transactionCodeRH = transactionCodeRH;
    }

    public java.lang.String getTransactionCountRH() {
        return this.transactionCountRH;
    }

    public void setTransactionCountRH(java.lang.String transactionCountRH) {
        this.transactionCountRH = transactionCountRH;
    }

    public java.lang.String getProcessorControlNumberRH() {
        return this.processorControlNumberRH;
    }

    public void setProcessorControlNumberRH(
            java.lang.String processorControlNumberRH) {
        this.processorControlNumberRH = processorControlNumberRH;
    }

    public java.lang.String getDateOfServiceRH() {
        return this.dateOfServiceRH;
    }

    public void setDateOfServiceRH(java.lang.String dateOfServiceRH) {
        this.dateOfServiceRH = dateOfServiceRH;
    }

    public java.lang.String getVendorCertificationRH() {
        return this.vendorCertificationRH;
    }

    public void setVendorCertificationRH(java.lang.String vendorCertificationRH) {
        this.vendorCertificationRH = vendorCertificationRH;
    }

    public java.lang.String getMessage() {
        return this.message;
    }

    public void setMessage(java.lang.String message) {
        this.message = message;
    }

    public java.lang.Integer getClaimStatus() {
        return this.claimStatus;
    }

    public void setClaimStatus(java.lang.Integer claimStatus) {
        this.claimStatus = claimStatus;
    }

    public Message(java.lang.Long id, java.lang.String versionNumberRH,
                   java.lang.String transactionCodeRH,
                   java.lang.String transactionCountRH,
                   java.lang.String processorControlNumberRH,
                   java.lang.String dateOfServiceRH,
                   java.lang.String vendorCertificationRH, java.lang.String message,
                   java.lang.Integer claimStatus) {
        this.id = id;
        this.versionNumberRH = versionNumberRH;
        this.transactionCodeRH = transactionCodeRH;
        this.transactionCountRH = transactionCountRH;
        this.processorControlNumberRH = processorControlNumberRH;
        this.dateOfServiceRH = dateOfServiceRH;
        this.vendorCertificationRH = vendorCertificationRH;
        this.message = message;
        this.claimStatus = claimStatus;
    }

}