package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.damsel.domain.Failure;
import com.rbkmoney.damsel.domain.SubFailure;
import com.rbkmoney.geck.common.stack.ObjectStack;
import com.rbkmoney.geck.serializer.exception.BadFormatException;

import java.io.IOException;

public class TTypedToDomainErrorHandler extends TErrorHandler<Failure> {
    private Failure failure;
    private ObjectStack<SubFailure> subFailures = new ObjectStack<>();

    @Override
    public void beginStruct(int size) throws IOException {
        if (failure == null) {
            failure = new Failure();
        } else {
            subFailures.push(new SubFailure());
        }
    }

    @Override
    public void endStruct() throws IOException {
        if (!subFailures.isEmpty()) {
            SubFailure subFailure = subFailures.pop();
            if (subFailure.getCode() != null) {
                if (subFailures.isEmpty()) {
                    failure.setSub(subFailure);
                } else {
                    subFailures.peek().setSub(subFailure);
                }
            }
        }
    }

    @Override
    public void name(String name) throws IOException {
        if (subFailures.isEmpty()) {
            failure.setCode(name);
        } else {
            subFailures.peek().setCode(name);
        }
    }

    @Override
    public Failure getResult() throws IOException {
        if (!subFailures.isEmpty()) {
            throw new BadFormatException("Not all sub failures closed");
        }
        Failure result = failure;
        failure = null;

        return result;
    }
}
