package edu.skku.dealistic.model;

import java.util.Map;

/**
 * Item reference
 *
 * @author Junhyun Kim
 */
public class ItemReference {
    /** Item ID used by our system */
    private Integer itemId;
    /** Map of IDs used by each {@link Reference} */
    private Map<Reference, String> referenceIds;

    public ItemReference(Integer itemId, Map<Reference, String> referenceIds) {
        this.itemId = itemId;
        this.referenceIds = referenceIds;
    }

    public String getItemReferenceId(Reference ref) {
        return referenceIds.get(ref);
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Map<Reference, String> getReferenceIds() {
        return referenceIds;
    }

    public void setReferenceIds(Map<Reference, String> referenceIds) {
        this.referenceIds = referenceIds;
    }
}
