package service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import proto.ClientDataServiceGrpc;
import proto.Server;

import java.util.Calendar;

public class ClientData extends ClientDataServiceGrpc.ClientDataServiceImplBase {

    @Override
    public void getClientData(Server.DataReceived request, StreamObserver<Server.ServerResponse> responseObserver) {
        System.out.println(request.getName());

        if (request.getCnp().length() == 7) {
            char gender = request.getCnp().charAt(0);
            switch (gender) {
                case '1':
                case '5':
                    System.out.println("Male");
                    break;
                case '2':
                case '6':
                    System.out.println("Female");
                    break;
                default:
                    Status status = Status.NOT_FOUND.withDescription("Gender not exist!");
                    responseObserver.onError(status.asRuntimeException());
            }

            int year;
            switch (gender) {
                case '1':
                case '2':
                    year = Integer.parseInt(request.getCnp().substring(1, 3)) + 1900;
                    break;
                default:
                    year = Integer.parseInt(request.getCnp().substring(1, 3)) + 2000;
                    break;
            }

            int month = Integer.parseInt(request.getCnp().substring(3, 5));
            int day = Integer.parseInt(request.getCnp().substring(5, 7));

            if (month < Calendar.MONTH) {
                int age = Calendar.YEAR - year - 1;
                System.out.println(age);
            }
            if (month == Calendar.MONTH && day < Calendar.DAY_OF_MONTH) {
                int age = Calendar.YEAR - year - 1;
                System.out.println(age);
            }
            if (month == Calendar.MONTH && day >= Calendar.DAY_OF_MONTH) {
                int age = Calendar.YEAR - year;
                System.out.println(age);
            }
            if (month > Calendar.MONTH) {
                int age = Calendar.YEAR - year;
                System.out.println(age);
            }
        }
        else {
            Status status = Status.OUT_OF_RANGE.withDescription("Invalid CNP length");
            responseObserver.onError(status.asRuntimeException());
        }
    }
}
