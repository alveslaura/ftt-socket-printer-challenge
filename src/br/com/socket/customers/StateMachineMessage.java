package br.com.socket.customers;

import br.com.socket.Message;
import br.com.socket.enums.EnumStateMachine;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.LinkedList;
import java.util.List;

  public class StateMachineMessage {

    private EnumStateMachine state;
    private StringBuilder content;
    private Message msg;
    private  final Charset iso88591charset = Charset.forName("ISO-8859-1");

    public List<Message> normalizeData(byte[] data, int length){
        List<Message> ret = new LinkedList<>();

        for (int i = 0; i < length; i++) {
            switch (state){
                case WAIT_STX:
                    if(data[i] == 0x02){
                        state = EnumStateMachine.WAIT_PrinterCode;
                    }
                    break;
                case WAIT_PrinterCode:
                    msg.setPrinterCode(new String(data, i, 1));
                    state = EnumStateMachine.WAIT_OPCODE;
                    break;
                case WAIT_OPCODE:
                    msg.setOpCode(new String(data, i, 1));
                    state = EnumStateMachine.WAIT_ETX;
                    break;
                case WAIT_ETX:
                    if(data[i] == 0x03){
                        msg.setMessage(content.toString());
                        ret.add(msg);
                        start();
                    }
                    else{
                        ByteBuffer bb = ByteBuffer.wrap(new byte[]{data[i]});
                        content.append(iso88591charset.decode(bb).toString());
                    }
                    break;
                default:
                    state = EnumStateMachine.WAIT_ETX;
                    break;
            }
        }
        return ret;
    }

    public void start(){
        content = new StringBuilder();
        msg = new Message();
        state = EnumStateMachine.WAIT_STX;
    }
}

