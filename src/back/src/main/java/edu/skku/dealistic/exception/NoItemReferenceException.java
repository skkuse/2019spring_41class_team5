package edu.skku.dealistic.exception;

import edu.skku.dealistic.model.Reference;

public class NoItemReferenceException extends RuntimeException {
    private Reference ref;

    public NoItemReferenceException(Reference ref) {
        this.ref = ref;
    }

    @Override
    public String getMessage() {
        return "No Specified Item Id for Reference: " + ref.getName();
    }
}
