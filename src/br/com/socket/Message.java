package br.com.socket;

    public class Message {
    private String OpCode;

    private String message;

    private String printerCode;

    public String getPrinterCode() {
        return printerCode;
    }

    public void setPrinterCode(String printerCode) {
        this.printerCode = printerCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOpCode() {
        return OpCode;
    }

    public void setOpCode(String opCode) {
        OpCode = opCode;
    }
}
