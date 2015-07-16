package org.geoint.acetate.impl.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.geoint.acetate.domain.model.DataModel;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.model.common.Version;

/**
 *
 */
public class ImmutableDomainModel implements DataModel {

    private final DomainId domainId;
    private final Map<String, ObjectModel> objects; //key is object name
    private final Map<String, Collection<ObjectModel>> attributeIndex; //key is attribute name

    public ImmutableDomainModel(DomainId domainId,
            Collection<ObjectModel> objects)
            throws UnknownDomainObjectException {
        this.domainId = domainId;
        Map<String, Collection<ObjectModel>> tmpAttIndex = new HashMap<>();
        Map<String, ObjectModel> tmpCompIndex = new HashMap<>();

        for (ObjectModel o : objects) {
            if (!domainId.isMember(o)) {
                throw new UnknownDomainObjectException(ObjectId.getInstance(o),
                        "Object '"+o.toString()+"' is not a  member of the domain model '"
                        + domainId.asString()
                        + "'");
            }

            tmpCompIndex.put(o.getName(), o);

            //build object attribute index
            o.getAttributes().entrySet().stream()
                    .forEach((e) -> {
                        tmpAttIndex.putIfAbsent(e.getKey(), new ArrayList<>());
                        tmpAttIndex.get(e.getKey()).add(o);
                    });

        }
//
//        objects.stream()
//                .filter((o) -> o.getDomainName().equalsIgnoreCase(this.domainId.getName()))
//                .filter((o) -> this.domainId.getVersion().isWithin(o.getDomainVersion()))
//                .map((o) -> {
//                    tmpCompIndex.put(o.getName(), o);
//                    return o;
//                })
//                .forEach((o) -> {
//                    o.getAttributes().keySet().stream()
//                    .forEach((a) -> {
//                        tmpAttIndex.putIfAbsent(a, new ArrayList<>());
//                        tmpAttIndex.get(a).add(o);
//                    });
//                });
        this.attributeIndex = Collections.unmodifiableMap(tmpAttIndex);
        this.objects = Collections.unmodifiableMap(tmpCompIndex);
    }

    @Override
    public String getName() {
        return domainId.getName();
    }

    @Override
    public Version getVersion() {
        return domainId.getVersion();
    }

    @Override
    public Collection<ObjectModel> findAll() {
        return objects.values();
    }

    @Override
    public Optional<ObjectModel> find(String componentName) {
        return Optional.ofNullable(objects.get(componentName));
    }

    @Override
    public Collection<ObjectModel> findByAttribute(String attributeName) {
        return (attributeIndex.containsKey(attributeName))
                ? attributeIndex.get(attributeName)
                : Collections.EMPTY_LIST;
    }

}
