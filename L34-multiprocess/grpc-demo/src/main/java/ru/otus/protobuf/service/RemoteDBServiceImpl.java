package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import java.util.List;
import ru.otus.protobuf.Empty;
import ru.otus.protobuf.RemoteDBServiceGrpc;
import ru.otus.protobuf.UserMessage;
import ru.otus.protobuf.model.User;

@SuppressWarnings({"squid:S2142", "squid:S106"})
public class RemoteDBServiceImpl extends RemoteDBServiceGrpc.RemoteDBServiceImplBase {

    private final RealDBService realDBService;

    public RemoteDBServiceImpl(RealDBService realDBService) {
        this.realDBService = realDBService;
    }

    @Override
    public void saveUser(UserMessage request, StreamObserver<UserMessage> responseObserver) {
        User user = realDBService.saveUser(request.getFirstName(), request.getLastName());
        responseObserver.onNext(user2UserMessage(user));
        responseObserver.onCompleted();
    }

    @Override
    public void findAllUsers(Empty request, StreamObserver<UserMessage> responseObserver) {
        List<User> allUsers = realDBService.findAllUsers();
        allUsers.forEach(u -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            responseObserver.onNext(user2UserMessage(u));
        });
        responseObserver.onCompleted();
    }

    private UserMessage user2UserMessage(User user) {
        return UserMessage.newBuilder()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .build();
    }
}
