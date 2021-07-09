package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.CalcGrpc;
import service.CalcRequest;
import service.CalcResponse;

import java.util.ArrayList;
import java.util.List;

public class CalcImpl extends CalcGrpc.CalcImplBase{
    ArrayList<Integer> numbers = new ArrayList<>();

    public CalcImpl () {
        super();
    }

    public void add (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double answer = 0;
        List<Double> numbers = req.getNumList();
        for (Double d : numbers) {
            answer = answer + d;
        }
        response.setSolution(answer);

        CalcResponse resp = response.build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void multiply (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double answer = 0;
        List<Double> numbers = req.getNumList();
        for (Double d : numbers) {
            answer = answer * d;
        }
        response.setSolution(answer);

        CalcResponse resp = response.build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void subtract (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double answer = 0;
        List<Double> numbers = req.getNumList();
        answer = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            answer = answer - numbers.get(i);
        }
        response.setSolution(answer);

        CalcResponse resp = response.build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void divide (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double answer = 0;
        List<Double> numbers = req.getNumList();
        answer = numbers.get(0);
        double denom = 0;
        for (int i = 1; i < numbers.size(); i++) {
            denom = denom + numbers.get(i);
        }
        answer = answer / denom;

        response.setSolution(answer);

        CalcResponse resp = response.build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

}
