package underflowers.stackunderflow.domain;

public interface IEntity<ENTITY extends IEntity<ENTITY, ID>, ID extends Id> {
    ID getId();
    ENTITY deepClone();
}
