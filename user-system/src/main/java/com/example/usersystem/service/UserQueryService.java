package com.example.usersystem.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.example.usersystem.aggregate.UserAggregate;
import com.example.usersystem.query.GetUserAggregateQuery;
import com.example.usersystem.repository.UserRepository;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQueryService {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private UserRepository userRepository;

    public void deleteUser(String userId) {

        userRepository.deleteById(userId);
    }

    public CompletableFuture<Optional<UserAggregate>> getUserAggreate(String postAggregateId, Long expectedVersion) {
        return queryGateway.query(new GetUserAggregateQuery(postAggregateId, expectedVersion),
                ResponseTypes.optionalInstanceOf(UserAggregate.class));

    }

    @QueryHandler
    public CompletableFuture<UserAggregate> handle(GetUserAggregateQuery getUserAggregateQuery) {
        CompletableFuture<UserAggregate> future = new CompletableFuture<UserAggregate>();
        if (getUserAggregateQuery.getExpectedVersion() == null) {
            EventSourcingRepository.builder(UserAggregate.class).eventStore(eventStore).build()
                    .load(getUserAggregateQuery.getPostAggregateId()).execute(future::complete);
        } else {
            EventSourcingRepository.builder(UserAggregate.class).eventStore(eventStore)
                    .eventStreamFilter(event -> event.getSequenceNumber() <= getUserAggregateQuery.getExpectedVersion())
                    .build().load(getUserAggregateQuery.getPostAggregateId()).execute(future::complete);
        }
        return future;
    }

}