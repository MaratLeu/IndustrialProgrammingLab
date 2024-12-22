package Strategy;

import com.google.protobuf.InvalidProtocolBufferException;

import java.io.*;
import java.util.ArrayList;

public class ProtobufStrategy implements ReadWriteStrategy {
    @Override
    public ArrayList<String> read(String protobufFile, boolean isExpression) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(protobufFile)) {
            if (isExpression) {
                Input.InputProto message;
                while ((message = Input.InputProto.parseDelimitedFrom(inputStream)) != null) {
                    data.addAll(message.getExpressionsList());
                }
            } else {
                Output.OutputProto message;
                while ((message = Output.OutputProto.parseDelimitedFrom(inputStream)) != null) {
                    data.addAll(message.getResultsList());
                }
            }
        } catch (InvalidProtocolBufferException e) {
            throw new IOException("Ошибка парсинга Protobuf: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IOException("Ошибка чтения Protobuf: " + e.getMessage(), e);
        }
        return data;
    }

    @Override
    public void write(String filename, ArrayList<String> data, boolean isExpression) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            if (isExpression) {
                Input.InputProto.Builder inputProtoBuilder = Input.InputProto.newBuilder();
                inputProtoBuilder.addAllExpressions(data);
                Input.InputProto inputProto = inputProtoBuilder.build();
                inputProto.writeDelimitedTo(outputStream);
            } else {
                Output.OutputProto.Builder outputProtoBuilder = Output.OutputProto.newBuilder();
                outputProtoBuilder.addAllResults(data);
                Output.OutputProto outputProto = outputProtoBuilder.build();
                outputProto.writeDelimitedTo(outputStream);
            }
        } catch (Exception e) {
            throw new IOException("Ошибка записи Protobuf: " + e.getMessage(), e);
        }
    }
}

