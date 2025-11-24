import java.io.Serializable;

public class Message implements Serializable {

    private final MessageType type;
    private final Object data;

    public Message(MessageType type, Object data) {
        this.type = type;
        this.data = data; }

    public MessageType getType() {
        return type;}

    public Object getData() {
        return data;}

    @Override public String toString() {
        return "Message{" + "type=" + type + ", data=" + data + '}'; } }