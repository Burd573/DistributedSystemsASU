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
        double val = 0;
        List<Double> numList = req.getNumList();
        String nums = "";
        for(Double d : numList) {
            nums += d + ", ";
        }
        System.out.println("Recieved from client: " + nums);
        for (Double d : numList) {
            val += d;
        }
        response.setSolution(val);
        CalcResponse resp = response.build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void subtract (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double val = 0;
        List<Double> numList = req.getNumList();
        String nums = "";
        for(Double d : numList) {
            nums += d + ", ";
        }
        System.out.println("Recieved from client: " + nums);
        val = numList.get(0);
        for (Double d : numList) {
            val -= d;
        }
        response.setSolution(val);
        CalcResponse resp = response.build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void multiply (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double val = 1;
        List<Double> numList = req.getNumList();
        String nums = "";
        for(Double d : numList) {
            nums += d + ", ";
        }
        System.out.println("Recieved from client: " + nums);
        for (Double d : numList) {
            val *= d;
        }
        response.setSolution(val);
        CalcResponse resp = response.build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void divide (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double val = 0;
        List<Double> numList = req.getNumList();
        String nums = "";
        for(Double d : numList) {
            nums += d + ", ";
        }
        System.out.println("Recieved from client: " + nums);
        val = numList.get(0);
        double denom = 0;
        for (Double d : numList) {
            denom = denom + d;
        }
        val /= denom;
        response.setSolution(val);
        CalcResponse resp = response.build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

}
